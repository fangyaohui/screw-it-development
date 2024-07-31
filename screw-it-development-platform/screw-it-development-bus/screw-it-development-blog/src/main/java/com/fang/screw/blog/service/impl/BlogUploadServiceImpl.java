package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fang.screw.blog.mapper.BlogInfoMapper;
import com.fang.screw.blog.service.BlogUploadService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import po.BlogInfoPO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.fang.screw.communal.constant.UploadConstant.BLOG_UPLOAD_MD_FILE_MAX_SIZE;

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
}