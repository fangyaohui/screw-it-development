package com.fang.screw.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.blog.mapper.WebInfoMapper;
import com.fang.screw.blog.service.WebInfoService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.WebInfoPO;
import com.fang.screw.domain.vo.WebInfoVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fang.screw.communal.constant.DynamicParameter.STATUS_OK;

/**
 * @FileName WebInfoServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@Service
@AllArgsConstructor
@Slf4j
public class WebInfoServiceImpl extends ServiceImpl<WebInfoMapper, WebInfoPO> implements WebInfoService {

    private WebInfoMapper webInfoMapper;

    /***
     * @Description 请求网站基本信息
     * @return {@link com.fang.screw.communal.utils.R<com.fang.screw.domain.vo.WebInfoVO> }
     * @Author yaoHui
     * @Date 2024/9/22
     */
    @Override
    public R<WebInfoVO> getWebInfo() {

        WebInfoPO webInfoPo = webInfoMapper.selectList(Wrappers.<WebInfoPO>lambdaQuery()
                .eq(WebInfoPO::getStatus,STATUS_OK)).get(0);

        WebInfoVO webInfoVO = webInfoPo.transformVO();
        return R.success(webInfoVO);
    }
}
