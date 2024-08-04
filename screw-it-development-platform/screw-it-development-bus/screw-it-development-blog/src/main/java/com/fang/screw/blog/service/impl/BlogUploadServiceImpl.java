package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.blog.mapper.BlogImageUploadMapper;
import com.fang.screw.blog.mapper.BlogInfoMapper;
import com.fang.screw.blog.service.BlogUploadService;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.ImageDetermineUtils;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import po.BlogImageUploadPO;
import po.BlogInfoPO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.UploadConstant.*;

/**
 * @FileName BlogUploadServiceImpl
 * @Description 博客上传等业务逻辑处理模块
 * @Author yaoHui
 * @date 2024-07-31
 **/
@Service
@AllArgsConstructor
@Slf4j
public class BlogUploadServiceImpl implements BlogUploadService {

    private BlogInfoMapper blogInfoMapper;

    private OssTemplate ossTemplate;

    private BlogImageUploadMapper blogImageUploadMapper;

    /**
     * @Description 上传MD文件博客
     * @param file
     * @return {@link R < Boolean> }
     * @Author yaoHui
     * @Date 2024/7/31
     */
    @Override
    public R<String> uploadMDFile(MultipartFile file) throws IOException {

//        log.info(file.toString());
//        log.info(file.getName()); file
//        log.info(file.getContentType()); text/markdown
//        log.info(String.valueOf(file.getInputStream())); java.io.FileInputStream@11a5941c
//        log.info(file.getOriginalFilename()); 02 Nexus.md
//        log.info(String.valueOf(file.getBytes())); [B@7774a93f
//        log.info(String.valueOf(file.getResource())); MultipartFile resource [file]
//        log.info(String.valueOf(file.getSize())); 1708

        // 限制上传文件大小 不能为空 OR 超过10MB
        if(ObjectUtils.isEmpty(file) || file.getSize() > BLOG_UPLOAD_MD_FILE_MAX_SIZE){
            // 清理临时缓存
            file.getInputStream().close();
            return R.failed("上传文件不能为空或大小超过10MB");
        }

        // 检查MIME类型
        String mimeType = file.getContentType();
        String fileName = file.getOriginalFilename();
        if (!"text/markdown".equals(mimeType) && !"text/plain".equals(mimeType) &&
                "application/octet-stream".equals(mimeType)) {
            assert fileName != null;
            if (!fileName.endsWith(".md")) {
                // 清理临时缓存
                file.getInputStream().close();
                return R.failed("上传的文件类型必须为markdown格式");
            }
        }

        // 读取上传的文件内容
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 替换MD文件中所有图片地址
        Map<String,String> imagePath =  blogImageUploadMapper.selectList(Wrappers.<BlogImageUploadPO> lambdaQuery()
                .eq(BlogImageUploadPO::getUserId,CurrentUserHolder.getUser().getId()))
                .stream()
                .collect(Collectors.toMap(BlogImageUploadPO::getSourcePath,BlogImageUploadPO::getTargetPath));
        String content = ImageDetermineUtils.replaceImageUrls(contentBuilder.toString(),imagePath);
        List<String> imagePathList = ImageDetermineUtils.extractImageUrls(content);

        // 保存博客基本信息
        BlogInfoPO blogInfoPO = new BlogInfoPO();
        blogInfoPO.init();
        blogInfoPO.setAuthor(CurrentUserHolder.getUser().getNickName());
        assert fileName != null;
        blogInfoPO.setTitle(fileName.substring(0,fileName.length()-3));
        blogInfoPO.setSummary(content.substring(0,Math.min(200,content.length())));
        blogInfoPO.setContent(content);
        blogInfoPO.setImages(imagePathList);
        blogInfoMapper.save(blogInfoPO);

        // 清理临时缓存
        file.getInputStream().close();

        return R.ok("文件上传成功");
    }

    /**
     * @Description 上传博客图片
     * @param file
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/7/31
     */
    @Override
    public R<String> uploadBlogImage(MultipartFile file) throws IOException {

        // 限制上传文件大小 不能为空 OR 超过10MB
        if(ObjectUtils.isEmpty(file) || file.getSize() > BLOG_UPLOAD_IMAGE_MAX_SIZE){
            // 清理临时缓存
            file.getInputStream().close();
            return R.failed("上传文件不能为空或大小超过10MB");
        }

        // 判断上传的文件是图片
        if(!ImageDetermineUtils.isImageFile(file)){
            file.getInputStream().close();
            return R.failed("只能上传图片，请确保上传的是图片");
        }

        // 文件名字格式化
        String fileName = file.getOriginalFilename();
        if(StringUtils.isNotEmpty(fileName) && fileName.startsWith("./")){
            fileName = fileName.substring(2);
        }

        // 判断用户是否上传过这个文件
        if(blogImageUploadMapper.exists(Wrappers.<BlogImageUploadPO>lambdaQuery()
                .eq(BlogImageUploadPO::getUserId,CurrentUserHolder.getUser().getId())
                .eq(BlogImageUploadPO::getSourcePath,fileName))){
            return R.ok("上传成功");
        }

        String imageName = UUID.randomUUID().toString();
        OssFile ossFile = ossTemplate.upLoadFile(BLOG_IMAGE_UPLOAD_FOLDER_NAME,imageName,file);

        // 保存用户上传过的图片信息
        BlogImageUploadPO blogImageUploadPO = new BlogImageUploadPO();
        blogImageUploadPO.setImgSize(ossFile.getSize());
        blogImageUploadPO.setSourcePath(fileName);
        blogImageUploadPO.setTargetPath(ossFile.getFilePath());
        blogImageUploadPO.setUserId(CurrentUserHolder.getUser().getId());
        blogImageUploadMapper.insert(blogImageUploadPO);

        file.getInputStream().close();

        return R.ok("上传成功");
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
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // 根据文件类型设置正确的Content-Type
                .body(resource);
    }
}
