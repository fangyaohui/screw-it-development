package po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BlogInfoPO
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
@Data
@Document(indexName = "blog_info")
public class BlogInfoPO {

    @Id
    private String id;

    private String title;

    private String content;

    private String summary;

    private String author;

    private String images;

    private String attachments;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer views;

    private Integer likes;

    private Integer tags;

    private Integer status;

    private Integer accessControl;

    private Integer comments_count;

    private Integer category;

}