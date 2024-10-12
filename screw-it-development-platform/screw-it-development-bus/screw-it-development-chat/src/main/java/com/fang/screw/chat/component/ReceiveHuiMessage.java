package com.fang.screw.chat.component;

import com.fang.screw.client.annotation.HuiListener;
import com.fang.screw.domain.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @FileName ReceiveHuiMessage
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Component
@Slf4j
public class ReceiveHuiMessage {

    @HuiListener(queueName = "queue")
    public void noticeUserHaveComment(CommentVO commentVO){
      log.info("Chat模块接收到消息：" + commentVO.toString());
    }

}
