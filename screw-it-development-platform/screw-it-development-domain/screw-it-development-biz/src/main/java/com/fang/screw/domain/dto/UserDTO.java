package com.fang.screw.domain.dto;

import com.fang.screw.domain.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName UserVO
 * @Description
 * @Author yaoHui
 * @date 2024-09-20
 **/
@Data
public class UserDTO {

    private Integer id;

    private String username;

    private String phoneNumber;

    private String email;

    private String password;

    private Integer gender;

    private String avatar;

    private String introduction;

    private String subscribe;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String updateBy;

    private Boolean isBoss = false;

    private String accessToken;

    private String code;

    public UserPO transformPO(){
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(this,userPO);
        return userPO;
    }
}

