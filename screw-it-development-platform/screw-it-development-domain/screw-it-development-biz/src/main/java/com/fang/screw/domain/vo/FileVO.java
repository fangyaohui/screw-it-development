package com.fang.screw.domain.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileVO {

    private String type;

    private String storeType;

    private String relativePath;

    private String absolutePath;

    private String visitPath;

//    private MultipartFile file;
    // 转为 Base64 以便通过 JSON 传递文件内容
    private String fileContent;

    private String originalName;

    private String fileName;

    private Integer userId;
}
