package com.fang.screw.client.Handle;

import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @FileName NettyClientHandle
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageBase.Message) {
            MessageBase.Message message = (MessageBase.Message) msg;
            System.out.println("Received response from server:");
            System.out.println("ID: " + message.getRequestId());
            System.out.println("Content: " + message.getContent());
        } else {
            System.err.println("Received an unknown message type: " + msg.getClass().getName());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }
}
