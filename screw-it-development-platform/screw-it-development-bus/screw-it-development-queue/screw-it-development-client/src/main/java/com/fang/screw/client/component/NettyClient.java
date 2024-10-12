package com.fang.screw.client.component;

import com.fang.screw.client.Handle.HeartbeatHandler;
import com.fang.screw.client.Handle.NettyClientHandler;
import com.fang.screw.client.Thread.SendMessageThread;
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
import org.springframework.util.ObjectUtils;
import sun.font.TrueTypeFont;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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

    private static final EventLoopGroup group = new NioEventLoopGroup();

    private static final Integer port = 54021;

    private static final String host = "localhost";

    private static SocketChannel socketChannel;

    private static final SendMessageThread sendMessageThread = new SendMessageThread();

    /***
    * @Description 添加消息到阻塞队列中 为消息生产者调用
    * @param message
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    public void addMessageBlockingQueue(MessageBase.Message message){
        if(!socketChannel.isActive()){
            this.start();
        }
        sendMessageThread.addMessageBlockingQueue(message);
    }

    /***
    * @Description 发送消息
    * @param message
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    public void sendMessage(MessageBase.Message message){
        if(!socketChannel.isActive()){
            this.start();
        }
        socketChannel.writeAndFlush(message);
    }

    /***
    * @Description 该方法提供获取SocketChannel 暂时无用
    * @return {@link SocketChannel }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    public static SocketChannel getSocketChannel() throws Exception {
        if (!socketChannel.isActive()) {
            return socketChannel;
//            socketChannel = socketChannel1;
        }
        return socketChannel;
    }

    /***
    * @Description 连接断开重新连接
    * @return {@link SocketChannel }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    private static SocketChannel retryConnect(){
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
                                .addLast(new IdleStateHandler(0, 0, 60)) // 60秒写空闲，30秒读空闲
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
        if (future.isSuccess()){
            return (SocketChannel) future.channel();
        }else{
            return null;
        }
    }

    /***
    * @Description Netty客户端启动函数 调用Start可以启动对Netty服务端的连接
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    @PostConstruct
    private void start(){
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
                                .addLast(new IdleStateHandler(0, 0, 60)) // 60秒写空闲，30秒读空闲
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
                future1.channel().eventLoop().schedule(this::start, 10, TimeUnit.SECONDS);
            }
        });
        socketChannel = (SocketChannel) future.channel();

        sendMessageThread.setSocketChannel(socketChannel);
    }


}
