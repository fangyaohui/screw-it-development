package com.fang.screw.domain.dto;

import com.fang.screw.domain.vo.FileVO;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @FileName UploadImageDTO
 * @Description
 * @Author yaoHui
 * @date 2024-10-31
 **/
@Data
public class UploadImageDTO {

    private String type;

    private String storeType;

    private String relativePath;

    private String absolutePath;

    private String visitPath;

    private MultipartFile file;

    private String originalName;
}
