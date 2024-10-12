package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @FileName MessageQueuePO
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Data
@Slf4j
@TableName("message_queue")
public class MessageQueuePO {

    @TableId(value = "id" ,type = IdType.AUTO)
    private Long id;

    private String requestId;

    private Integer cmd;

    private String content;

    private Integer retryCount;

    private String urlPath;

    private Integer delFlag;

    @TableField("create_time")
    private LocalDateTime createTime;
}
