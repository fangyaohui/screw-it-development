package com.fang.screw.upm.service;

import com.fang.screw.upm.utils.R;
import vo.LoginVO;

/**
 * @FileName LoginService
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
public interface LoginService {

    R<String> signIn(LoginVO loginVO);

}