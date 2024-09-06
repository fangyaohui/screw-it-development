package com.fang.screw.upm.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.vo.BlogUserVO;
import com.fang.screw.domain.vo.LoginVO;

/**
 * @FileName LoginService
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
public interface LoginService {

    R<LoginVO> signIn(LoginVO loginVO);

}
