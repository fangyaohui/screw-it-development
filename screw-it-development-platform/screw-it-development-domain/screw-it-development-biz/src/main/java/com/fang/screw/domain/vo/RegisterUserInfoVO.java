package com.fang.screw.domain.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.fang.screw.domain.po.BlogUserPO;

/**
 * @FileName RegisterInfoVO
 * @Description
 * @Author yaoHui
 * @date 2024-07-27
 **/
@Data
public class RegisterUserInfoVO {

    /**
     *  用户名
     **/
    private String userName;

    /**
     *  密码
     **/
    private String password;

    /**
     *  邮箱
     **/
    private String email;

    /**
     *  手机号
     **/
    private String phone;

    /**
    * @Description 浅拷贝
    * @return {@link BlogUserPO }
    * @Author yaoHui
    * @Date 2024/7/27
    */
    public BlogUserPO transformToBlogUserPo(){
        BlogUserPO blogUserPO = new BlogUserPO();
        BeanUtils.copyProperties(this,blogUserPO);
        return blogUserPO;
    }

}