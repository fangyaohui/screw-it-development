package po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @FileName blogImageUpload
 * @Description
 * @Author yaoHui
 * @date 2024-08-02
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "blog_image_upload")
public class BlogImageUploadPO extends BasePO{

    // 用户ID
    private Long userId;

    // 图片源地址
    private String sourcePath;

    // 图片目标地址
    private String targetPath;

    // 图片大小
    private Long imgSize;

}
