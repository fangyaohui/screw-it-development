package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.BlogHeadlineVO;
import com.fang.screw.domain.vo.BlogInfoVO;

import java.util.List;

/**
 * @FileName BlogService
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
public interface BlogService {

    /***
     * @Description 返回头条五条博客简要信息
     * @return {@link com.fang.screw.communal.utils.R<java.util.List< BlogInfoVO >> }
     * @Author yaoHui
     * @Date 2024/8/3
     */
    R<List<BlogHeadlineVO>> getHeadlineBriefInfo();

    /***
     * @Description 根据博客ID查询博客的详细信息
     * @param blogId
     * @return {@link R< BlogInfoVO> }
     * @Author yaoHui
     * @Date 2024/8/4
     */
    R<BlogInfoVO> getBlogInfoByBlogId(String blogId);

    /***
     * @Description 获取所有的博客详细内容
     * @return {@link com.fang.screw.communal.utils.R<java.util.List<com.fang.screw.domain.vo.BlogInfoVO>> }
     * @Author yaoHui
     * @Date 2024/9/6
     */
    R<List<BlogInfoVO>> getAllBlogInfo();
}
