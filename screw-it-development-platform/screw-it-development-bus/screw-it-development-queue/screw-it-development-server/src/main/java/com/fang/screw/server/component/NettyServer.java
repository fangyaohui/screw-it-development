package com.fang.screw.server.component;

import com.fang.screw.server.Handle.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @FileName NettyServer
 * @Description Netty 服务
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Component
@Slf4j
public class NettyServer {

    /**
     * boss 线程组用于处理连接工作
     */
    private final EventLoopGroup boss = new NioEventLoopGroup();

    /**
     * work 线程组用于数据处理
     */
    private final EventLoopGroup work = new NioEventLoopGroup();

//    @Value("${netty.port}")
    private static final Integer port = 54021;

    private HuiMessageQueue huiMessageQueue;

    @Autowired
    private void setHuiMessageQueue(HuiMessageQueue huiMessageQueue){
        this.huiMessageQueue = huiMessageQueue;
    }

    // @PostConstruct 是一个用于 Java EE 和 Spring 框架中的注解，标记在一个方法上，表示这个方法将在依赖注入完成后被自动调用。
    // 它通常用于进行初始化操作，例如设置默认值、执行启动时的逻辑、或者进行资源的准备。
    /***
    * @Description 启动Netty Server
    * @return {@link  }
    * @Author yaoHui
    * @Date 2024/10/10
    */
    @PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work)
                // 指定Channel
                .channel(NioServerSocketChannel.class)
                //使用指定的端口设置套接字地址
                .localAddress(new InetSocketAddress(port))
                // 方法设置了 Socket 选项 SO_BACKLOG，它指定了在连接队列中可以排队的最大连接数。当服务器正在处理现有连接时，
                // 新的连接请求会被放入队列中，直到当前连接处理完成。1024 是队列的长度，这个值可以根据需要进行调整。
                .option(ChannelOption.SO_BACKLOG,1024)
                // 设置 SO_KEEPALIVE 选项为 true，这意味着在 TCP 连接上启用 TCP 保活机制。
                // 如果连接闲置时间过长，系统会发送保活探测包，以保持连接的活跃状态。
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                // 设置 TCP_NODELAY 选项为 true，启用 Nagle 算法。
                // 这会在发送小数据包时禁用延迟，从而减少数据包的发送延迟，提高实时性。
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new NettyServerHandlerInitializer(huiMessageQueue));
        ChannelFuture future = serverBootstrap.bind().sync();
        if (future.isSuccess()){
            log.info("Netty Server Running");
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        boss.shutdownGracefully().sync();
        work.shutdownGracefully().sync();
        log.info("Netty Server Stop");
    }

}
