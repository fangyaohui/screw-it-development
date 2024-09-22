package com.fang.screw.blog.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.WebInfoVO;

/**
 * @FileName WebInfoService
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
public interface WebInfoService {

    /***
     * @Description 请求网站基本信息
     * @return {@link com.fang.screw.communal.utils.R<com.fang.screw.domain.vo.WebInfoVO> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    R<WebInfoVO> getWebInfo();
}
