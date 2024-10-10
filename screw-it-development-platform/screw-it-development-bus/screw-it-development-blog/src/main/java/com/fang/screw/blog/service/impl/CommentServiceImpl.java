package com.fang.screw.blog.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.feign.UserFeign;
import com.fang.screw.blog.mapper.ArticleMapper;
import com.fang.screw.blog.mapper.CommentMapper;
import com.fang.screw.blog.service.CommentService;
import com.fang.screw.client.component.NettyClient;
import com.fang.screw.client.protocol.MessageBase;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.TreeBuildUtils;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.enums.CodeMsg;
import com.fang.screw.domain.enums.CommentTypeEnum;
import com.fang.screw.domain.po.*;
import com.fang.screw.domain.vo.BaseRequestVO;
import com.fang.screw.domain.vo.BlogCommentVO;
import com.fang.screw.domain.vo.CommentVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.DynamicParameter.*;

/**
 * @FileName CommentServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Service
@AllArgsConstructor
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentPO> implements CommentService {

    private ArticleMapper articleMapper;

    private UserFeign userFeign;

    private CommentMapper commentMapper;

    private NettyClient nettyClient;

    /***
     * @Description 查询评论
     * @param baseRequestVO
     * @return {@link PoetryResult<BaseRequestVO> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    @Override
    public R<BaseRequestVO> getListComment(BaseRequestVO baseRequestVO) {

        if (baseRequestVO.getSource() == null || !StringUtils.hasText(baseRequestVO.getCommentType())) {
            return R.fail(CodeMsg.PARAMETER_ERROR);
        }

        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(baseRequestVO.getCommentType())) {
            ArticlePO articlePO = articleMapper.selectOne(Wrappers.<ArticlePO>lambdaQuery()
                    .eq(ArticlePO::getUserId,baseRequestVO.getSource())
                    .eq(ArticlePO::getCommentStatus,STATUS_OK));

            if (articlePO != null && !articlePO.getCommentStatus()) {
                return R.fail("评论功能已关闭！");
            }
        }

        if (baseRequestVO.getFloorCommentId() == null) {

            Page<CommentPO> commentPOPage = new Page<>();
            BeanUtils.copyProperties(baseRequestVO,commentPOPage);
            page(commentPOPage,Wrappers.<CommentPO>lambdaQuery()
                    .eq(CommentPO::getSource,baseRequestVO.getSource())
                    .eq(CommentPO::getType,baseRequestVO.getCommentType())
                    .eq(CommentPO::getParentCommentId,FIRST_COMMENT)
                    .eq(CommentPO::getDelFlag,NOT_DEL_FLAG)
                    .orderByDesc(CommentPO::getCreateTime));

            List<CommentPO> comments = commentPOPage.getRecords();
            if (CollectionUtils.isEmpty(comments)) {
                return R.success(baseRequestVO);
            }
            List<CommentVO> commentVOs = comments.stream().map(c -> {
                CommentVO commentVO = buildCommentVO(c);
                Page<CommentPO> page = new Page<>(1, 5);
                page(page,Wrappers.<CommentPO>lambdaQuery()
                        .eq(CommentPO::getSource,baseRequestVO.getSource())
                        .eq(CommentPO::getType,baseRequestVO.getCommentType())
                        .eq(CommentPO::getFloorCommentId,c.getId())
                        .eq(CommentPO::getDelFlag,NOT_DEL_FLAG)
                        .orderByAsc(CommentPO::getCreateTime));

                List<CommentPO> childComments = page.getRecords();
                Page<CommentVO> commentVOPage = new Page<>();
                BeanUtils.copyProperties(page,commentVOPage);
                if (childComments != null) {
                    List<CommentVO> ccVO = childComments.stream().map(this::buildCommentVO).collect(Collectors.toList());
                    commentVOPage.setRecords(ccVO);
                }
                commentVO.setChildComments(commentVOPage);
                return commentVO;
            }).collect(Collectors.toList());
            baseRequestVO.setRecords(commentVOs);
        } else {

            Page<CommentPO>page = new Page<>();
            BeanUtils.copyProperties(baseRequestVO,page);
            page(page,Wrappers.<CommentPO>lambdaQuery()
                    .eq(CommentPO::getSource,baseRequestVO.getSource())
                    .eq(CommentPO::getType,baseRequestVO.getCommentType())
                    .eq(CommentPO::getFloorCommentId,baseRequestVO.getFloorCommentId())
                    .eq(CommentPO::getDelFlag,NOT_DEL_FLAG)
                    .orderByAsc(CommentPO::getCreateTime));

            List<CommentPO> childComments = page.getRecords();
            if (CollectionUtils.isEmpty(childComments)) {
                return R.success(baseRequestVO);
            }
            List<CommentVO> ccVO = childComments.stream().map(cc -> buildCommentVO(cc)).collect(Collectors.toList());
            baseRequestVO.setRecords(ccVO);
        }
        return R.success(baseRequestVO);
    }

    /***
     * @Description 查询评论数量
     * @param source
     * @param type
     * @return {@link PoetryResult<Integer> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    @Override
    public R<Integer> getCommentCount(Integer source, String type) {

        Integer commentCount = Math.toIntExact(commentMapper.selectCount(Wrappers.<CommentPO>lambdaQuery()
                .eq(CommentPO::getType, type)
                .eq(CommentPO::getSource,source)));

        return R.success(commentCount);
    }

    /***
     * @Description 保存评论
     * @param commentVO
     * @return {@link PoetryResult }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    @Override
    public R<String> saveComment(CommentVO commentVO) {

        if (CommentTypeEnum.getEnumByCode(commentVO.getType()) == null) {
            return R.fail("评论来源类型不存在！");
        }
        ArticlePO articlePO = null;
        if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {

            articlePO = articleMapper.selectOne(Wrappers.<ArticlePO>lambdaQuery()
                    .eq(ArticlePO::getId,commentVO.getSource())
                    .select(ArticlePO::getUserId,ArticlePO::getArticleTitle,ArticlePO::getCommentStatus));

            if (articlePO == null) {
                return R.fail("文章不存在");
            } else {
                if (!articlePO.getCommentStatus()) {
                    return R.fail("评论功能已关闭！");
                }
            }
        }


        CommentPO comment = new CommentPO();
        comment.setSource(commentVO.getSource());
        comment.setType(commentVO.getType());
        comment.setCommentContent(commentVO.getCommentContent());
        comment.setParentCommentId(commentVO.getParentCommentId());
        comment.setFloorCommentId(commentVO.getFloorCommentId());
        comment.setParentUserId(commentVO.getParentUserId());
        comment.setUserId(CurrentUserHolder.getUser().getId());
        if (StringUtils.hasText(commentVO.getCommentInfo())) {
            comment.setCommentInfo(commentVO.getCommentInfo());
        }
        save(comment);

        // TODO 邮件通知 待定
//        try {
//            mailSendUtil.sendCommentMail(commentVO, articlePO, this);
//        } catch (Exception e) {
//            log.error("发送评论邮件失败：", e);
//        }

        return R.success();
    }

    /***
     * @Description 测试接口 测试Netty是否能够正常发送消息和接收消息
     * @param commentVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/10/10
     */
    @Override
    public R<String> testNettySendMessage(CommentVO commentVO) {

        MessageBase.Message message = MessageBase.Message.newBuilder()
                .setContent(commentVO.getCommentContent())
                .build();
        nettyClient.sendMessage(message);
        return R.ok("ok");
    }

    /***
    * @Description
    * @param c
    * @return {@link CommentVO }
    * @Author yaoHui
    * @Date 2024/9/23
    */
    private CommentVO buildCommentVO(CommentPO c) {
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(c, commentVO);

        UserPO user = CurrentUserHolder.getUser();
        if (user != null) {
            commentVO.setAvatar(user.getAvatar());
            commentVO.setUsername(user.getUsername());
        }

        // 如果用户没有设置用户名称 就根据一定的规则生成随机名称
        if (!StringUtils.hasText(commentVO.getUsername())) {
            commentVO.setUsername(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8)
                    + commentVO.getUserId().toString());
        }

        if (commentVO.getParentUserId() != null) {
            UserDTO u = userFeign.getUserDTOById(commentVO.getParentUserId()).getData();

            if (u != null) {
                commentVO.setParentUsername(u.getUsername());
            }
            if (!StringUtils.hasText(commentVO.getParentUsername())) {
                commentVO.setUsername(UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8)
                        + commentVO.getParentUserId().toString());
            }
        }
        return commentVO;
    }

}
