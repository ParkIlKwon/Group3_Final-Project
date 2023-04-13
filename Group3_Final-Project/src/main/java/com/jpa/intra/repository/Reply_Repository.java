package com.jpa.intra.repository;

import com.jpa.intra.domain.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Reply_Repository {
    private final EntityManager em;

    public void createNewReply(Reply reply) {em.persist(reply);}

    public List<Reply> findAllReply() {return em.createQuery("SELECT rp FROM Reply rp", Reply.class).getResultList();}

    public List<Reply> findReplyByBoardId(Long boardId) {
        return em.createQuery("SELECT rp FROM Reply rp WHERE rp.currentBoard.id = :boardId", Reply.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

}
