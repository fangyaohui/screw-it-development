package com.fang.screw.domain.dto;

import com.fang.screw.domain.po.BlogUserPO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @FileName BlogUserDTO
 * @Description
 * @Author yaoHui
 * @date 2024-07-27
 **/
@Data
public class BlogUserDTO {

    private Long id;

    /**
     *  用户名
     **/
    private String userName;

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
     *  性别
     **/
    private Integer gender;

    /**
     *  最后登录
     **/
    private LocalDateTime lastLogin;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer status;

    private Integer delFlag;

    private String description;


    public BlogUserPO transformToDTO(){
        BlogUserPO blogUserPO = new BlogUserPO();
        BeanUtils.copyProperties(this,blogUserPO);
        return blogUserPO;
    }


}