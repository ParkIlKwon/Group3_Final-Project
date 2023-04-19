package com.jpa.intra;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.domain.board.BoardNotice;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Component //자동으로 객체를 생성해줌
@RequiredArgsConstructor //final 붙은 값을 넣어줌
public class init {


    private final InitService initService;

    //생성자가 만들어지면 바로 호출하는 메서드
    @PostConstruct
    public void init() {
        Random ran = new Random();

        for (int i = 0; i < 5; i++) {
            int num = ran.nextInt(6);
            initService.makeMemberDummy(num);
        }

//        initService.makeTaskDummy();
//        initService.KBJMemberDummy();
        initService.noticeDummy();
    }

    @Component
    @Transactional //db에 값을 넣어주는 어노테이션.
    @RequiredArgsConstructor //생성자 의존성 주입 생략
    static class InitService {
        int index = 0;

        private final EntityManager em;

        public void makeMemberDummy(int num) {
            String TeamArr[] = {"영업부", "인사부", "기획부",
                    "관리부", "회계부", "총무부"};
            String EmailArr[] = {"@naver.com","@hanmail.net"
                    ,"@gmail.com","@kakao.com","@outlook.com"
                    ,"@nate.com"};


            Team t = new Team();
            t.setTeam_name(TeamArr[index]);
            em.persist(t);

            Member m = new Member();
            String userID = "test" + index;
            m.setMem_id(userID);
            m.setMem_pw("321");
            m.setMem_name(userID);
            m.setStatus("offline");
            m.setEmail(userID + EmailArr[num]);
            String gender = num % 2 == 0 ? "남":"여";
            m.setGender(gender);
            m.setVacation(180);

            String RandomAddress[] = {"경기도 시흥시"
                    , "서울시", "강릉시", "강원도", "김포시", "안산시"};

            String RandomRoadName[] = {"정왕대로 54번길",
                    "언주로87길", "강릉대로203번길", "강원9번길",
                    "김포7번길", "안산62번길"};

            Address address = new Address(
                    RandomAddress[num], RandomRoadName[num]);

            m.setAddress(address);
            em.persist(m);

            t.getMembers().add(m);
            m.setTeam(t);

            index++;

        }

        //공지사항 더미
        public void noticeDummy(){
            BoardNotice n1 = new BoardNotice();
            n1.setCreateDate("2023-04-27");
            n1.setBoardWriter("ADMIN");
            n1.setBoardTitle("[작업공지] 2023-04-27 시스템 보안 점검 작업");
            n1.setBoardContent("작업 일시 : 2023-04-27 00:00~00:30 \n작업 상세 : 시스템 보안 점검 작업\n"+
            "담당자 : 인프라팀 이성권 책임\n연락처 : 02-456-7890");
            em.persist(n1);

            BoardNotice n2 = new BoardNotice();
            n2.setCreateDate("2023-05-05");
            n2.setBoardWriter("ADMIN");
            n2.setBoardTitle("[행사공지] 2023-05-05 전사 체육대회");
            n2.setBoardContent("행사 일시 : 2023-05-05 09:00~16:00 \n행사 장소 : 상암 월드컵경기장\n"+
                    "행사 담당 : 마케팅팀 김지영 선임\n연락처 : 02-789-6543");
            em.persist(n2);
        }

    }
}



