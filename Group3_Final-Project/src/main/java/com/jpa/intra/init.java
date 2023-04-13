package com.jpa.intra;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
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

//        public void makeTaskDummy() {
//
//            Team t = new Team();
//            t.setTeam_name("인사부");
//            em.persist(t);
//
//            Member m = new Member();
//            String userID = "shoichi";
//            m.setMem_id(userID);
//            m.setMem_pw("321");
//            m.setMem_name("쇼이치");
//            em.persist(m);
//
//            t.getMembers().add(m);
//            m.setTeam(t);
//
//            BoardTask boardTask=new BoardTask();    //boardTask객체
//            boardTask.setBoardTitle("강성 고객 민원 확인 후 달래주다.");  //제목
//            boardTask.setBoardContent("Dummy Task");  //내용
//            boardTask.setCreateDate(null); //작성일
//            boardTask.setUpdateDate(null);  //수정일
//            boardTask.setBoardWriter("shoichi");
//            boardTask.setResponsibleMemNum(m);    //담당자번호
//            boardTask.setStartDate(null);    //업무시작 날짜
//            boardTask.setEndDate(null);    //업무 종료 날짜
//            boardTask.setTeamNum(m.getTeam());    //담당자의 팀번호
//            boardTask.setProgress("TO_DO");  //진행상황("TO_DO","IN_PROGRESS","DONE") : 최초 저장은 무조건 "할 일"이기 때문에 "TO_DO"고정
//
//            em.persist(boardTask);
//        }
    }
}



