package com.fang.screw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.ArticlePO;
import com.fang.screw.domain.vo.UserBlogInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @FileName ArticleMapper
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {

    UserBlogInfoVO getUserBlogInfo(@Param("userId") Long userId);

}
