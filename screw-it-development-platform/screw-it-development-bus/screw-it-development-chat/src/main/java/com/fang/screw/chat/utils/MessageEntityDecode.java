package com.fang.screw.chat.utils;

import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import com.fang.screw.domain.po.MessagePO;
import com.fang.screw.domain.vo.MessageVO;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @FileName MessageEntityDecode
 * @Description 解码 将JSON字符串解码成指定的消息对象
 * @Author yaoHui
 * @date 2024-10-08
 **/
public class MessageEntityDecode implements Decoder.Text<MessageVO>{

    @Override
    public MessageVO decode(String s) throws DecodeException {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .fromJson(s, MessageVO.class);
    }

    @Override
    public boolean willDecode(String s) {
        return false;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
