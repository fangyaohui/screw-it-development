package com.fang.screw.blog.controller;

import com.fang.screw.blog.service.WebInfoService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.po.WebInfoPO;
import com.fang.screw.domain.vo.WebInfoVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @FileName WebInfoController
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@RequestMapping("/webInfo")
@RestController
@Slf4j
@AllArgsConstructor
public class WebInfoController {

    private WebInfoService webInfoService;

    /***
    * @Description 请求网站基本信息
    * @return {@link com.fang.screw.communal.utils.R<com.fang.screw.domain.vo.WebInfoVO> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @GetMapping("/getWebInfo")
    public R<WebInfoVO> getWebInfo(){
        return webInfoService.getWebInfo();
    }

    /***
    * @Description 获取赞赏
    * @return {@link PoetryResult<List<User>> }
    * @Author yaoHui
    * @Date 2024/9/22
    */
    @GetMapping("/getAdmire")
    public R<List<UserPO>> getAdmire() {
        return R.success();
    }
}
