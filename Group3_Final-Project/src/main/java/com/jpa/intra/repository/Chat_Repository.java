package com.jpa.intra.repository;

import com.jpa.intra.domain.ChatRoom;
import com.jpa.intra.domain.board.BoardTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Chat_Repository {
    private final EntityManager em;

    public void createChatRoom(ChatRoom chatRoom) {em.persist(chatRoom);}

    public List<ChatRoom> findAllChatRoom() {return em.createQuery("SELECT c FROM ChatRoom c", ChatRoom.class).getResultList();}

    public ChatRoom findRoomByRoomId(Long roomId) {
        return em.createQuery("SELECT c FROM ChatRoom c WHERE c.id = :roomId", ChatRoom.class)
                .setParameter("roomId", roomId).getSingleResult();
    }
}
