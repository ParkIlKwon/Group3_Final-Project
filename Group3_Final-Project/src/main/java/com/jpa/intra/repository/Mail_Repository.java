package com.jpa.intra.repository;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.board.BoardTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
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

    // @Id 선언한 값과 일치하는 Mail 객체 찾기
    public Mail findById(Long id){return em.find(Mail.class, id);}

    // 로그인한 사원 전체 메일 목록
    public List<Mail> findLogMailList(Member member){
//        Member member = (Member) session.getAttribute("user");
        String sender_email = member.getEmail();
        TypedQuery<Mail> query = em.createQuery("select m from Mail m " +
                "where m.sender_email=:sender_email or m.receiver=:receiver " +
                "order by m.id desc ", Mail.class);
        query.setParameter("sender_email",sender_email);
        query.setParameter("receiver",sender_email);
        return query.getResultList();
    }

//    public void updateMailView(Member member){
//        String receiver = member.getEmail();
//        em.createQuery("update Mail mail set mail.view = 1 where mail.receiver=:receiver")
//                .setParameter("receiver",receiver)
//                .executeUpdate();
//    }
//public void updateMailView(Mail mail, Member member){
//    String receiver = member.getEmail();
//    int mailId = Math.toIntExact(mail.getId());
//    em.createQuery("update Mail mail set mail.view = 1 where mail.receiver=:receiver and mail.id=:id")
//            .setParameter("receiver",receiver)
//            .setParameter("id",mailId)
//            .executeUpdate();
//}
public void updateMailView(Long mailId, String receiver){
    em.createQuery("update Mail mail set mail.view = 1 where mail.id=:id and mail.receiver=:receiver")
            .setParameter("id",mailId)
            .setParameter("receiver",receiver)
            .executeUpdate();
}
}
