package com.jpa.intra.service;

import com.jpa.intra.domain.ChatRoom;
import com.jpa.intra.repository.Chat_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ChatService {
    private final Chat_Repository cChatRepository;

    @Transactional
    public Long createChatRoom(ChatRoom chatRoom) {
        cChatRepository.createChatRoom(chatRoom);
        return chatRoom.getId();
    }

    public List<ChatRoom> findRooms() {return cChatRepository.findAllChatRoom();}

    public ChatRoom findRoomByRoomId(Long roomId) {return cChatRepository.findRoomByRoomId(roomId);}
}
