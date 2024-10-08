package com.fang.screw.domain.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * @FileName BlogInfoVO
 * @Description
 * @Author yaoHui
 * @date 2024-08-03
 **/
@Data
@Slf4j
public class BlogInfoVO implements Serializable {

    private String id;

    // 博客标题
    private String articleTitle;

    // 博客内容
    private String articleContent;

    // 博客简要
    private String articleAbstract;

    // 博客作者
    private Long userId;

    // 博客所有图片保存服务器地址
    private List<String> images;

    // 附件地址
    private String attachments;

    // 博客上传时间 或 创建时间
    private String createTime;

    // 博客最后一次更新时间
    private String updateTime;

    // 博客浏览人数
    private Integer views;

    // 博客点赞人数
    private Integer likes;

    // 博客标签
    private List<Integer> tags;

    // 博客状态 1-正常 0-异常
    private Integer status;

    // 博客访问类型 1-普通 2-VIP 3-付费
    private Integer accessControl;

    // 博客评论总数
    private Integer commentsCount;

    // 博客类型ID
    private List<Integer> categoryId;

}
