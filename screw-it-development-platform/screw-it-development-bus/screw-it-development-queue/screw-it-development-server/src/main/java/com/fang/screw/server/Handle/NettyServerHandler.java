package com.fang.screw.server.Handle;

import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName NettyServerHandler
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageBase.Message) {
            // 将接收到的消息转换为 Protobuf 消息
            MessageBase.Message message = (MessageBase.Message) msg;

            // 处理消息（例如，打印消息内容）
            log.info("Received message:");
            log.info(message.toString());

            // 可选：根据接收到的消息发送响应
            MessageBase.Message response = MessageBase.Message.newBuilder()
                    .setRequestId(message.getRequestId())
                    .setContent("Response to: " + message.getContent())
                    .build();
            ctx.writeAndFlush(response); // 发送响应
        } else {
            // 如果接收到的消息不是预期的类型，可以选择忽略或者抛出异常
            System.err.println("Received an unknown message type: " + msg.getClass().getName());
            ctx.fireChannelRead(msg); // 继续向下传递
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 处理异常
        cause.printStackTrace();
        ctx.close(); // 关闭连接
    }
}
