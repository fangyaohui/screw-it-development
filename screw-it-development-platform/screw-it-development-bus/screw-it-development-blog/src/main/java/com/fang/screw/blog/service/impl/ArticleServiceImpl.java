package com.fang.screw.blog.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.ArticleMapper;
import com.fang.screw.blog.mapper.BlogImageUploadMapper;
import com.fang.screw.blog.mapper.LabelMapper;
import com.fang.screw.blog.mapper.SortMapper;
import com.fang.screw.blog.service.ArticleService;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.ImageDetermineUtils;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.StringUtil;
import com.fang.screw.domain.enums.PoetryEnum;
import com.fang.screw.domain.po.ArticlePO;
import com.fang.screw.domain.po.BlogImageUploadPO;
import com.fang.screw.domain.po.LabelPO;
import com.fang.screw.domain.po.SortPO;
import com.fang.screw.domain.vo.ArticleVO;
import com.fang.screw.domain.vo.BaseRequestVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.DynamicParameter.*;

/**
 * @FileName ArticelServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,ArticlePO> implements ArticleService {

    private SortMapper sortMapper;

    private ArticleMapper articleMapper;

    private LabelMapper labelMapper;

    private BlogImageUploadMapper blogImageUploadMapper;

    /***
     * @Description 保存博客内容
     * @param articleVO
     * @return {@link R }
     * @Author yaoHui
     * @Date 2024/9/21
     */
    @Override
    public R saveArticle(ArticleVO articleVO) {

        // 如果把这个文章设置了不可见却又没有设置密码 则需要设置密码
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && !StringUtils.hasText(articleVO.getPassword())) {
            return R.fail("请设置文章密码！");
        }

        ArticlePO articlePO = new ArticlePO();
        if (StringUtils.hasText(articleVO.getArticleCover())) {
            articlePO.setArticleCover(articleVO.getArticleCover());
        }
        if (StringUtils.hasText(articleVO.getVideoUrl())) {
            articlePO.setVideoUrl(articleVO.getVideoUrl());
        }
        if (articleVO.getViewStatus() != null && !articleVO.getViewStatus() && StringUtils.hasText(articleVO.getPassword())) {
            articlePO.setPassword(articleVO.getPassword());
            articlePO.setTips(articleVO.getTips());
        }
        articlePO.setViewStatus(articleVO.getViewStatus());
        articlePO.setCommentStatus(articleVO.getCommentStatus());
        articlePO.setRecommendStatus(articleVO.getRecommendStatus());
        articlePO.setArticleTitle(articleVO.getArticleTitle());

        articlePO.setSortId(articleVO.getSortId());
        articlePO.setLabelId(articleVO.getLabelId());
        Integer userId = CurrentUserHolder.getUser().getId();
        articlePO.setUserId(userId);

        // 处理上传博客内的图片地址
        // TODO 处理博客地址
        // 替换MD文件中所有图片地址
        // 读取上传的文件内容

        Map<String,String> imagePath =  blogImageUploadMapper.selectList(Wrappers.<BlogImageUploadPO> lambdaQuery()
                .eq(BlogImageUploadPO::getUserId,CurrentUserHolder.getUser().getId()))
                .stream()
                .collect(Collectors.toMap(BlogImageUploadPO::getSourcePath,BlogImageUploadPO::getTargetPath));
        String content = ImageDetermineUtils.replaceImageUrls(articleVO.getArticleContent(),imagePath);
        List<String> imagePathList = ImageDetermineUtils.extractImageUrls(content);

        articlePO.setArticleContent(content);

        save(articlePO);

        return R.success();
    }

    /***
     * @Description 查询分类文章List
     * @return {@link PoetryResult<Map<Integer,List<ArticleVO>>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<Map<Integer, List<ArticleVO>>> getListSortArticle() {

        Map<Integer, List<ArticleVO>> map = new HashMap<>();
        Map<Integer, LabelPO> labelPOMap = labelMapper.selectList(null)
                .stream().collect(Collectors.toMap(LabelPO::getId, Function.identity()));

        List<SortPO> sortPOList = sortMapper.selectList(null);
        for(SortPO sort : sortPOList){

            List<ArticlePO> articlePOList = articleMapper.selectList(Wrappers.<ArticlePO>lambdaQuery()
                    .eq(ArticlePO::getSortId,sort.getId())
                    .eq(ArticlePO::getDelFlag,NOT_DEL_FLAG)
                    .orderByDesc(ArticlePO::getCreateTime)
                    .last("limit 6"));
            if(CollectionUtils.isEmpty(articlePOList)){
                continue;
            }

            List<ArticleVO> articleVOList = articlePOList.stream().map(articlePO -> {
                if (articlePO.getArticleContent().length() > SUMMARY) {
                    articlePO.setArticleContent(articlePO.getArticleContent()
                            .substring(0, SUMMARY)
                            .replace("`", "")
                            .replace("#", "")
                            .replace(">", ""));
                }

                ArticleVO articleVO = articlePO.transformVO();
                articleVO.setHasVideo(StringUtils.hasText(articlePO.getVideoUrl()));
                articleVO.setPassword(null);
                articleVO.setVideoUrl(null);
                articleVO.setSort(sort);
                articleVO.setLabel(labelPOMap.get(articlePO.getLabelId()));

                return articleVO;

            }).collect(Collectors.toList());

            map.put(sort.getId(),articleVOList);
        }

        return R.success(map);
    }

    /***
     * @Description 根据ID查询对应文章
     * @param id
     * @param password
     * @return {@link PoetryResult<ArticleVO> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<ArticleVO> getArticleById(Integer id, String password) {

        ArticlePO articlePO = getById(id);

        if (articlePO == null) {
            return R.success();
        }
        if (!articlePO.getViewStatus() && (!StringUtils.hasText(password) || !password.equals(articlePO.getPassword()))) {
            return R.fail("密码错误" + (StringUtils.hasText(articlePO.getTips()) ? articlePO.getTips() : "请联系作者获取密码"));
        }

        // TODO 同步更新文章浏览人数-注意高并发情况下的数据一致性情况-articleMapper.updateViewCount(id);
        //  article.setPassword(null);

        if (StringUtils.hasText(articlePO.getVideoUrl())) {
            articlePO.setVideoUrl(SecureUtil.aes(CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8))
                    .encryptBase64(articlePO.getVideoUrl()));
        }
        ArticleVO articleVO = buildArticleVO(articlePO);
        return R.success(articleVO);
    }

    /***
     * @Description 查询文章List
     * @param baseRequestVO
     * @return {@link PoetryResult<Page> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<Page> getListArticle(BaseRequestVO baseRequestVO) {

        List<Integer> ids = null;
        List<List<Integer>> idList = null;
        if (StringUtils.hasText(baseRequestVO.getArticleSearch())) {
            idList = getArticleIds(baseRequestVO.getArticleSearch());
            ids = idList.stream().flatMap(Collection::stream).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(ids)) {
                return R.success();
            }
        }

//        LambdaQueryChainWrapper<ArticlePO> lambdaQuery = lambdaQuery();
//        lambdaQuery.in(!CollectionUtils.isEmpty(ids), ArticlePO::getId, ids);
//        lambdaQuery.like(StringUtils.hasText(baseRequestVO.getSearchKey()), ArticlePO::getArticleTitle, baseRequestVO.getSearchKey());
//        lambdaQuery.eq(baseRequestVO.getRecommendStatus() != null && baseRequestVO.getRecommendStatus(),
//                ArticlePO::getRecommendStatus, PoetryEnum.STATUS_ENABLE.getCode());
//        lambdaQuery.eq(ArticlePO::getDelFlag, NOT_DEL_FLAG);
//
//
//        if (baseRequestVO.getLabelId() != null) {
//            lambdaQuery.eq(ArticlePO::getLabelId, baseRequestVO.getLabelId());
//        } else if (baseRequestVO.getSortId() != null) {
//            lambdaQuery.eq(ArticlePO::getSortId, baseRequestVO.getSortId());
//        }
//
//        lambdaQuery.orderByDesc(ArticlePO::getCreateTime);
//
//        lambdaQuery.page(baseRequestVO);
//
//        List<ArticlePO> records = baseRequestVO.getRecords();
//        if (!CollectionUtils.isEmpty(records)) {
//            List<ArticleVO> articles = new ArrayList<>();
//            List<ArticleVO> titles = new ArrayList<>();
//            List<ArticleVO> contents = new ArrayList<>();
//
//            for (ArticlePO article : records) {
//                if (article.getArticleContent().length() > SUMMARY) {
//                    article.setArticleContent(article.getArticleContent().substring(0, SUMMARY)
//                            .replace("`", "")
//                            .replace("#", "")
//                            .replace(">", ""));
//                }
//                ArticleVO articleVO = buildArticleVO(article);
//                articleVO.setHasVideo(StringUtils.hasText(articleVO.getVideoUrl()));
//                articleVO.setPassword(null);
//                articleVO.setVideoUrl(null);
//                if (CollectionUtils.isEmpty(ids)) {
//                    articles.add(articleVO);
//                } else if (idList.get(0).contains(articleVO.getId())) {
//                    titles.add(articleVO);
//                } else if (idList.get(1).contains(articleVO.getId())) {
//                    contents.add(articleVO);
//                }
//            }
//
//            List<ArticleVO> collect = new ArrayList<>();
//            collect.addAll(articles);
//            collect.addAll(titles);
//            collect.addAll(contents);
//            baseRequestVO.setRecords(collect);
//        }
//        return R.success(baseRequestVO);

        // TODO 前端页面下一页存在bug，Page获取到的total存在问题 后续有时间进行修复
        Page<ArticlePO> page = new Page<>();
        page(page,Wrappers.<ArticlePO>lambdaQuery()
                .like(StringUtils.hasText(baseRequestVO.getSearchKey()), ArticlePO::getArticleTitle, baseRequestVO.getSearchKey())
                .in(!CollectionUtils.isEmpty(ids), ArticlePO::getId, ids)
                .eq(baseRequestVO.getRecommendStatus() != null && baseRequestVO.getRecommendStatus(),
                        ArticlePO::getRecommendStatus, PoetryEnum.STATUS_ENABLE.getCode())
                .eq(!ObjectUtils.isEmpty(baseRequestVO.getLabelId()),ArticlePO::getLabelId,baseRequestVO.getLabelId())
                .eq(!ObjectUtils.isEmpty(baseRequestVO.getSortId()),ArticlePO::getSortId,baseRequestVO.getSortId())
                .eq(ArticlePO::getDelFlag,NOT_DEL_FLAG)
                .orderByDesc(ArticlePO::getCreateTime));

        Page<ArticleVO> articleVOPage = new Page<>();
        BeanUtils.copyProperties(page,articleVOPage);

        articleVOPage.setRecords(page.getRecords().stream().map(this::buildArticleVO).collect(Collectors.toList()));

        return R.success(articleVOPage);
    }

    private List<List<Integer>> getArticleIds(String searchText){
        List<ArticlePO> articles = articleMapper.selectList(null);

        List<List<Integer>> ids = new ArrayList<>();
        List<Integer> titleIds = new ArrayList<>();
        List<Integer> contentIds = new ArrayList<>();

        for (ArticlePO article : articles) {
            if (StringUtil.matchString(article.getArticleTitle(), searchText)) {
                titleIds.add(article.getId());
            } else if (StringUtil.matchString(article.getArticleContent(), searchText)) {
                contentIds.add(article.getId());
            }
        }

        ids.add(titleIds);
        ids.add(contentIds);
        return ids;
    }

    /***
    * @Description 为ArticleVO注入前端所需要的属性
    * @param articlePO
    * @return {@link ArticleVO }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    private ArticleVO buildArticleVO(ArticlePO articlePO){
        ArticleVO articleVO = articlePO.transformVO();

        SortPO sortPO = sortMapper.selectById(articlePO.getSortId());
        LabelPO labelPO = labelMapper.selectById(articlePO.getLabelId());

        articleVO.setSort(sortPO);
        articleVO.setLabel(labelPO);

        return articleVO;
    }
}
