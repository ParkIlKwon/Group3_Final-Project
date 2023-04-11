package com.jpa.intra.repository;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.board.BoardTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Mail_Repository {

    private final EntityManager em;
    public void sendMail(Mail mail){
        em.persist(mail);
    }

    public List<Mail> findAllMailList() {
        return em.createQuery("SELECT m FROM Mail m", Mail.class).getResultList();
    }

    public List<Mail> findMailByUserId(String userId) {
        TypedQuery<Mail> query = em.createQuery(
                "SELECT m FROM Mail m WHERE m.receiver = :userId",
                Mail.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }



}
