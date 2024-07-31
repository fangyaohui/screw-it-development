package com.fang.screw.communal.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.io.IOException;

import static com.fang.screw.communal.constant.UploadConstant.IMAGE_EXTENSIONS;
import static com.fang.screw.communal.constant.UploadConstant.IMAGE_MIME_TYPES;
import static com.fang.screw.communal.template.impl.MinIOTemplate.getFileExtension;

/**
 * @FileName ImageDetermineUtils
 * @Description
 * @Author yaoHui
 * @date 2024-07-31
 **/
public class ImageDetermineUtils {

    /**
    * @Description 判断即将上传的文件是否是图片类型
    * @param file
    * @return {@link Boolean }
    * @Author yaoHui
    * @Date 2024/7/31
    */
    public static Boolean isImageFile(MultipartFile file) throws IOException {
        String mimeType = file.getContentType();
        if (!IMAGE_MIME_TYPES.contains(mimeType)) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        if (fileName != null && !ObjectUtils.isEmpty(fileName)) {
            String fileExtension = getFileExtension(fileName);
            if (!IMAGE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                return false;
            }
        }

        return ImageIO.read(file.getInputStream()) != null;
    }

}