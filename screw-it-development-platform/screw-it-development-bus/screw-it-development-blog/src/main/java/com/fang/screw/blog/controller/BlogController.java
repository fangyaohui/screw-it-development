package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.BlogService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.template.OssTemplate;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vo.BlogInfoVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
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

    @RequestMapping("/test")
    public R<String> test(){
        log.info(CurrentUserHolder.getUser().toString());
        return R.ok("test");
    }

    /***
    * @Description 返回头条五条博客简要信息
    * @return {@link com.fang.screw.communal.utils.R<java.util.List<vo.BlogInfoVO>> }
    * @Author yaoHui
    * @Date 2024/8/3
    */
    @RequestMapping("/getHeadlineBriefInfo")
    public R<List<BlogInfoVO>> getHeadlineBriefInfo(){
        return blogService.getHeadlineBriefInfo();
    }

    /***
    * @Description 根据博客ID查询博客的详细信息
    * @param blogId
    * @return {@link R< BlogInfoVO> }
    * @Author yaoHui
    * @Date 2024/8/4
    */
    @GetMapping("/getBlogInfoByBlogId")
    public R<BlogInfoVO> getBlogInfoByBlogId(@RequestParam("blogId") String blogId){
        return blogService.getBlogInfoByBlogId(blogId);
    }
}
