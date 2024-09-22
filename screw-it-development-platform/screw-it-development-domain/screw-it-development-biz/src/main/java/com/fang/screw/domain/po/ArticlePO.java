package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fang.screw.domain.vo.ArticleVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author sara
 * @since 2021-09-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("article")
public class ArticlePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 分类ID
     */
    @TableField("sort_id")
    private Integer sortId;

    /**
     * 标签ID
     */
    @TableField("label_id")
    private Integer labelId;

    /**
     * 封面
     */
    @TableField("article_cover")
    private String articleCover;

    /**
     * 博文标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 博文内容
     */
    @TableField("article_content")
    private String articleContent;

    /**
     * 视频链接
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 提示
     */
    @TableField("tips")
    private String tips;

    /**
     * 是否可见[0:否，1:是]
     */
    @TableField("view_status")
    private Boolean viewStatus;

    /**
     * 浏览量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 是否启用评论[0:否，1:是]
     */
    @TableField("comment_status")
    private Boolean commentStatus;

    /**
     * 是否推荐[0:否，1:是]
     */
    @TableField("recommend_status")
    private Boolean recommendStatus;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最终修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 最终修改人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 是否启用[0:未删除，1:已删除]
     */
    @TableField("del_flag")
    private Integer delFlag;


    public ArticleVO transformVO(){
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(this,articleVO);
        return articleVO;
    }

//    private ArticleVO buildArticleVO(ArticlePO article, Boolean isAdmin) {
//        ArticleVO articleVO = new ArticleVO();
//        BeanUtils.copyProperties(article, articleVO);
//        if (!isAdmin) {
//            if (!StringUtils.hasText(articleVO.getArticleCover())) {
//                articleVO.setArticleCover(PoetryUtil.getRandomCover(articleVO.getId().toString()));
//            }
//        }
//
//        User user = commonQuery.getUser(articleVO.getUserId());
//        if (user != null && StringUtils.hasText(user.getUsername())) {
//            articleVO.setUsername(user.getUsername());
//        } else if (!isAdmin) {
//            articleVO.setUsername(PoetryUtil.getRandomName(articleVO.getUserId().toString()));
//        }
//        if (articleVO.getCommentStatus()) {
//            articleVO.setCommentCount(commonQuery.getCommentCount(articleVO.getId(), CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode()));
//        } else {
//            articleVO.setCommentCount(0);
//        }
//
//        List<Sort> sortInfo = commonQuery.getSortInfo();
//        if (!CollectionUtils.isEmpty(sortInfo)) {
//            for (Sort s : sortInfo) {
//                if (s.getId().intValue() == articleVO.getSortId().intValue()) {
//                    Sort sort = new Sort();
//                    BeanUtils.copyProperties(s, sort);
//                    sort.setLabels(null);
//                    articleVO.setSort(sort);
//                    if (!CollectionUtils.isEmpty(s.getLabels())) {
//                        for (int j = 0; j < s.getLabels().size(); j++) {
//                            Label l = s.getLabels().get(j);
//                            if (l.getId().intValue() == articleVO.getLabelId().intValue()) {
//                                Label label = new Label();
//                                BeanUtils.copyProperties(l, label);
//                                articleVO.setLabel(label);
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
//            }
//        }
//        return articleVO;
//    }

}
