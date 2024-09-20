package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.blog.mapper.BlogHeadlineMapper;
import com.fang.screw.blog.mapper.BlogInfoMapper;
import com.fang.screw.blog.service.BlogService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.ElasticSearchUtils;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.TimeUtils;
import com.fang.screw.domain.enums.PermissionCategoryEnum;
import com.fang.screw.domain.po.BlogPermissionPO;
import com.fang.screw.domain.vo.TopAndFeaturedBlogVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.fang.screw.domain.po.BlogHeadlinePO;
import com.fang.screw.domain.po.BlogInfoPO;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.BlogHeadlineVO;
import com.fang.screw.domain.vo.BlogInfoVO;

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

    @DubboReference
    private UserDubboService userDubboService;

    /**
     * @Description 返回头条五条博客简要信息
     * @return {@link com.fang.screw.communal.utils.R<java.util.List< BlogInfoVO >> }
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

        if(StringUtils.isEmpty(blogId)){
            return R.failed("查无此博客");
        }

        // 查询到该博客的具体内容
        BlogInfoPO blogInfoPO = ElasticSearchUtils.getElasticSearchById(blogInfoMapper,blogId);
        if(ObjectUtils.isEmpty(blogInfoPO)){
            return R.failed(null,"该博客查询异常，请稍后再试");
        }

        // 判断当前用户是否有权限访问该博客具体内容
        BlogUserPO blogUserPO = CurrentUserHolder.getUser();
        if(ObjectUtils.isEmpty(blogUserPO)){
            return R.failed("用户未登录，请重新登录");
        }

        List<BlogPermissionPO> blogPermissionPOList = userDubboService.getBlogPermissionListByUserId(blogUserPO.getId());
        if(blogPermissionPOList.isEmpty()){
            return R.failed("用户获取权限失败，请稍后再试");
        }

        int accessControl = blogInfoPO.getAccessControl();
        boolean accessFlag = false;
        for(BlogPermissionPO blogPermissionPO : blogPermissionPOList){
            if(blogPermissionPO.getPermissionScope().equals(accessControl) &&
                    (blogPermissionPO.getPermissionCategory().equals(PermissionCategoryEnum.READ.getCode()) ||
                            blogPermissionPO.getPermissionCategory().equals(PermissionCategoryEnum.READ_WRITE.getCode()))){
                accessFlag = true;
                break;
            }
        }

        if(!accessFlag){
            return R.failed("您暂无权限访问");
        }

        BlogInfoVO blogInfoVO = blogInfoPO.transformVO();
        return R.ok(blogInfoVO);
    }

    /***
     * @Description 获取所有的博客详细内容
     * @return {@link com.fang.screw.communal.utils.R<java.util.List<com.fang.screw.domain.vo.BlogInfoVO>> }
     * @Author yaoHui
     * @Date 2024/9/6
     */
    @Override
    public R<List<BlogInfoVO>> getAllBlogInfo() {

        List<BlogInfoPO> blogInfoPOList = ElasticSearchUtils.getElasticSearchAllInfo(blogInfoMapper);

        if(ObjectUtils.isEmpty(blogInfoPOList)){
            return R.ok(null);
        }

        return R.ok(blogInfoPOList.stream().map(BlogInfoPO::transformVO).collect(Collectors.toList()));
    }

    /***
     * @Description 获取置顶和推荐文章
     * @return {@link R< TopAndFeaturedBlogVO> }
     * @Author yaoHui
     * @Date 2024/9/7
     */
    @Override
    public R<TopAndFeaturedBlogVO> getTopAndFeaturedBlog() {
        //TODO 获取置顶和推荐文章 这里只是为了简单的模拟 后续请进行更改优化
        List<BlogInfoPO> blogInfoPOList = ElasticSearchUtils.getElasticSearchAllInfo(blogInfoMapper);
        if(ObjectUtils.isEmpty(blogInfoPOList)){
            return R.ok(null);
        }

        TopAndFeaturedBlogVO topAndFeaturedBlogVO = new TopAndFeaturedBlogVO();
        topAndFeaturedBlogVO.setTopArticle(blogInfoPOList.get(0).transformVO());
        blogInfoPOList.remove(0);
        topAndFeaturedBlogVO.setFeaturedArticles(blogInfoPOList.stream().map(BlogInfoPO::transformVO)
                .collect(Collectors.toList()));
        return R.ok(topAndFeaturedBlogVO);
    }
}
