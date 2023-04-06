package com.jpa.intra.repository;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.swing.text.html.parser.Entity;

@Repository //따로 DAO를 implements 해오지 않아도 이미 기본적인게 만들어져 있어서 가져옴
//이게 없으면 현재 클래스에서 엔티티매니저를 사용할 수 없고 따로 클래스를 생성해서 해줘야함 .
@RequiredArgsConstructor //어노테이션으로 간단하게 생성자 생성
//null 이 아닌 필드를 전부 넣어줘서 생성
public class A_Member_Repository {

    private final EntityManager em; //영속성 인스턴스 개체 생성
    //CRUD(만들기,읽기,수정,삭제) 작업수행시 필요한 JPA의 인터페이스

    public void save(Member member){
        em.persist(member); //1차 캐시에 저장. 아직 DB에 안 보냄
        //최종적으로 DB에 적재를 해주기 위해선 @Transactional 으로 다른 클래스에서 해줌
    }

    //public Member findOne()

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

    public Member findById(Long id) {return em.find(Member.class, id);}


}
