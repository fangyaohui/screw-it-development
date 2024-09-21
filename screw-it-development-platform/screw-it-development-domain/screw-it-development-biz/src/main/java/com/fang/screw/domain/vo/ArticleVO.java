package com.fang.screw.domain.vo;

import com.fang.screw.domain.po.ArticlePO;
import com.fang.screw.domain.po.LabelPO;
import com.fang.screw.domain.po.SortPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ArticleVO {

    private Integer id;

    private Integer userId;

    //查询为空时，随机选择
    private String articleCover;

    private String articleTitle;

    private String articleContent;

    private Integer viewCount;

    private Integer likeCount;

    private Boolean commentStatus;

    private Boolean recommendStatus;

    private String videoUrl;

    private String password;

    private String tips;

    private Boolean viewStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String updateBy;

    private Integer sortId;

    private Integer labelId;

    // 需要查询封装
    private Integer commentCount;
    private String username;
    private SortPO sort;
    private LabelPO label;
    private Boolean hasVideo = false;

    public ArticlePO transformPO(){
        ArticlePO articlePO = new ArticlePO();
        BeanUtils.copyProperties(this,articlePO);
        return articlePO;
    }
}
