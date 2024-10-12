package com.fang.screw.server.component;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fang.screw.client.protocol.MessageBase;
import com.fang.screw.domain.po.MessageQueuePO;
import com.fang.screw.server.mapper.MessageQueueMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName HuiMessageQueue
 * @Description 消息队列 保存从生产者传来的消息 该消息队列采用拉的模式，即消息由消费者自来来取 取几条取决于消费者
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Component
@Slf4j
public class HuiMessageQueue {

    // 存放待消费消息阻塞队列
//    private static final BlockingQueue<MessageBase.Message> messageBlockingQueue = new ArrayBlockingQueue<MessageBase.Message>(1024);

    public static final ConcurrentHashMap<String,BlockingQueue<MessageBase.Message>> messageQueueMap = new ConcurrentHashMap<>();

    private MessageQueueMapper messageQueueMapper;

    @Autowired
    public void setMessageQueueMapper(MessageQueueMapper mapper){
        this.messageQueueMapper = mapper;
    }

    /***
    * @Description 保存消息到消息队列中 并且保存到MySQL数据库持久化 注意这里是一个事务 要么都成功要么都别成功
    * @param message
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMessage(MessageBase.Message message){
        try{
            // 保存到消息队列中
            if(!messageQueueMap.containsKey(message.getChannel())){
                BlockingQueue<MessageBase.Message> messageBlockingQueue = new ArrayBlockingQueue<MessageBase.Message>(1024);
                messageQueueMap.put(message.getChannel(),messageBlockingQueue);
            }
            messageQueueMap.get(message.getChannel()).add(message);

            // 保存到MySQL数据库中
            if(!messageQueueMapper.exists(Wrappers.<MessageQueuePO>lambdaQuery()
                    .eq(MessageQueuePO::getRequestId,message.getRequestId())
                    .eq(MessageQueuePO::getDelFlag,0))){
                MessageQueuePO messageQueuePO = new MessageQueuePO();
                messageQueuePO.setRequestId(message.getRequestId());
                messageQueuePO.setCmd(message.getCmdValue());
                messageQueuePO.setContent(message.getContent());
                messageQueuePO.setUrlPath(message.getUrlPath());
                messageQueueMapper.insert(messageQueuePO);
                log.info("HuiMQ保存消息：" + messageQueuePO.toString());
            }
        }catch (Exception e){
            log.info("保存消息至消息队列错误！");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /***
    * @Description 发送消息队列中的消息给指定Channel
    * @param ctx
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/12
    */
    public void sendMessage(ChannelHandlerContext ctx, MessageBase.Message message){
        try{
            if(messageQueueMap.containsKey(message.getChannel())){
                BlockingQueue<MessageBase.Message> queue = messageQueueMap.get(message.getChannel());
                while(!queue.isEmpty()){
                    MessageBase.Message sendMessage = queue.take();
                    ctx.writeAndFlush(sendMessage);
                }
            }
        } catch (InterruptedException e) {
            log.info("HuiMQ 给消费者发送消息失败,通道为：" + message.getChannel());
            e.printStackTrace();
        }
    }

}
