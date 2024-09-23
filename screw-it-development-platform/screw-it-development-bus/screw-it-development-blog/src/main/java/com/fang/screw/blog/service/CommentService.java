package com.fang.screw.blog.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.blog.mapper.CommentMapper;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.BlogCommentPO;
import com.fang.screw.domain.po.CommentPO;
import com.fang.screw.domain.vo.BaseRequestVO;
import com.fang.screw.domain.vo.BlogCommentVO;
import com.fang.screw.domain.vo.CommentVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @FileName CommentService
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
public interface CommentService extends IService<CommentPO> {


    /***
     * @Description 查询评论
     * @param baseRequestVO
     * @return {@link PoetryResult<BaseRequestVO> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    R<BaseRequestVO> getListComment(@RequestBody BaseRequestVO baseRequestVO);

    /***
     * @Description 查询评论数量
     * @param source
     * @param type
     * @return {@link PoetryResult<Integer> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    R<Integer> getCommentCount(Integer source, String type);

    /***
     * @Description 保存评论
     * @param commentVO
     * @return {@link PoetryResult }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    R<String> saveComment(CommentVO commentVO);

}
