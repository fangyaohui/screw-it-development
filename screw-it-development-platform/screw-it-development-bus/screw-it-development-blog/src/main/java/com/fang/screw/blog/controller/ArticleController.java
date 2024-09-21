package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.ArticleService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.ArticleVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName ArticleController
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@RestController
@RequestMapping("/article")
@Slf4j
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    /***
    * @Description 保存博客内容
    * @param articleVO
    * @return {@link R }
    * @Author yaoHui
    * @Date 2024/9/21
    */
    @PostMapping("/saveArticle")
    public R saveArticle(@RequestBody ArticleVO articleVO){

        return articleService.saveArticle(articleVO);
    }

}
