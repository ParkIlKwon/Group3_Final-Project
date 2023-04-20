package com.jpa.intra.repository;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository //따로 DAO를 implements 해오지 않아도 이미 기본적인게 만들어져 있어서 가져옴
//이게 없으면 현재 클래스에서 엔티티매니저를 사용할 수 없고 따로 클래스를 생성해서 해줘야함 .
@RequiredArgsConstructor //어노테이션으로 간단하게 생성자 생성
//null 이 아닌 필드를 전부 넣어줘서 생성
public class Member_Repository {

    private final EntityManager em; //영속성 인스턴스 개체 생성
    //CRUD(만들기,읽기,수정,삭제) 작업수행시 필요한 JPA의 인터페이스

    public void save(Member member){
        em.persist(member); //1차 캐시에 저장. 아직 DB에 안 보냄
        //최종적으로 DB에 적재를 해주기 위해선 @Transactional 으로 다른 클래스에서 해줌
    }

    //public Member findOne()
    public List<Member>getAllMemberList(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member login(String id, String pw) {
        TypedQuery<Member> query = em.createQuery( //멤버 아이디와 패스워드 동시에 일치 하면 멤버 받아오는 로직
                "SELECT m FROM Member m WHERE m.mem_id = :id AND m.mem_pw = :password", Member.class);
        query.setParameter("id", id);
        query.setParameter("password", pw);
        try {
            return query.getSingleResult(); //성공시 하나의 Member객체 받아옴
        } catch (NoResultException | NonUniqueResultException e) {
            return null; //받아온 값이 없을 때 Null을 반환
        }
    }

    // EntityManager의 내장 함수 find로 아이디 값을 참조하여 Member 객체를 뽑음
    public Member findById(Long id) {return em.find(Member.class, id);}


    public void update(Member m) { //업데이트 문
        em.createQuery("UPDATE Member m SET m.mem_id = :id, m.mem_pw = :pw, m.status = :status WHERE m.id = :memberId")
                .setParameter("id", m.getMem_id())
                .setParameter("pw", m.getMem_pw())
                .setParameter("status", m.getStatus())
                .setParameter("memberId", m.getId())
                .executeUpdate();
    }

    public void updateImg(Member m) { //이미지 업데이트 문
        em.createQuery("UPDATE Member m SET m.mem_img = :img WHERE m.id = :memberId")
                .setParameter("img",m.getMem_img())
                .setParameter("memberId",m.getId())
                .executeUpdate();
    }
    
    // 메일과 아이디로 멤버 객체 값 가져오기, 일치 없으면 null 값 호출
    public Member getMember(String memberId, String email){
        TypedQuery<Member> query = em.createQuery("SELECT m from Member m where m.mem_id = :memberId and m.email = :email", Member.class);
        query.setParameter("memberId", memberId);
        query.setParameter("email", email);
        try{
            Member member = query.getSingleResult();
            return member;
        }catch (NoResultException e){
            return null;
        }
    }

    public Member getMemberByUserId(String memberId) {
        TypedQuery<Member> query = em.createQuery("SELECT m from Member m where m.mem_id = :memberId", Member.class);
        query.setParameter("memberId", memberId);
        return query.getSingleResult();
    }





//  메일과 아이디가 일치하는 멤버 , 비밀번호를 '321' 으로 업데이트
    public void rePassword(String memberId, String email){
        em.createQuery("update Member m set m.mem_pw = '321' where m.mem_id = :memberId and m.email = :email")
                .setParameter("memberId", memberId)
                .setParameter("email", email)
                .executeUpdate();
    }

    public void updateMemberVacation(Member member, String startDate, String endDate, int updatedVacation) {
        member.setVacationStart(startDate);
        member.setVacationEnd(endDate);
        member.setVacation(updatedVacation);
        em.merge(member);
        em.flush();
    }

    public void updateMemberWOC(Member member, String inWorkTime, String outWorkTime) {
        member.setInWorkTime(inWorkTime);
        member.setOutWorkTime(outWorkTime);
        em.merge(member);
        em.flush();
    }

    public List<Member> findByMemNameContainingIgnoreCase(String query) {
        return em.createQuery("SELECT m FROM Member m WHERE lower(m.mem_name) LIKE lower(concat('%', :query, '%'))", Member.class)
                .setParameter("query", query)
                .getResultList();
    }

    public Member findByMemName(String memName) {
        TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m WHERE m.mem_name = :memName", Member.class);
        query.setParameter("memName", memName);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public void Delete(Long uid) {
        em.de
    }
}
