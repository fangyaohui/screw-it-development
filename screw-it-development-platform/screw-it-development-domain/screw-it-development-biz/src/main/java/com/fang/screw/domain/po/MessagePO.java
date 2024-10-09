package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.screw.domain.vo.MessageVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @FileName MessagePO
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@Data
@TableName("message")
public class MessagePO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer sendUser;

    private Integer receiveUser;

    private Integer messageType;

    private String message;

    private Integer delFlag;

    @TableField("create_time")
    private LocalDateTime createTime;

    public MessageVO transformVO(){
        MessageVO messageVO = new MessageVO();
        BeanUtils.copyProperties(this,messageVO);
        return messageVO;
    }

}
