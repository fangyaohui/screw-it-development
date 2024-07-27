package com.fang.screw.upm.service;

import com.fang.screw.communal.utils.BusinessException;
import com.fang.screw.communal.utils.R;
import vo.RegisterUserInfoVO;

public interface RegisterService {

    /***
    * @Description 注册用户
    * @param registerUserInfoVO
    * @return {@link R< Boolean> }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    R<String> registerUser(RegisterUserInfoVO registerUserInfoVO) throws BusinessException;
}
