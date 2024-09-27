package com.fang.screw.domain.vo;

import lombok.Data;

/**
 * @FileName LoginVO
 * @Description
 * @Author yaoHui
 * @date 2024-07-18
 **/
@Data
public class LoginVO {

    private String userName;

    private String account;

    private String password;

    private String privateKey;

    private String token;

    private BlogUserVO blogUserVO;

}
