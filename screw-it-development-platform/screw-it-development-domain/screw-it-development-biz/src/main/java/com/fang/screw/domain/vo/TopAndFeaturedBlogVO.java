package com.fang.screw.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @FileName TopAndFeaturedBlogVO
 * @Description
 * @Author yaoHui
 * @date 2024-09-07
 **/
@Data
public class TopAndFeaturedBlogVO {

    private BlogInfoVO topArticle;

    private List<BlogInfoVO> featuredArticles;
}
