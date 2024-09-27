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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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

    @Override
    public R<String> uploadImage(MultipartFile file, FileVO fileVO) {

        if (file == null || !StringUtils.hasText(fileVO.getType()) || !StringUtils.hasText(fileVO.getRelativePath())) {
            return R.fail("文件和资源类型和资源路径不能为空！");
        }

        fileVO.setFile(file);
        // TODO 根据上传来的参数指定其存储在哪
        // 目前只是默认存储在minio服务器中 后续有更改在进行修正
//        StoreService storeService = fileStorageService.getFileStorage(fileVO.getStoreType());

        // 保存文件到minio服务器中
        // 限制上传文件大小 不能为空 OR 超过10MB
        try{
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

            // 判断用户是否上传过这个文件 如果是上传的封面照片就不需要判断用户是否上传过了
            BlogImageUploadPO blogImageUploadPO = blogImageUploadMapper.selectOne(Wrappers.<BlogImageUploadPO>lambdaQuery()
                    .eq(BlogImageUploadPO::getUserId, CurrentUserHolder.getUser().getId())
                    .eq(BlogImageUploadPO::getSourcePath,fileName));
            if(!Objects.equals(fileVO.getType(), "articleCover") && !ObjectUtils.isEmpty(blogImageUploadPO)){
                return R.ok(blogImageUploadPO.getTargetPath());
            }

            String imageName = UUID.randomUUID().toString();
            OssFile ossFile = ossTemplate.upLoadFile(BLOG_IMAGE_UPLOAD_FOLDER_NAME,imageName,file);

            String imageKey = ossFile.getName().replaceFirst("/"+BLOG_IMAGE_UPLOAD_FOLDER_NAME,"");
            String imageUrl = IMG_WEB_SITE_URL + imageKey;

            if(!Objects.equals(fileVO.getType(),"articleCover")){
                // 保存用户上传过的图片信息
                blogImageUploadPO = new BlogImageUploadPO();
                blogImageUploadPO.setImgSize(ossFile.getSize());
                blogImageUploadPO.setSourcePath(fileName);
                blogImageUploadPO.setTargetPath(imageUrl);
                blogImageUploadPO.setUserId(CurrentUserHolder.getUser().getId());
                blogImageUploadMapper.insert(blogImageUploadPO);
            }

            ResourcePO resourcePO = new ResourcePO();
            resourcePO.setUserId(CurrentUserHolder.getUser().getId());
            resourcePO.setPath(imageUrl);
            resourcePO.setType(fileVO.getType());
            resourcePO.setSize(ossFile.getSize());
            resourcePO.setOriginalName(ossFile.getOriginalName());
            resourcePO.setStoreType("MinIO");

            save(resourcePO);

            file.getInputStream().close();

            return R.success(imageUrl);
        }catch (Exception e){
            return R.fail("上传失败");
        }

//
//        Resource re = new Resource();
//        re.setPath(result.getVisitPath());
//        re.setType(fileVO.getType());
//        re.setSize(Integer.valueOf(Long.toString(file.getSize())));
//        re.setMimeType(file.getContentType());
//        re.setStoreType(fileVO.getStoreType());
//        re.setOriginalName(fileVO.getOriginalName());
//        re.setUserId(PoetryUtil.getUserId());
//        resourceService.save(re);
//        return PoetryResult.success(result.getVisitPath());

//        return null;
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
