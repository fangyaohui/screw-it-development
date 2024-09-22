package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.SortPO;

import java.util.List;
import java.util.Map;

/**
 * @FileName SortLabelService
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
public interface SortLabelService {

    R<Map> getListSortAndLabel();

    /***
     * @Description 获取分类标签信息
     * @return {@link R< List< SortPO>> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<List<SortPO>> getSortInfo();
}
