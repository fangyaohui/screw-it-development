package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BlogCommentPO
 * @Description 用户-评论
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Data
@TableName(value = "blog_comment")
public class BlogCommentPO {

    // ID
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    // 用户ID
    private Long userId;

    // 博客ID
    private Long blogId;

    // 评论
    private String comment;

    // 回复评论ID 若-1则是回复当前博客
    private Long parentId;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer status;

    private Integer delFlag;

}
