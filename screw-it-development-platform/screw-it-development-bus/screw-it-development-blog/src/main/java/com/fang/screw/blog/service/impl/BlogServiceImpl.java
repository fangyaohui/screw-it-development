package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.blog.mapper.BlogHeadlineMapper;
import com.fang.screw.blog.mapper.BlogInfoMapper;
import com.fang.screw.blog.service.BlogService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.ElasticSearchUtils;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import po.BlogHeadlinePO;
import po.BlogInfoPO;
import po.BlogUserPO;
import vo.BlogHeadlineVO;
import vo.BlogInfoVO;

import java.util.List;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.DynamicParameter.*;

/**
 * @FileName BlogServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-30
 **/
@Service
@Slf4j
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private BlogInfoMapper blogInfoMapper;

    private BlogHeadlineMapper blogHeadlineMapper;

    /**
     * @Description 返回头条五条博客简要信息
     * @return {@link com.fang.screw.communal.utils.R<java.util.List<vo.BlogInfoVO>> }
     * @Author yaoHui
     * @Date 2024/8/3
     */
    @Override
    public R<List<BlogHeadlineVO>> getHeadlineBriefInfo() {

        List<BlogHeadlinePO> blogHeadlinePOList = blogHeadlineMapper.selectList(Wrappers.<BlogHeadlinePO>lambdaQuery()
                .eq(BlogHeadlinePO::getDelFlag,NOT_DEL_FLAG)
                .eq(BlogHeadlinePO::getStatus,STATUS_OK)
                .le(BlogHeadlinePO::getStartTime, TimeUtils.getCurrentTimeStr())
                .ge(BlogHeadlinePO::getEndTime, TimeUtils.getCurrentTimeStr())
                .last("limit " + HEADLINE_NUM));

        if(blogHeadlinePOList.isEmpty()){
            return R.failed("暂未设置头条博客");
        }

        // 所有头条博客ID
        List<String> blogIdList = blogHeadlinePOList.stream().map(BlogHeadlinePO::getBlogId).collect(Collectors.toList());
        List<BlogInfoPO> blogInfoPOList = ElasticSearchUtils.getElasticSearchByIds(blogInfoMapper,blogIdList);

        if(blogInfoPOList == null){
            return R.failed("博客查询错误");
        }

        return R.ok(blogInfoPOList.stream().map(e -> {
            BlogHeadlineVO blogHeadlineVO = e.transformBlogHeadlineVO();
            if(!e.getImages().isEmpty()){
                blogHeadlineVO.setImage(e.getImages().get(0));
            }else{
                blogHeadlineVO.setImage(null);
            }
            return blogHeadlineVO;
        }).collect(Collectors.toList()));
    }

    /***
     * @Description 根据博客ID查询博客的详细信息
     * @param blogId
     * @return {@link R< BlogInfoVO> }
     * @Author yaoHui
     * @Date 2024/8/4
     */
    @Override
    public R<BlogInfoVO> getBlogInfoByBlogId(String blogId) {

        // 判断当前用户是否有权限访问该博客具体内容
        BlogUserPO blogUserPO = CurrentUserHolder.getUser();
        if(ObjectUtils.isEmpty(blogUserPO)){
            return R.failed("用户未登录，请重新登录");
        }



        BlogInfoPO blogInfoPO = ElasticSearchUtils.getElasticSearchById(blogInfoMapper,blogId);
        if(ObjectUtils.isEmpty(blogInfoPO)){
            return R.failed(null,"该博客查询异常，请稍后再试");
        }

        BlogInfoVO blogInfoVO = blogInfoPO.transformVO();
        return R.ok(blogInfoVO);
    }
}
