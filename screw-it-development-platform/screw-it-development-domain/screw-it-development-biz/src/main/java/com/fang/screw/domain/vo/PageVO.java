package com.fang.screw.domain.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @FileName PageVO
 * @Description
 * @Author yaoHui
 * @date 2024-10-23
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PageVO<T> extends Page<T> {

    String keyWord;
}
