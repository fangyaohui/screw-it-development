package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.screw.domain.dto.BlogUserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fang.screw.domain.vo.BlogUserVO;

import java.time.LocalDateTime;

/**
 * @FileName BlogUserPO
 * @Description 用户PO
 * @Author yaoHui
 * @date 2024-07-27
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "blog_user")
public class BlogUserPO extends BasePO{

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;


    public BlogUserDTO transformToDTO(){
        BlogUserDTO blogUserDTO = new BlogUserDTO();
        BeanUtils.copyProperties(this,blogUserDTO);
        return blogUserDTO;
    }

    public BlogUserVO transformToVO(){
        BlogUserVO blogUserVO = new BlogUserVO();
        BeanUtils.copyProperties(this,blogUserVO);
        return blogUserVO;
    }

}