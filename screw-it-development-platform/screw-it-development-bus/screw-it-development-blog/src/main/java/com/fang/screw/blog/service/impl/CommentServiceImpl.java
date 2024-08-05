package com.fang.screw.blog.service.impl;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.CommentMapper;
import com.fang.screw.blog.service.CommentService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.TreeBuildUtils;
import com.fang.screw.domain.po.BlogCommentPO;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.BlogCommentVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @FileName CommentServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, BlogCommentPO> implements CommentService {


    /**
     * @Description 添加博客评论
     * @param blogCommentVO
     * @return {@link R< Boolean> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    @Override
    public R<Boolean> addBlogComment(BlogCommentVO blogCommentVO) {

        BlogUserPO blogUserPO = CurrentUserHolder.getUser();
        if(ObjectUtils.isEmpty(blogUserPO)){
            return R.failed(false,"请重新登录");
        }

        BlogCommentPO blogCommentPO = blogCommentVO.transformToPO();
        blogCommentPO.setUserId(blogUserPO.getId());
        blogCommentPO.setNickName(blogUserPO.getNickName());

        return R.ok(save(blogCommentPO));
    }

    /**
     * @Description 根据blogId查询其评论树，使用的是hutool工具
     * @param blogId
     * @return {@link R< List< Tree< Long>>> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    @Override
    public R<List<Tree<Long>>> getCommentTreeByBlogId(Long blogId) {

        if(ObjectUtils.isEmpty(blogId)){
            return R.failed("博客不存在，请稍后再试");
        }

        List<BlogCommentPO> blogCommentPOList = baseMapper.selectList(Wrappers.<BlogCommentPO>lambdaQuery()
                .eq(BlogCommentPO::getBlogId,blogId));
        if(blogCommentPOList.isEmpty()){
            return R.failed("该博客无评论");
        }

        try {
            return R.ok(TreeBuildUtils.buildTree(blogCommentPOList));
        } catch (IllegalAccessException e) {
            return R.failed("评论树构建失败");
        }
    }
}