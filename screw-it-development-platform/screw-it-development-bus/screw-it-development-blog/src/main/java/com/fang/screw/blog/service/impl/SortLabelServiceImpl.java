package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fang.screw.blog.mapper.ArticleMapper;
import com.fang.screw.blog.mapper.LabelMapper;
import com.fang.screw.blog.mapper.SortMapper;
import com.fang.screw.blog.service.SortLabelService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.ArticlePO;
import com.fang.screw.domain.po.LabelPO;
import com.fang.screw.domain.po.SortPO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName SortLabelServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Service
@Slf4j
@AllArgsConstructor
public class SortLabelServiceImpl implements SortLabelService {

    private SortMapper sortMapper;

    private LabelMapper labelMapper;

    private ArticleMapper articleMapper;

    @Override
    public R<Map> getListSortAndLabel() {

        Map<String, List> map = new HashMap<>();
        map.put("sorts", new LambdaQueryChainWrapper<>(sortMapper).list());
        map.put("labels", new LambdaQueryChainWrapper<>(labelMapper).list());
        return R.success(map);
    }

    /***
     * @Description 获取分类标签信息
     * @return {@link R< List< SortPO>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<List<SortPO>> getSortInfo() {

        List<SortPO> sortPOList = sortMapper.selectList(null);

        if (!CollectionUtils.isEmpty(sortPOList)) {
            sortPOList.forEach(sort -> {

                Integer countOfSort = Math.toIntExact(articleMapper.selectCount(Wrappers.<ArticlePO>lambdaQuery()
                        .eq(ArticlePO::getSortId, sort.getId())));
                sort.setCountOfSort(countOfSort);

                List<LabelPO> labels = labelMapper.selectList(Wrappers.<LabelPO>lambdaQuery()
                        .eq(LabelPO::getSortId,sort.getId()));

                if (!CollectionUtils.isEmpty(labels)) {
                    labels.forEach(label -> {

                        Integer countOfLabel = Math.toIntExact(articleMapper.selectCount(Wrappers.<ArticlePO>lambdaQuery()
                                .eq(ArticlePO::getLabelId, label.getId())));
                        label.setCountOfLabel(countOfLabel);
                    });
                    sort.setLabels(labels);
                }
            });
        }

        return R.success(sortPOList);
    }
}
