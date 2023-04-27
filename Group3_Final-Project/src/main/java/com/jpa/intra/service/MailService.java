package com.jpa.intra.service;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Mail_Repository;
import com.jpa.intra.repository.Member_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService{

    private final Mail_Repository mail_repository;
    private final Member_Repository member_repository;
    private final HttpServletRequest request;

    @Autowired
    JavaMailSender emailsender; // Bean 등록해둔 MailConfig 를 emailsender 라는 이름으로 autowired

    private String ePw; // 인증번호

    // 메일 내용 작성

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("ManyToOne 회원등록 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> 근태관리사이트 ManyToOne 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원등록 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>항상 당신의 꿈을 응원합니다. 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("testusage@naver.com", "ManyToOne_Admin"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!

    public String sendSimpleMessage(String to) throws Exception {

        ePw = createKey(); // 랜덤 인증번호 생성

        MimeMessage message = createMessage(to); // 메일 발송
        try {// 예외처리
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }

    // 선택한 메일
    public Mail getOneMail(Long id){
        return mail_repository.findById(id);
    }

    // session "user" 참조 : 전체 메일
   public List<Mail> findLogMailList(){
       HttpSession session = request.getSession();
       Member member = (Member) session.getAttribute("user");
       return mail_repository.findLogMailList(member);
   }
    @Transactional
    public void updateMailView(Long mailId, String receiver){
        mail_repository.updateMailView(mailId, receiver);
    }

    public Map<String,String> getAllMailAddress(){
        Map<String,String> mailAddress = new HashMap<>();
        List<Member> memberList = member_repository.getAllMemberList();
        for (Member member : memberList) {
            mailAddress.put(member.getEmail(), member.getMem_name());
        }
        return mailAddress;
    }

    public List<String> getTeamMailAddress(String teamName){
        List<String> teamMailList = new ArrayList<>();
        List<Member> memberList = member_repository.getAllMemberList();
        for (Member member : memberList) {
            if(member.getTeam().getTeam_name().equals(teamName)){
                teamMailList.add(member.getEmail());
            }
        }
        return teamMailList;
    }




}
