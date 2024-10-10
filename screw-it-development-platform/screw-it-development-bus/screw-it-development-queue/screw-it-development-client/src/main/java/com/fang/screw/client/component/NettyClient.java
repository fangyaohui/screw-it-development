package com.fang.screw.client.component;

import com.fang.screw.client.Handle.HeartbeatHandler;
import com.fang.screw.client.Handle.NettyClientHandler;
import com.fang.screw.client.protocol.MessageBase;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @FileName NettyClient
 * @Description Netty 客户端
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Component
@Slf4j
public class NettyClient {

    private final EventLoopGroup group = new NioEventLoopGroup();

    private static final Integer port = 54021;

    private static final String host = "localhost";

    private SocketChannel socketChannel;

    public void sendMessage(MessageBase.Message message){
        log.info("准备发送消息:" + message.getContent());
        socketChannel.writeAndFlush(message);
    }

    @PostConstruct
    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 空闲检测
                                .addLast(new IdleStateHandler(60, 30, 0)) // 60秒写空闲，30秒读空闲
                                .addLast(new HeartbeatHandler())
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufDecoder(MessageBase.Message.getDefaultInstance()))
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new ProtobufEncoder())
                                .addLast(new NettyClientHandler())
                        ; // 自定义处理器
                    }
                });
        ChannelFuture future = bootstrap.connect();
        //客户端断线重连逻辑
        future.addListener((ChannelFutureListener) future1 -> {
            if (future1.isSuccess()) {
                log.info("连接Netty服务端成功");
            } else {
                log.info("连接失败，进行断线重连");
                future1.channel().eventLoop().schedule(this::start, 20, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();
    }

}
