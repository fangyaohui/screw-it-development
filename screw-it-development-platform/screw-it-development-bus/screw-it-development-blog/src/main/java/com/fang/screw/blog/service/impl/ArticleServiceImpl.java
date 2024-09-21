package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.ArticleMapper;
import com.fang.screw.blog.service.ArticleService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.ArticlePO;
import com.fang.screw.domain.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @FileName ArticelServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,ArticlePO> implements ArticleService {

    /***
     * @Description 保存博客内容
     * @param articleVO
     * @return {@link R }
     * @Author yaoHui
     * @Date 2024/9/21
     */
    @Override
    public R saveArticle(ArticleVO articleVO) {

        // 如果把这个文章设置了不可见却又没有设置密码 则需要设置密码
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && !StringUtils.hasText(articleVO.getPassword())) {
            return R.fail("请设置文章密码！");
        }

        ArticlePO articlePO = new ArticlePO();
        if (StringUtils.hasText(articleVO.getArticleCover())) {
            articlePO.setArticleCover(articleVO.getArticleCover());
        }
        if (StringUtils.hasText(articleVO.getVideoUrl())) {
            articlePO.setVideoUrl(articleVO.getVideoUrl());
        }
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && StringUtils.hasText(articleVO.getPassword())) {
            articlePO.setPassword(articleVO.getPassword());
            articlePO.setTips(articleVO.getTips());
        }
        articlePO.setViewStatus(articleVO.getViewStatus());
        articlePO.setCommentStatus(articleVO.getCommentStatus());
        articlePO.setRecommendStatus(articleVO.getRecommendStatus());
        articlePO.setArticleTitle(articleVO.getArticleTitle());
        articlePO.setArticleContent(articleVO.getArticleContent());
        articlePO.setSortId(articleVO.getSortId());
        articlePO.setLabelId(articleVO.getLabelId());
        Integer userId = CurrentUserHolder.getUser().getId();
        articlePO.setUserId(userId);

        // 处理上传博客内的图片地址
        // TODO 处理博客地址

        save(articlePO);

        return R.success();
    }
}
