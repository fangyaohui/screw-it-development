package com.fang.screw.blog.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.blog.mapper.CommentMapper;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.BlogCommentPO;
import com.fang.screw.domain.vo.BlogCommentVO;

import java.util.List;

/**
 * @FileName CommentService
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
public interface CommentService extends IService<BlogCommentPO> {

    /**
     * @Description 添加博客评论
     * @param blogCommentVO
     * @return {@link R< Boolean> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    R<Boolean> addBlogComment(BlogCommentVO blogCommentVO);

    /**
     * @Description 根据blogId查询其评论树，使用的是hutool工具
     * @param blogId
     * @return {@link R< List< Tree< Long>>> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    R<List<Tree<Long>>> getCommentTreeByBlogId(Long blogId);

}