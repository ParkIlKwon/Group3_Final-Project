package com.jpa.intra;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
@Component //자동으로 객체를 생성해줌
@RequiredArgsConstructor //final 붙은 값을 넣어줌
public class init {

    private final InitService initService;


//생성자가 만들어지면 바로 호출하는 메서드
    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional //db에 값을 넣어주는 어노테이션.
    @RequiredArgsConstructor //생성자 의존성 주입 생략
    static class InitService {

        private final EntityManager em;


        public void dbInit1() {
            // add Team DataSample
            Team t=new Team();
            t.setTeam_name("영업부");
            em.persist(t);

            // add Member DataSample
            Member m = new Member();
            m.setMem_id("test1");
            m.setMem_pw("321");
            m.setMem_name("shoichi");
            em.persist(m);

            t.getMembers().add(m);
            m.setTeam(t);
        }











    }
}

