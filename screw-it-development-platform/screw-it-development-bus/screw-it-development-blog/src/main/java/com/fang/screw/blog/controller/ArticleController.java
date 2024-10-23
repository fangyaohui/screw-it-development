package com.fang.screw.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.screw.blog.service.ArticleService;
import com.fang.screw.communal.annotation.Cacheable;
import com.fang.screw.communal.annotation.CheckPermission;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.ArticleVO;
import com.fang.screw.domain.vo.BaseRequestVO;
import com.fang.screw.domain.vo.PageVO;
import com.fang.screw.domain.vo.UserBlogInfoVO;
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
//    @CheckPermission
    @GetMapping("/getListSortArticle/{userId}")
    @Cacheable(value = "article:getListSortArticle",key = "#userId")
    public R<Map<Integer, List<ArticleVO>>> getListSortArticle(@PathVariable("userId") Integer userId) {
        return articleService.getListSortArticle(userId);
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
    @Cacheable(value = "article:getArticleById",key = "#id")
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

    /***
    * @Description 通过分页读取页面内容
    * @param articleVOPage
    * @return {@link R< Page< ArticleVO>> }
    * @Author yaoHui
    * @Date 2024/10/23
    */
    @PostMapping("/getPageArticle")
    @Cacheable(value = "page:article",key = "#0.current:#0.keyWord")
    public R<Page<ArticleVO>> getPageArticle(@RequestBody PageVO<ArticleVO> articleVOPage){
        return articleService.getPageArticle(articleVOPage);
    }

    /***
     * @Description 通过用户ID分页读取页面内容
     * @param articleVOPage
     * @return {@link R< Page< ArticleVO>> }
     * @Author yaoHui
     * @Date 2024/10/23
     */
    @PostMapping("/getPageArticleByUserId")
    public R<Page<ArticleVO>> getPageArticleByUserId(@RequestBody PageVO<ArticleVO> articleVOPage){
        return articleService.getPageArticleByUserId(articleVOPage);
    }

    /***
     * @Description 获取用户文章数、点赞数和访问数
     * @param articleVOPage
     * @return {@link R< Page< ArticleVO>> }
     * @Author yaoHui
     * @Date 2024/10/23
     */
    @PostMapping("/getUserBlogInfo")
    public R<UserBlogInfoVO> getUserBlogInfo(){
        return articleService.getUserBlogInfo();
    }

}
