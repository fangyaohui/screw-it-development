package com.fang.screw.blog.service;

import com.fang.screw.communal.entity.OssFile;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.FileVO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @FileName ResourceService
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
public interface ResourceService {


    R<String> uploadImage(@RequestParam("file") MultipartFile file, FileVO fileVO);

    /**
     * @Description 获取minio服务器中的图片
     * @param imageName
     * @return {@link R<  OssFile > }
     * @Author yaoHui
     * @Date 2024/8/1
     */
    ResponseEntity<InputStreamResource> getImage(String imageName);

}
