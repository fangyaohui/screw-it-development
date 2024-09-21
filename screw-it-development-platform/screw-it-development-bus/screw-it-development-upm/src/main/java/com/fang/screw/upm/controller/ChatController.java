//package com.fang.screw.upm.controller;
//
//import com.fang.screw.domain.po.ChatMessage;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @FileName MessageController
// * @Description 负责实时通信
// * @Author yaoHui
// * @date 2024-09-20
// **/
//@Controller
//public class ChatController  {
//
//    // 处理客户端发来的聊天消息
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/messages")
//    public ChatMessage sendMessage(ChatMessage message) {
//        // 返回消息，广播给订阅 "/topic/messages" 的用户
//        return message;
//    }
//
//    // 处理用户加入事件
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/messages")
//    public ChatMessage addUser(ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
//        // 将用户加入到WebSocket的会话中
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        message.setContent(message.getSender() + " 加入了聊天");
//        return message;
//    }
//
//}
