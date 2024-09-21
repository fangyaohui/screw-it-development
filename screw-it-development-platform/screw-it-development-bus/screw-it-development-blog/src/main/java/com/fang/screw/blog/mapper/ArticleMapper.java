package com.fang.screw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.ArticlePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName ArticleMapper
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {
}
