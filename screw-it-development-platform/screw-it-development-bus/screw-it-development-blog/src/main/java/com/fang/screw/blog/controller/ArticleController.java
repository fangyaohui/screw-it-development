package com.fang.screw.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.screw.blog.service.ArticleService;
import com.fang.screw.communal.annotation.CheckPermission;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.ArticleVO;
import com.fang.screw.domain.vo.BaseRequestVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /***
    * @Description 查询分类文章List
    * @return {@link PoetryResult<Map<Integer,List<ArticleVO>>> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @CheckPermission
    @GetMapping("/getListSortArticle")
    public R<Map<Integer, List<ArticleVO>>> getListSortArticle() {
        return articleService.getListSortArticle();
    }

    /***
    * @Description 根据ID查询对应文章
    * @param id
    * @param password
    * @return {@link PoetryResult<ArticleVO> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @GetMapping("/getArticleById")
    public R<ArticleVO> getArticleById(@RequestParam("id") Integer id, @RequestParam(value = "password", required = false) String password) {
        return articleService.getArticleById(id, password);
    }

    /***
    * @Description 查询文章List
    * @param baseRequestVO
    * @return {@link PoetryResult<Page> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @PostMapping("/getListArticle")
    public R<Page> getListArticle(@RequestBody BaseRequestVO baseRequestVO) {
        return articleService.getListArticle(baseRequestVO);
    }

}
