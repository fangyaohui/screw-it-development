package com.fang.screw.chat.utils;

import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import com.fang.screw.domain.po.MessagePO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @FileName MessageEntityEncode
 * @Description 将消息编码成指定的JSON格式字符串
 * @Author yaoHui
 * @date 2024-10-08
 **/
public class MessageEntityEncode implements Encoder.Text<MessagePO>{
    @Override
    public String encode(MessagePO messagePO) throws EncodeException {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .toJson(messagePO);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
