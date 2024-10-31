package com.fang.screw.blog.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.BlogImageUploadMapper;
import com.fang.screw.blog.mapper.ResourceMapper;
import com.fang.screw.blog.service.ResourceService;
import com.fang.screw.blog.service.StorageService;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.ImageDetermineUtils;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.BlogImageUploadPO;
import com.fang.screw.domain.po.ResourcePO;
import com.fang.screw.domain.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

import static com.fang.screw.communal.constant.DynamicParameter.IMG_WEB_SITE_URL;
import static com.fang.screw.communal.constant.UploadConstant.BLOG_IMAGE_UPLOAD_FOLDER_NAME;
import static com.fang.screw.communal.constant.UploadConstant.BLOG_UPLOAD_IMAGE_MAX_SIZE;

/**
 * @FileName ResourceServiceImpl
 * @Description 上传图片
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourcePO> implements ResourceService {

    private StorageService storageService;

    private BlogImageUploadMapper blogImageUploadMapper;

    private OssTemplate ossTemplate;

    private ResourceMapper resourceMapper;

    private RabbitTemplate rabbitTemplate;

    /***
     * @Description 上传图片服务——博客内部的图片OR头像OR封面
     * @param file
     * @param fileVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/10/31
     */
    @Override
    public R<String> uploadImage(MultipartFile file, FileVO fileVO) {

        if (file == null || !StringUtils.hasText(fileVO.getType()) || !StringUtils.hasText(fileVO.getRelativePath())) {
            return R.fail("文件和资源类型和资源路径不能为空！");
        }

        // 保存文件到minio服务器中
        // 限制上传文件大小 不能为空 OR 超过10MB
        try{
            fileVO.setFileContent(Base64.getEncoder().encodeToString(file.getBytes()));

            if(ObjectUtils.isEmpty(file) || file.getSize() > BLOG_UPLOAD_IMAGE_MAX_SIZE){
                // 清理临时缓存
                file.getInputStream().close();
                return R.fail("上传文件不能为空或大小超过10MB");
            }

            // 判断上传的文件是图片
            if(!ImageDetermineUtils.isImageFile(file)){
                file.getInputStream().close();
                return R.fail("只能上传图片，请确保上传的是图片");
            }

            // 文件名字格式化
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotEmpty(fileName) && fileName.startsWith("./")){
                fileName = fileName.substring(2);
            }
            fileVO.setFileName(fileName);

            fileVO.setUserId(CurrentUserHolder.getUser().getId());

            if(Objects.equals(fileVO.getType(),"blogImage")){
                rabbitTemplate.convertAndSend("uploadImageExchange","uploadImageKey",fileVO);
                return R.success();
            }

            String imageUrl = saveImageAndResource(fileVO);
            file.getInputStream().close();

            return R.success(imageUrl);
        }catch (Exception e){

            log.error("上传失败："+e.getMessage());
            return R.fail("上传失败："+e.getMessage());
        }
    }

    /***
    * @Description 监听上传图片人物——只适用于批量上传博客图片场景
    * @param fileVO
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/31
    */
    @RabbitListener(queues = "uploadImageSimpleQueue")
    public void uploadImageSimpleQueueProcess(FileVO fileVO){
        try{
            String imageUrl = saveImageAndResource(fileVO);
        }catch (Exception e){
            log.info(CurrentUserHolder.getUser().toString());
            e.printStackTrace();
            log.error("批量上传博客图片监听任务失败："+e.getMessage());
        }

    }

    /***
     * @Description 上传图片
     * @param fileVO
     * @return {@link }
     * @Author yaoHui
     * @Date 2024/10/31
     */
    private String saveImageAndResource(FileVO fileVO) throws IOException {


        // 判断用户是否上传过这个文件 如果是上传的封面照片就不需要判断用户是否上传过了
        // 用户上传头像也会走这个逻辑，但是应该没关系；
        // 不行这个逻辑得取消，上线之后我们不能保证每个用户上传的名称不重复
        BlogImageUploadPO blogImageUploadPO = blogImageUploadMapper.selectOne(Wrappers.<BlogImageUploadPO>lambdaQuery()
                .eq(BlogImageUploadPO::getUserId, fileVO.getUserId())
                .eq(BlogImageUploadPO::getSourcePath,fileVO.getFileName()));
        if(!ObjectUtils.isEmpty(blogImageUploadPO)){
            return blogImageUploadPO.getTargetPath();
        }

        String imageName = UUID.randomUUID().toString();
        byte[] fileContent = Base64.getDecoder().decode(fileVO.getFileContent());
        InputStream inputStream = new ByteArrayInputStream(fileContent);
        OssFile ossFile = ossTemplate.upLoadFile(BLOG_IMAGE_UPLOAD_FOLDER_NAME,imageName,"png",inputStream);

        String imageKey = ossFile.getName().replaceFirst("/"+BLOG_IMAGE_UPLOAD_FOLDER_NAME,"");
        String imageUrl = ossTemplate.getImgWebSiteUrl() + imageKey;

        if(!Objects.equals(fileVO.getType(),"articleCover")){
            // 保存用户上传过的图片信息
            blogImageUploadPO = new BlogImageUploadPO();
            blogImageUploadPO.setImgSize(ossFile.getSize());
            blogImageUploadPO.setSourcePath(fileVO.getFileName());
            blogImageUploadPO.setTargetPath(imageUrl);
            blogImageUploadPO.setUserId(fileVO.getUserId());
            blogImageUploadMapper.insert(blogImageUploadPO);
        }

        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setUserId(fileVO.getUserId());
        resourcePO.setPath(imageUrl);
        resourcePO.setType(fileVO.getType());
        resourcePO.setSize(ossFile.getSize());
        resourcePO.setOriginalName(ossFile.getOriginalName());
        resourcePO.setStoreType("MinIO");

        save(resourcePO);

//        fileVO.getFile().getInputStream().close();

        return imageUrl;
    }



    /**
     * @Description 获取minio服务器中的图片
     * @param imageName
     * @return {@link R< OssFile> }
     * @Author yaoHui
     * @Date 2024/8/1
     */
    @Override
    public ResponseEntity<InputStreamResource> getImage(String imageName) {
        InputStream inputStream = ossTemplate.getOssFile(imageName);
        if(ObjectUtils.isEmpty(inputStream)){
            return ResponseEntity.ok().body(null);
        }
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // 根据文件类型设置正确的Content-Type
                .body(resource);
    }
}
