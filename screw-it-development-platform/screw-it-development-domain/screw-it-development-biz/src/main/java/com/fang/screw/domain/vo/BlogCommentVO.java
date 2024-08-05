package com.fang.screw.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fang.screw.domain.po.BlogCommentPO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BlogCommentVO
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Data
public class BlogCommentVO {

    // ID
    private Long id;

    // 用户ID
    private Long userId;

    // 用户昵称
    private String nickName;

    // 回复者昵称
    private String replyNickName;

    // 博客ID
    private Long blogId;

    // 评论
    private String comment;

    // 回复评论ID 若-1则是回复当前博客
    private Long parentId = -1L;

    private LocalDateTime createTime;

    private Integer status = 1;

    public BlogCommentPO transformToPO(){
        BlogCommentPO blogCommentPO = new BlogCommentPO();
        BeanUtils.copyProperties(this,blogCommentPO);
        return blogCommentPO;
    }

}