package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fang.screw.blog.mapper.BlogInfoMapper;
import com.fang.screw.blog.service.BlogUploadService;
import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.ImageDetermineUtils;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import po.BlogInfoPO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

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
        if (!"text/markdown".equals(mimeType) && !"text/plain".equals(mimeType)) {
            // 清理临时缓存
            file.getInputStream().close();
            return R.failed("上传的文件类型必须为markdown格式");
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

        BlogInfoPO blogInfoPO = new BlogInfoPO();
        blogInfoPO.setContent(contentBuilder.toString());

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
    public R<OssFile> uploadImage(MultipartFile file) throws IOException {

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

        String imageName = UUID.randomUUID().toString();
        OssFile ossFile = ossTemplate.upLoadFile(BLOG_IMAGE_UPLOAD_FOLDER_NAME,imageName,file);

        file.getInputStream().close();

        return R.ok(ossFile,"上传成功");
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