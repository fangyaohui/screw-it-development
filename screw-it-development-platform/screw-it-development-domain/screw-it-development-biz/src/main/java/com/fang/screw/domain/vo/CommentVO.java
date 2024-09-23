package com.fang.screw.domain.vo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CommentVO {

    private Integer id;

    private Integer source;

    /**
     * 评论来源类型
     */
    private String type;

    //层主的parentCommentId是0，回复的parentCommentId是层主的id
    private Integer parentCommentId;

    //层主的parentUserId是null，回复的parentUserId是被回复的userId
    private Integer parentUserId;

    private Integer userId;

    private Integer likeCount;

    private String commentContent;

    private String commentInfo;

    //子评论必须传评论楼层ID
    private Integer floorCommentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 需要查询封装
    private Page childComments;
    private String parentUsername;
    private String username;
    private String avatar;
}
