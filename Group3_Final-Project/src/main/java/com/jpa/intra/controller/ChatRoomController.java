package com.jpa.intra.controller;

import com.jpa.intra.domain.ChatRoom;
import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final Member_Repository member_repository;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    String formattedDate = now.format(formatter);
    @GetMapping("/moveMeeting")
    public String goChatRoom(HttpSession session, Model model){
        List<ChatRoom> crlist=chatService.findRooms();
        Member curUser=(Member)session.getAttribute("user");
        model.addAttribute("curUserName",curUser.getMem_name());
        model.addAttribute("crlist",crlist);
        return "meeting/roomlist";
    }

    @PostMapping("/chat/createRoom")
    @ResponseBody
    public String createChatRoom(@RequestParam("creator") String creator, @RequestParam("roomName") String roomName, @RequestParam("roomPwd") String roomPwd, @RequestParam("maxUserCnt") String maxUserCnt, @RequestParam("secretChk") String secretChk) {
        Member memberObject=member_repository.findByMemName(creator);

        ChatRoom chatRoom=new ChatRoom();

        chatRoom.setCreator(memberObject);
        chatRoom.setRoomName(roomName);
        chatRoom.setRoomPwd(roomPwd);
        chatRoom.setMaxUserCnt(Integer.parseInt(maxUserCnt));
        chatRoom.setSecretChk(secretChk);
        chatRoom.setCreateDateTime(formattedDate);
        chatRoom.setDestroyDateTime(null);
        chatRoom.setCurUserCnt(0);

        chatService.createChatRoom(chatRoom);
        return "success";
    }

    @GetMapping("/chat/room")
    public String roomDetail(HttpSession session, Model model, String roomId) {
        Member curUser=(Member)session.getAttribute("user");
        ChatRoom room=chatService.findRoomByRoomId(Long.parseLong(roomId));
        model.addAttribute("curUserName",curUser.getMem_name());
        model.addAttribute("room", room);
        return "meeting/chatroom";
    }

    @PostMapping("/chat/checkpassword")
    @ResponseBody
    public ResponseEntity<?> checkRoomPwd(@RequestParam("room_id") String roomNum, @RequestParam("password") String reqPwd) {
        boolean isValid = chatService.checkRoomPassword(Long.parseLong(roomNum), reqPwd);

        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);

        return ResponseEntity.ok(response);
    }
}
