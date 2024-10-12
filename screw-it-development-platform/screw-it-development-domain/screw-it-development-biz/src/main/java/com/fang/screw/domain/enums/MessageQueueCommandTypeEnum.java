package com.fang.screw.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @FileName MessageQueueCommandTypeEnum
 * @Description 消息队列通信消息类型
 * @Author yaoHui
 * @date 2024-10-12
 **/
@AllArgsConstructor
@Getter
public enum MessageQueueCommandTypeEnum {

    NORMAL(0,"常规业务消息"), //常规业务消息
    HEARTBEAT_REQUEST(1,"客户端心跳消息"), //客户端心跳消息
    HEARTBEAT_RESPONSE(2,"服务端心跳消息"), //服务端心跳消息
    ACK(3,"Ack确认消息"), // Ack确认消息
    SAVE_MESSAGE(4,"客户端请求保存消息"), //客户端请求保存消息
    SEND_MESSAGE(5,"客户端请求发送消息"), //客户端请求发送消息

    ;

    final Integer type;

    final String des;

}
