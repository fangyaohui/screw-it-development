package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.FileVO;
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

}
