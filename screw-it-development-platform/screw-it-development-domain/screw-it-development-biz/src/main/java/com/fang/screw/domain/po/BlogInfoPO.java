package com.fang.screw.domain.po;

import com.fang.screw.domain.vo.BlogHeadlineVO;
import com.fang.screw.domain.vo.BlogInfoVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public void init(){
        List<Integer> list = new ArrayList<>();
        list.add(0);
        this.setLikes(0);
        this.setStatus(1);
        this.setTags(list);
        this.setAccessControl(1);
        this.setViews(0);
        this.setLikes(0);
        this.setCommentsCount(0);
        this.setCategoryId(list);
        this.setCreateTime(getCurrentTimeStr());
        this.setUpdateTime(getCurrentTimeStr());
        this.setAttachments(null);
    }

    public String getCurrentTimeStr() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public BlogInfoVO transformVO(){
        BlogInfoVO blogInfoVO = new BlogInfoVO();
        BeanUtils.copyProperties(this,blogInfoVO);
        return blogInfoVO;
    }

    public BlogHeadlineVO transformBlogHeadlineVO(){
        BlogHeadlineVO blogHeadlineVO = new BlogHeadlineVO();
        BeanUtils.copyProperties(this,blogHeadlineVO);
        return blogHeadlineVO;
    }

}
