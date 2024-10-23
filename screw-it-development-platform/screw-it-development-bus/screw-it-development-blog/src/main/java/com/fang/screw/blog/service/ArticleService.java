package com.fang.screw.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.ArticleVO;
import com.fang.screw.domain.vo.BaseRequestVO;
import com.fang.screw.domain.vo.PageVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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

    /***
     * @Description 查询分类文章List
     * @return {@link PoetryResult<Map<Integer,List<ArticleVO>>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<Map<Integer, List<ArticleVO>>> getListSortArticle();

    /***
     * @Description 根据ID查询对应文章
     * @param id
     * @param password
     * @return {@link PoetryResult<ArticleVO> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<ArticleVO> getArticleById(Integer id,String password);

    /***
     * @Description 查询文章List
     * @param baseRequestVO
     * @return {@link PoetryResult<Page> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<Page> getListArticle(BaseRequestVO baseRequestVO);

    /***
     * @Description 通过分页读取页面内容
     * @param articleVOPage
     * @return {@link R< Page< ArticleVO>> }
     * @Author yaoHui
     * @Date 2024/10/23
     */
    R<Page<ArticleVO>> getPageArticle(@RequestBody PageVO<ArticleVO> articleVOPage);
}
