package com.fang.screw.domain.vo;

import com.fang.screw.domain.po.UserPO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class UserVO {

    private Integer id;

    private String username;

    private String phoneNumber;

    private String email;

    private String password;

    private Integer gender;

    private String avatar;

    private String introduction;

    private String subscribe;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // @JsonDeserialize(using = LocalDateTimeDeserializer.class) 解决RabbitMQ中无法对LocalDateTime序列化和反序列化的问题
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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

