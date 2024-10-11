package com.fang.screw.server.Handle;

import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @FileName ServerIdleStateHandler
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Slf4j
public class ServerIdleStateHandler extends IdleStateHandler {
    Integer times = 0;
    /**
     * 设置空闲检测时间为 30s
     */
    private static final int READER_IDLE_TIME = 60;
    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, READER_IDLE_TIME, READER_IDLE_TIME, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("{} 秒内没有读取到数据,关闭连接", READER_IDLE_TIME);

        System.out.println(ctx.channel().remoteAddress() + "发生超时事件--" + evt);

        ctx.channel().close();
    }
}
