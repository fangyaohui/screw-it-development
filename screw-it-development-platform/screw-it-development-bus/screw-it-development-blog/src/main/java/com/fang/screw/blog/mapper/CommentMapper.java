package com.fang.screw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.BlogCommentPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName CommentMapper
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Mapper
public interface CommentMapper extends BaseMapper<BlogCommentPO> {
}