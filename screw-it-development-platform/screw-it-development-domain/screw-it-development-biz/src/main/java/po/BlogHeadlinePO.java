package po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BlogHeadlinePO
 * @Description 头条博客
 * @Author yaoHui
 * @date 2024-08-04
 **/
@Data
@TableName(value = "blog_headline")
public class BlogHeadlinePO{

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    // 博客ID
    private String blogId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Integer status;

    private Integer delFlag;

    private String description;

}
