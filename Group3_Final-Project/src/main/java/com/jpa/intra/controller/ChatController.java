package com.jpa.intra.controller;

import com.jpa.intra.query.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
//    @GetMapping("/moveMeetingDetail")
//    public String goChatRoom(HttpSession session, Model model){
//        Member curUser=(Member)session.getAttribute("user");
//        model.addAttribute("curUserName",curUser.getMem_name());
//
////        model.addAttribute("list", chatRepository.findAllRoom());
////        model.addAttribute("user", "hey");
////        log.info("SHOW ALL ChatList {}", chatRepository.findAllRoom());
////        model.addAttribute("page", "회의");
//        return "/meeting/chatroomclone";
//    }
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
