package com.fang.demo.comfangdemoupm.service;

import com.fang.demo.comfangdemoupm.utils.R;
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