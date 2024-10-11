package com.fang.screw.client.Handle;

import com.fang.screw.client.Thread.SendMessageThread;
import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName NettyClientHandle
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageBase.Message) {
            MessageBase.Message message = (MessageBase.Message) msg;

            if(message.getCmd() == MessageBase.Message.CommandType.ACK){
                SendMessageThread.getAckAndRemoveMessage(message.getRequestId());
                log.info("收到ACK：" + message.getRequestId());
            }else{
                System.out.println("Received response from server:");
                System.out.println("ID: " + message.getRequestId());
                System.out.println("Content: " + message.getContent());
            }

        } else {
            System.err.println("Received an unknown message type: " + msg.getClass().getName());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("客户端发生异常", cause);
        ctx.close();
    }
}
