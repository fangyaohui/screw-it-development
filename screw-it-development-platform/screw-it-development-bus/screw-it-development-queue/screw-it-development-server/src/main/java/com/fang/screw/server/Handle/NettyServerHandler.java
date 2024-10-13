package com.fang.screw.server.Handle;

import com.fang.screw.client.protocol.MessageBase;
import com.fang.screw.server.component.HuiMessageQueue;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

/**
 * @FileName NettyServerHandler
 * @Description
 * @Author yaoHui
 * @date 2024-10-10
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final HuiMessageQueue huiMessageQueue;

    public NettyServerHandler(HuiMessageQueue messageQueue){
        this.huiMessageQueue = messageQueue;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof MessageBase.Message) {
            // 将接收到的消息转换为 Protobuf 消息
            MessageBase.Message message = (MessageBase.Message) msg;

            // 处理消息（例如，打印消息内容）
//            log.info("Received message:");
//            log.info(message.toString());

            // 请求保存消息
            if(message.getCmd() == MessageBase.Message.CommandType.SAVE_MESSAGE){
                // 生产者请求保存消息
                // 可选：根据接收到的消息发送响应
                MessageBase.Message response = null;
                boolean flag = huiMessageQueue.saveMessage(message);
                if(flag){
                    response = MessageBase.Message.newBuilder()
                            .setRequestId(message.getRequestId())
                            .setCmd(MessageBase.Message.CommandType.ACK)
                            .build();
                }else{
                    response = MessageBase.Message.newBuilder()
                            .setRequestId(message.getRequestId())
                            .setCmd(MessageBase.Message.CommandType.RETRY_MESSAGE)
                            .build();
                }
                log.info("HuiMQ 收到生产者消息：" + message.toString());
                ctx.writeAndFlush(response); // 发送响应
            }else if(message.getCmd() == MessageBase.Message.CommandType.SEND_MESSAGE){
                // 消费端请求发送消息
                // 检查是否有超时的消息 如果有则将其重新放置于待重传的消息队列中
                HuiMessageQueue.checkTimeOutMessage();

                if(HuiMessageQueue.messageQueueMap.containsKey(message.getChannel())){
                    BlockingQueue<MessageBase.Message> queue = HuiMessageQueue.messageQueueMap.get(message.getChannel());
                    while(!queue.isEmpty()){
                        MessageBase.Message sendMessage = queue.take();
                        log.info("HuiMQ向消费者发送消息：" + sendMessage.toString());
                        ctx.writeAndFlush(sendMessage);
                        // 将消费列为带确认消息
                        HuiMessageQueue.waitAckMessageMap.put(sendMessage.getRequestId(),sendMessage);
                        HuiMessageQueue.messageTimeOutMap.put(sendMessage.getRequestId(), LocalDateTime.now().plusSeconds(2L));
                    }
                }
            }else if(message.getCmd() == MessageBase.Message.CommandType.ACK){
                // 收到消费端传来的ACK确认报文
                log.info("收到消费端发来的ACK报文：" + message.getRequestId());
                HuiMessageQueue.setMessageAck(message.getRequestId());
            }
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
