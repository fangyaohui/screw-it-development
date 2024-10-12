package com.fang.screw.client.Handle;

import com.fang.screw.client.Thread.SendMessageThread;
import com.fang.screw.client.process.HuiListenerRegistry;
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

    private static final HuiListenerRegistry huiListenerRegistry = new HuiListenerRegistry();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageBase.Message) {
            MessageBase.Message message = (MessageBase.Message) msg;
            log.info("收到HuiMQ消息：" + message.toString());

            if(message.getCmd() == MessageBase.Message.CommandType.ACK){
                SendMessageThread.getAckAndRemoveMessage(message.getRequestId());
                log.info("收到ACK：" + message.getRequestId());
            }else if(message.getCmd() == MessageBase.Message.CommandType.SAVE_MESSAGE){
                boolean flag = huiListenerRegistry.handleMessage(message);
                MessageBase.Message response = null;
                if(flag){
                    response = MessageBase.Message.newBuilder().setRequestId(message.getRequestId())
                            .setCmd(MessageBase.Message.CommandType.ACK).build();
                }else{
                    response = MessageBase.Message.newBuilder().setRequestId(message.getRequestId())
                            .setCmd(MessageBase.Message.CommandType.RETRY_MESSAGE).build();
                }
                ctx.writeAndFlush(response);
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
