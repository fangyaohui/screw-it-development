package com.fang.screw.communal.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @FileName BlogUploadConstant
 * @Description 上传文件常量
 * @Author yaoHui
 * @date 2024-07-31
 **/
public class UploadConstant {

    // 限制MD文件上传最大不超过10MB
    public final static Long BLOG_UPLOAD_MD_FILE_MAX_SIZE = 10*1024*1024L;

    // 限制博客图片上传最大不超过10MB
    public final static Long BLOG_UPLOAD_IMAGE_MAX_SIZE = 10*1024*1024*1024L;

    // 图片MIME类型
    public static final Set<String> IMAGE_MIME_TYPES = new HashSet<String>() {{
        add("image/jpeg");
        add("image/png");
        add("image/gif");
        add("image/bmp");
        add("image/webp");
        add("image/tiff");
    }};

    // 图片拓展名类型
    public static final Set<String> IMAGE_EXTENSIONS = new HashSet<String>() {{
        add("jpg");
        add("jpeg");
        add("png");
        add("gif");
        add("bmp");
        add("webp");
        add("tiff");
    }};

    // 博客图片上传minio文件夹名称
    public static final String BLOG_IMAGE_UPLOAD_FOLDER_NAME = "blog_image_upload_folder_path";
}
