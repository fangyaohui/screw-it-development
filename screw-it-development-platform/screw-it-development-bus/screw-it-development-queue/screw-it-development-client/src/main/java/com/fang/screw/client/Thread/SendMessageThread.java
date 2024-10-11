package com.fang.screw.client.Thread;

import com.fang.screw.client.component.NettyClient;
import com.fang.screw.client.protocol.MessageBase;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @FileName SendMessageThread
 * @Description
 * @Author yaoHui
 * @date 2024-10-11
 **/
@Slf4j
public class SendMessageThread {

    // 采用阻塞队列保证线程的安全 以便免多线程的情况下导致数据丢失 同时阻塞队列也可以当队列满的时候阻塞线程让其之后再重新添加运行
    private static final BlockingQueue<MessageBase.Message> blockingQueue = new ArrayBlockingQueue<MessageBase.Message>(1024);

    // 等待确认的消息Map
    private static final ConcurrentHashMap<String, MessageBase.Message> waitAckMessageMap = new ConcurrentHashMap<>();

    // 等待确认消息超时Map
    private static final ConcurrentHashMap<String, LocalDateTime> messageTimeOutMap = new ConcurrentHashMap<>();

    // 超时
    private static final Long timeout = 2000L;

    // 最多超时重传次数
    private static final Integer maxRetries = 5;

    private static SocketChannel socketChannel;

    public SendMessageThread(){
        sendMessageByThread();
        messageRetryThread();
    }

    public SendMessageThread(SocketChannel socketChannel1){
        socketChannel = socketChannel1;
    }

    public void setSocketChannel(SocketChannel socketChannel1){
        socketChannel = socketChannel1;
    }

    public void addMessageBlockingQueue(MessageBase.Message message){
        blockingQueue.add(message);
    }

    /***
     * @Description 构建一个线程来一直对队列中的消息发送到Netty服务端
     * @return {@link }
     * @Author yaoHui
     * @Date 2024/10/11
     */
    private void sendMessageByThread(){
        new Thread(() -> {
            while(true){
                try{
                    log.info("sendMessageByThread ready");
                    MessageBase.Message message = blockingQueue.take();
                    log.info("sendMessageByThread working");
                    sendMessage(message);
                } catch (Exception e) {
                    log.error("sendMessageByThread is interrupt 发送消息线程被中断");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    /***
     * @Description 线程调用 让消息发送到Netty服务端的具体实现逻辑
     * @param message
     * @return {@link  }
     * @Author yaoHui
     * @Date 2024/10/11
     */
    private void sendMessage(MessageBase.Message message){
        // 超过最大重传次数
        if(message.getRetryCount() > maxRetries){
            log.error("消息传输失败次数超过5次：" + message.toString());
        }else{
            if (socketChannel.isActive()){
                socketChannel.writeAndFlush(message);
                waitAckMessageMap.put(message.getRequestId(),message);
                messageTimeOutMap.put(message.getRequestId(),LocalDateTime.now().plusSeconds(timeout));
            }else{
                log.info("Netty连接失败，请重试");
                addMessageBlockingQueue(message);
            }
        }

    }

    /***
    * @Description 将超时的消息重新加入队列中重新进行发送
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    private void messageRetryThread(){
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        Runnable task = () -> {
            log.info("messageRetryThread working");
          for (Map.Entry<String, MessageBase.Message> entry : waitAckMessageMap.entrySet()){
              MessageBase.Message message = entry.getValue();
              String key = entry.getKey();
              LocalDateTime time = messageTimeOutMap.get(key);
              if(time.isBefore(LocalDateTime.now())){
                  MessageBase.Message newMessage = MessageBase.Message.newBuilder()
                          .setRequestId(message.getRequestId())
                          .setCmd(message.getCmd())
                          .setContent(message.getContent())
                          .setRetryCount(message.getRetryCount()+1)
                          .setUrlPath(message.getUrlPath())
                          .build();
                  addMessageBlockingQueue(newMessage);
                  messageTimeOutMap.remove(key);
                  waitAckMessageMap.remove(key);
              }
          }
        };

        // 安排任务在延迟2秒后开始执行，之后每隔3秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(task,2,3,TimeUnit.SECONDS);
    }

    /***
    * @Description 根据RequestId来消除待确认中的消息
    * @param key
    * @return {@link }
    * @Author yaoHui
    * @Date 2024/10/11
    */
    public static void getAckAndRemoveMessage(String key){
        messageTimeOutMap.remove(key);
        waitAckMessageMap.remove(key);
    }


}
