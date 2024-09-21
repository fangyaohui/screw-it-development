package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fang.screw.blog.mapper.LabelMapper;
import com.fang.screw.blog.mapper.SortMapper;
import com.fang.screw.blog.service.SortLabelService;
import com.fang.screw.communal.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public R<Map> getListSortAndLabel() {

        Map<String, List> map = new HashMap<>();
        map.put("sorts", new LambdaQueryChainWrapper<>(sortMapper).list());
        map.put("labels", new LambdaQueryChainWrapper<>(labelMapper).list());
        return R.success(map);
    }
}
