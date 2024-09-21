package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.BlogService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.TopAndFeaturedBlogVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.fang.screw.domain.vo.BlogHeadlineVO;
import com.fang.screw.domain.vo.BlogInfoVO;

import java.util.List;

/**
 * @FileName BlogController
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
@RestController
@RequestMapping("/blog")
@Slf4j
@AllArgsConstructor
public class BlogController {

    private BlogService blogService;



//    /***
//    * @Description 返回头条五条博客简要信息
//    * @return {@link com.fang.screw.communal.utils.R<java.util.List< BlogInfoVO >> }
//    * @Author yaoHui
//    * @Date 2024/8/3
//    */
//    @RequestMapping("/getHeadlineBriefInfo")
//    public R<List<BlogHeadlineVO>> getHeadlineBriefInfo(){
//        return blogService.getHeadlineBriefInfo();
//    }
//
//    /***
//    * @Description 根据博客ID查询博客的详细信息
//    * @param blogId
//    * @return {@link R< BlogInfoVO> }
//    * @Author yaoHui
//    * @Date 2024/8/4
//    */
//    @GetMapping("/getBlogInfoByBlogId")
//        public R<BlogInfoVO> getBlogInfoByBlogId(@RequestParam("blogId") String blogId){
//        return blogService.getBlogInfoByBlogId(blogId);
//    }
//
//    /***
//    * @Description 获取所有的博客详细内容
//    * @return {@link com.fang.screw.communal.utils.R<java.util.List<com.fang.screw.domain.vo.BlogInfoVO>> }
//    * @Author yaoHui
//    * @Date 2024/9/6
//    */
//    @GetMapping("/getAllBlogInfo")
//    public R<List<BlogInfoVO>> getAllBlogInfo(){
//        return blogService.getAllBlogInfo();
//    }
//
//    /***
//    * @Description 获取置顶和推荐文章
//    * @return {@link R< TopAndFeaturedBlogVO> }
//    * @Author yaoHui
//    * @Date 2024/9/7
//    */
//    @GetMapping("/getTopAndFeaturedBlog")
//    public R<TopAndFeaturedBlogVO> getTopAndFeaturedBlog(){
//        return blogService.getTopAndFeaturedBlog();
//    }
//
//


}
