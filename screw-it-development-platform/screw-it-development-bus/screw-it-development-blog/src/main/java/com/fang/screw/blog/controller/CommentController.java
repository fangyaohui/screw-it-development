//package com.fang.screw.blog.controller;
//
//import cn.hutool.core.lang.tree.Tree;
//import com.fang.screw.blog.service.CommentService;
//import com.fang.screw.communal.utils.R;
//import com.fang.screw.domain.vo.BlogCommentVO;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * @FileName CommentController
// * @Description 评论-控制层
// * @Author yaoHui
// * @date 2024-08-05
// **/
//@AllArgsConstructor
//@RestController
//@Slf4j
//@RequestMapping("/blog/comment")
//public class CommentController {
//
//    private CommentService commentService;
//
//    /**
//    * @Description 添加博客评论
//    * @param blogCommentVO
//    * @return {@link R< Boolean> }
//    * @Author yaoHui
//    * @Date 2024/8/5
//    */
//    @PostMapping("/addBlogComment")
//    public R<Boolean> addBlogComment(@RequestBody BlogCommentVO blogCommentVO){
//        return commentService.addBlogComment(blogCommentVO);
//    }
//
//    /**
//    * @Description 根据blogId查询其评论树，使用的是hutool工具
//    * @param blogId
//    * @return {@link R< List< Tree< Long>>> }
//    * @Author yaoHui
//    * @Date 2024/8/5
//    */
//    @GetMapping("/getCommentByBlogId")
//    public R<List<Tree<Long>>> getCommentTreeByBlogId(@RequestParam("blogId") Long blogId) {
//        return commentService.getCommentTreeByBlogId(blogId);
//    }
//
//
//}
