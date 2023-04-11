package com.jpa.intra.controller;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.query.MailDTO;
import com.jpa.intra.repository.Mail_Repository;
import com.jpa.intra.service.MailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final Mail_Repository mailRepository;
    @GetMapping("/main")
    public String mailMain(Model model){
        List<Mail> mailList = new ArrayList<>();
        mailList = mailRepository.findAllMailList();
        System.out.println(mailList.size() +"사이즈");
        model.addAttribute("mailList",mailList);

        return "mail/main";
    }

    @GetMapping("/mailForm")
    public String mailForm(Model model){

        List<Mail> mailList = new ArrayList<>();
        mailList = mailRepository.findAllMailList();
        System.out.println(mailList.size() +"사이즈");
        model.addAttribute("mailList",mailList);

        return "mail/mailForm";
    }

    private final MailSendService mailSendService;
    @PostMapping("/mailForm")
    public String SendMail(MailDTO m, HttpServletRequest request){

        HttpSession session = request.getSession();

        String newBody = m.getBody().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        //텍스트 에어리어는 화딱지 나게 태그를 따로 제거해줘야함.
        LocalDateTime now = LocalDateTime.now();

        Mail resMail = new Mail();
        resMail.setTitle(m.getTitle()); //제목 
        resMail.setBody(newBody); //내용
        resMail.setSendDate(now.toString()); //현재 날짜 (2023-04-11T12:22:08.845386100)

        resMail.setView(0); //열람여부 0 -> false , 1 -> true

        resMail.setSender((String) session.getAttribute("log")); //보내는 사람 (현재 로그인된 사람 불러옴)
        //object type to String
        resMail.setReceiver(m.getReceiver()); //받는사람
        

        //상황에 맡게 DTO로 가공한 메일 객체를 재 삽입.
        mailSendService.sendMail(resMail);


        return "redirect:/mail/mailForm";
    }

}
