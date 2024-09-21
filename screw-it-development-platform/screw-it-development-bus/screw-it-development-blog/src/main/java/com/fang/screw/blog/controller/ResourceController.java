package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.ResourceService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.FileVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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


}
