package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.ArticleVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @FileName ArticleService
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
public interface ArticleService {

    /***
     * @Description 保存博客内容
     * @param articleVO
     * @return {@link R }
     * @Author yaoHui
     * @Date 2024/9/21
     */
    R saveArticle(@RequestBody ArticleVO articleVO);
}
