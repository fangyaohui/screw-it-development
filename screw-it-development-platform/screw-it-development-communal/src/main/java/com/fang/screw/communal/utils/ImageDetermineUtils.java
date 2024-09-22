package com.fang.screw.communal.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /***
    * @Description 查询到文档中的所有图片地址并进行返回
    * @param markdownContent
    * @return {@link List<String> }
    * @Author yaoHui
    * @Date 2024/8/2
    */
    public static List<String> extractImageUrls(String markdownContent) {
        List<String> imageUrls = new ArrayList<>();
        Pattern pattern = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(markdownContent);
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }
        return imageUrls;
    }

    /***
    * @Description 替换图片地址
    * @param markdownContent
    * @param newImageUrl
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/8/2
    */
    public static String replaceImageUrls(String markdownContent, Map<String,String> imagePathMap) {

        // 正则表达式匹配 ![img](assets/xxx.png)
//        String regex = "!\\[img\\]\\(assets/(.*?)\\)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(markdownContent);

        Pattern pattern = Pattern.compile("!\\[[^]]*]\\(([^)]+)\\)");
        Matcher matcher = pattern.matcher(markdownContent);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String imagePath = matcher.group(1);
            imagePath = getFileName(imagePath);
            if(StringUtils.isNotEmpty(imagePath) && imagePath.startsWith("./")){
                imagePath = imagePath.substring(2);
            }
            matcher.appendReplacement(sb, "![img](" + imagePathMap.getOrDefault(imagePath,imagePath) + ")");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /***
    * @Description 截取路径最后的名称
    * @param filePath
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/8/2
    */
    public static String getFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        int lastIndex = filePath.lastIndexOf('/');
        if (lastIndex == -1) {
            return filePath;  // 如果没有找到斜杠，返回整个字符串
        }
        return filePath.substring(lastIndex + 1);
    }

}
