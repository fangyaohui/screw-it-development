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

    // 博客标题
    private String title;

    // 博客内容
    private String content;

    // 博客简要
    private String summary;

    // 博客作者
    private String author;

    // 博客所有图片保存服务器地址
    private String images;

    // 附件地址
    private String attachments;

    // 博客上传时间 或 创建时间
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 博客最后一次更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 博客浏览人数
    private Integer views;

    // 博客点赞人数
    private Integer likes;

    // 博客标签
    private Integer tags;

    // 博客状态
    private Integer status;

    // 博客访问类型
    private Integer accessControl;

    // 博客评论总数
    private Integer comments_count;

    // 博客类型
    private Integer category;

}