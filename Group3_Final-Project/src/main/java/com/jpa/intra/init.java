package com.jpa.intra;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.domain.board.BoardNotice;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.service.FileService;
import com.jpa.intra.service.MemberService;
import com.jpa.intra.util.TeamConverter;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@Component //자동으로 객체를 생성해줌
@RequiredArgsConstructor //final 붙은 값을 넣어줌
public class init {


    private final InitService initService;


    //생성자가 만들어지면 바로 호출하는 메서드
    @PostConstruct
    public void init() {
        initService.noticeDummy();
        initService.mailDummy();
        Random ran = new Random();

        for (int i = 0; i < 5; i++) {
            int num = ran.nextInt(6);
            initService.makeMemberDummy(num);
        }

        for (int i = 0; i < 5; i++) {
            initService.makeMemberDummy2(i);
        }
       // initService.makeTaskDummy();

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
            m.setBirthday("2023-04-0"+num);

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

        private final MemberService memberService;
        private final TeamConverter tc;
        public void makeMemberDummy2(int i) {


            //테스트 5 번 더미 데이터 부터는 팀원들 이메일 넣어 봤습니다.
            String EmailArr[] = {"dlfrnjs51@naver.com" , "kimbj0117@gmail.com" , "rlatjddbs316@naver.com"
            ,"kimbj0117@gmail.com" , "2021620059@sdu.ac.kr"};


            Member m = new Member();
            String userID = "test" + (i+5);
            m.setMem_id(userID);
            m.setMem_pw("321");
            m.setMem_name(userID);
            m.setStatus("offline");
            m.setBirthday("2023-04-0"+(i*3));

            m.setEmail(EmailArr[i]);
            String gender = i % 2 == 0 ? "남":"여";
            m.setGender(gender);
            m.setVacation(180);


            String RandomAddress[] = {"경기도 시흥시"
                    , "서울시", "강릉시", "강원도", "김포시", "안산시"};

            String RandomRoadName[] = {"정왕대로 54번길",
                    "언주로87길", "강릉대로203번길", "강원9번길",
                    "김포7번길", "안산62번길"};

            Address address = new Address(
                    RandomAddress[i], RandomRoadName[i]);

            m.setAddress(address);
            Team t = tc.convert("" + (i+1)); //팀번호로 해당되는 팀 객체 가져옴
            System.out.println("==============================================================");
            System.out.println(t.getTeam_name());

            memberService.Join(m,t); //서비스에서 동시에 처리해줘야함. 따라서 넣을 멤버와 멤버의 팀객체를 동시에 삽입시켜줌
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

        public void mailDummy(){
            Mail mail = new Mail();
            mail.setBody("<p>제목: 휴가 신청 관련 안내</p><p>안녕하세요,</p><p>저는 인사팀 입니다. 휴가 신청에 관한 안내를 드리려고 합니다.</p><p>회사 내규에 따라 휴가 신청은 온라인 시스템을 통해 진행되어야 합니다. 휴가를 신청하실 때에는 아래의 절차에 따라 주시기 바랍니다.</p><ol><li><p>휴가 신청 기간: 휴가 신청은 휴가 출발일 기준으로 최소 3일 전에 신청해야 합니다. 급박한 사유가 있는 경우에 한하여 휴가 출발 전 <br>3일 이내에도 휴가 신청이 가능하나, 미리 인사팀과 협의가 필요합니다.</p></li><li><p>휴가 신청 방법: 휴가 신청은 회사 내부 포털 시스템의 휴가 신청 페이지에서 작성한 양식에 따라 진행됩니다. <br>양식을 작성하고 제출하신 후, 인사팀에서 신청 내용을 확인하고 승인 여부를 통보해드립니다.</p></li><li><p>휴가 신청의 예외 사항: 휴가 신청 중 특정 휴가 예외 사항(예: 육아 휴가, 병가 등)이 있는 경우에는 별도의 절차와 <br>서류 제출이 필요할 수 있으니, 미리 인사팀에 문의하여 필요한 조치를 취해주시기 바랍니다.</p></li></ol><p>더불어, 휴가 기간 동안의 근무 및 조직 업무 배정 등에 관한 안내는 휴가 승인 후 개별적으로 안내드릴 예정입니다.</p><p>자세한 내용 및 궁금한 사항이 있는 경우, 인사팀으로 문의해주시기 바랍니다.</p><p>감사합니다.</p>");
            mail.setReceiver("dlfrnjs51@naver.com");
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            mail.setSendDate(formatDate);
            mail.setSender("test6");
            mail.setSender_email("kimbj0117@gmail.com");
            mail.setSender_name("test6");
            mail.setTitle("안녕하세요. 휴가 사용 관련 메일입니다.");
            mail.setView(0);

            em.persist(mail);

            Mail mail2 = new Mail();
            mail2.setBody("<p>제목: 회사 프로젝트 업데이트 및 협력 요청</p><p>안녕하세요,</p><p>저는 회사 프로젝트 팀의 매니저입니다. 저희 프로젝트가 진행 중인데, 몇 가지 업데이트와 협력에 관해 알려드리려고 합니다.</p><p>우선, 프로젝트 진행 상황에 대해 간략히 알려드리겠습니다. 현재 프로젝트는 일정에 따라 원활하게 진행되고 있습니다. <br>하지만 몇 가지 이슈가 발생하여 추가적인 조치가 필요하게 되었습니다. 이에 대한 대응을 아래와 같이 안내드립니다.</p><ol><li><p>예산 조정: 프로젝트 예산을 검토한 결과, 몇 가지 예상치 못한 비용이 발생하여 예산 조정이 필요합니다. <br>관련 부서와 협력하여 빠른 시일 내에 예산 조정을 완료하도록 부탁드립니다.</p></li><li><p>리소스 할당: 몇몇 팀이 다른 프로젝트에 참여하게 되어 현재 프로젝트에 필요한 리소스가 부족한 상황입니다. 협력 부탁드리며, <br>다양한 팀 간 협력을 강화하여 프로젝트의 원활한 진행을 도와주세요.</p></li><li><p>일정 조정: 프로젝트 일정이 약간 변경되어 업무 계획을 조정해야 할 필요가 있습니다. 관련 팀과 일정 조율을 통해 적절한 조치를 <br>취하도록 부탁드립니다.</p></li></ol><p>더불어, 프로젝트에 대한 진행 상황 및 이슈에 대한 정기 보고를 강화하고자 합니다. 모두가 함께 협력하여 프로젝트를 성공적으로 <br>마무리하기 위해 노력해주시기 바랍니다.</p><p>감사합니다.</p>\n");
            mail2.setReceiver("kimbj0117@gmail.com");
            mail2.setSendDate(formatDate);
            mail2.setSender("test5");
            mail2.setSender_email("dlfrnjs51@naver.com");
            mail2.setSender_name("test5");
            mail2.setTitle("안녕하세요. 프로젝트 관련으로 연락드립니다.");
            mail2.setView(0);

            em.persist(mail2);
        }
    }
}



