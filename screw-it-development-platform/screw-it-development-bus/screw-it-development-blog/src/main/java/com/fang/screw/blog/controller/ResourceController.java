package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.ResourceService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.fang.screw.communal.constant.UploadConstant.BLOG_IMAGE_UPLOAD_FOLDER_NAME;

/**
 * @FileName ResourceController
 * @Description 资源保存控制层
 * @Author yaoHui
 * @date 2024-09-21
 **/
@RestController
@RequestMapping("/resource")
@AllArgsConstructor
@Slf4j
public class ResourceController {

    private ResourceService resourceService;

    /***
    * @Description
    * @param file
    * @param fileVO
    * @return {@link PoetryResult<String> }
    * @Author yaoHui
    * @Date 2024/9/21
    */
    @PostMapping("/uploadImage")
    public R<String> uploadImage(@RequestParam("file") MultipartFile file, FileVO fileVO) {
        return resourceService.uploadImage(file,fileVO);
    }

    /**
    * @Description 获取minio服务器中的图片
    * @param imageName
    * @return {@link R< OssFile> }
    * @Author yaoHui
    * @Date 2024/8/1
    */
    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("imageName") String imageName){
        if(StringUtils.isEmpty(imageName)){
            return ResponseEntity.ok().body(null);
        }
        return resourceService.getImage(BLOG_IMAGE_UPLOAD_FOLDER_NAME +"/" + imageName);
    }


}
