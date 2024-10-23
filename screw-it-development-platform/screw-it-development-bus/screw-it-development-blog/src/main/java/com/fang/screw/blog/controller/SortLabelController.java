package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.SortLabelService;
import com.fang.screw.communal.annotation.Cacheable;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.SortPO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
;
import java.util.List;
import java.util.Map;

/**
 * @FileName SortLabelController
 * @Description 博客分类&标签控制层
 * @Author yaoHui
 * @date 2024-09-21
 **/
@RestController
@RequestMapping("/sortLabel")
@Slf4j
@AllArgsConstructor
public class SortLabelController {

    private SortLabelService sortLabelService;

    /***
    * @Description 查询博客定义的分类和标签 目前查询都是个人博客内容 后续应该有大分类和自定义分类
    * @return {@link com.fang.screw.communal.utils.R<java.util.Map> }
    * @Author yaoHui
    * @Date 2024/9/21
    */
    @GetMapping("/getListSortAndLabel")
    public R<Map> getListSortAndLabel() {
        return sortLabelService.getListSortAndLabel();
    }

    /***
    * @Description 获取分类标签信息
    * @return {@link R< List< SortPO>> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @GetMapping("/getSortInfo")
    @Cacheable(value = "sortLabel:sort")
    public R<List<SortPO>> getSortInfo() {
        return sortLabelService.getSortInfo();
    }

}
