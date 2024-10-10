package com.fang.screw.blog.controller;

import cn.hutool.core.lang.tree.Tree;
import com.fang.screw.blog.service.CommentService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.StringUtil;
import com.fang.screw.domain.vo.BaseRequestVO;
import com.fang.screw.domain.vo.BlogCommentVO;
import com.fang.screw.domain.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName CommentController
 * @Description 评论-控制层
 * @Author yaoHui
 * @date 2024-08-05
 **/
@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    /***
    * @Description 查询评论
    * @param baseRequestVO
    * @return {@link PoetryResult<BaseRequestVO> }
    * @Author yaoHui
    * @Date 2024/9/23
    */
    @PostMapping("/getListComment")
    public R<BaseRequestVO> getListComment(@RequestBody BaseRequestVO baseRequestVO) {
        return commentService.getListComment(baseRequestVO);
    }

    /***
    * @Description 查询评论数量
    * @param source
    * @param type
    * @return {@link PoetryResult<Integer> }
    * @Author yaoHui
    * @Date 2024/9/23
    */
    @GetMapping("/getCommentCount")
    public R<Integer> getCommentCount(@RequestParam("source") Integer source, @RequestParam("type") String type) {
        return commentService.getCommentCount(source, type);
    }

    /***
    * @Description 保存评论
    * @param commentVO
    * @return {@link PoetryResult }
    * @Author yaoHui
    * @Date 2024/9/23
    */
    @PostMapping("/saveComment")
    public R<String> saveComment(@Validated @RequestBody CommentVO commentVO) {
        String content = StringUtil.removeHtml(commentVO.getCommentContent());
        if (!StringUtils.hasText(content)) {
            return R.fail("评论内容不合法！");
        }
        commentVO.setCommentContent(content);

        return commentService.saveComment(commentVO);
    }

    /***
    * @Description 测试接口 测试Netty是否能够正常发送消息和接收消息
    * @param commentVO
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/10/10
    */
    @PostMapping("/testNettySendMessage")
    public R<String> testNettySendMessage(@RequestBody CommentVO commentVO){
        return commentService.testNettySendMessage(commentVO);
    }

}
