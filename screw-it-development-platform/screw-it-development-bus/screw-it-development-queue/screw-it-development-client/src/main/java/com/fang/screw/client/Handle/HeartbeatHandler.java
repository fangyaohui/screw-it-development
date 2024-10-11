package com.fang.screw.client.Handle;

import com.fang.screw.client.component.NettyClient;
import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @FileName HeartbeatHandler
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Slf4j
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("60s无操作，断开连接");
                ctx.close();
            }else if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                log.info("60s无操作，断开连接");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生异常，关闭连接", cause);
        ctx.close();
    }
}
