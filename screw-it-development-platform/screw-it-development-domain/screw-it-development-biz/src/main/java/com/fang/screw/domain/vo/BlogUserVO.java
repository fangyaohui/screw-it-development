package com.fang.screw.domain.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BlogUserVO
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Data
public class BlogUserVO {

    /**
     *  用户名
     **/
    private String userName;

    /**
     *  密码
     **/
    private String password;

    /**
     *  盐
     **/
    private String salt;

    /**
     *  头像
     **/
    private String avatar;

    /**
     *  电话号码
     **/
    private String phone;

    /**
     *  昵称
     **/
    private String nickName;

    /**
     *  姓名
     **/
    private String name;

    /**
     *  邮箱
     **/
    private String email;

    /**
     *  锁定标记 0-未锁定 1-锁定
     **/
    private Integer lockFlag;

    /**
     *  微信登录
     **/
    private String wxOpenid;

    /**
     *  小程序登录
     **/
    private String miniOpenid;

    /**
     *  QQ登录
     **/
    private String qqOpenid;

    /**
     *  码云登录
     **/
    private String giteeLogin;

    /**
     *  开源中国标识
     **/
    private String oscId;

    /**
     *  性别
     **/
    private Integer gender;

    /**
     *  最后登录
     **/
    private LocalDateTime lastLogin;

}
