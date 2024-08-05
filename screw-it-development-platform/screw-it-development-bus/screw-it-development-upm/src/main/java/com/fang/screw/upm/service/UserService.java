package com.fang.screw.upm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.communal.utils.R;
import po.BlogUserPO;
import po.UserInfoPO;
import vo.BlogUserVO;

import java.util.List;

/**
 * @FileName UserService
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
public interface UserService extends IService<BlogUserPO> {

    List<UserInfoPO> getAllUserInfoList();



}