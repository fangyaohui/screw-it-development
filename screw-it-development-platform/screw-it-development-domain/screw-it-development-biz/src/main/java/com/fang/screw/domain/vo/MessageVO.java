package com.fang.screw.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName MessageVO
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@Data
public class MessageVO {

    private Integer id;

    private Integer sendUser;

    private Integer receiveUser;

    private Integer messageType;

    private String message;

    private Integer delFlag;

    private LocalDateTime time;

}
