package com.jpa.intra.controller;

import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.query.MailDTO;
import com.jpa.intra.repository.Mail_Repository;
import com.jpa.intra.service.MailSendService;
import com.jpa.intra.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final Mail_Repository mailRepository;
    private final MailService mailService;

    @GetMapping("/main")
    public String mailMain(Model model){
//        List<Mail> mailList = new ArrayList<>();
//        mailList = mailRepository.findAllMailList();
//        model.addAttribute("mailList",mailList);
        model.addAttribute("page", "메일");
        List<Mail> mailList = mailService.findLogMailList();
        model.addAttribute("mailList",mailList);
        return "mail/main";
    }

    @GetMapping("/mailForm")
    public String mailForm(Model model){

//        List<Mail> mailList = new ArrayList<>();
//        mailList = mailRepository.findAllMailList();
//        System.out.println(mailList.size() +"사이즈");
//        model.addAttribute("mailList",mailList);
//        model.addAttribute("page", "메일");

        Map<String,String> allMailAddress = new HashMap<>(mailService.getAllMailAddress());
        model.addAttribute("allMailAddress",allMailAddress);
        return "mail/mailForm";
    }

    private final MailSendService mailSendService;
    @PostMapping("/mailForm")
    public String SendMail(MailDTO m, HttpServletRequest request, @RequestParam("testValue")String[] values, Model model){
        Map<String,String> allMailAddress = new HashMap<>(mailService.getAllMailAddress());
        model.addAttribute("allMailAddress",allMailAddress);
        HttpSession session = request.getSession();

//        String newBody = m.getBody().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        //텍스트 에어리어는 화딱지 나게 태그를 따로 제거해줘야함.
        for (String value : values) {


            String newBody = m.getBody();
            LocalDateTime now = LocalDateTime.now();

            Mail resMail = new Mail();
            resMail.setTitle(m.getTitle()); //제목
            resMail.setBody(newBody); //내용
//        resMail.setSendDate(now.toString()); //현재 날짜 (2023-04-11T12:22:08.845386100)
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            resMail.setSendDate(formatDate);
            resMail.setView(0); //열람여부 0 -> false , 1 -> true

            resMail.setSender((String) session.getAttribute("log")); //보내는 사람 id (현재 로그인된 사람 불러옴)
            //String type to Object
            // 로그인 멤버 : 세션 user 참조
            Member member = (Member) session.getAttribute("user");
            resMail.setSender_name(member.getMem_name()); // 보내는사람 이름
            resMail.setSender_email(member.getEmail()); // 보내는사람 이메일
//            resMail.setReceiver(m.getReceiver()); //받는사람 이메일
            resMail.setReceiver(value);

            //상황에 맡게 DTO로 가공한 메일 객체를 재 삽입.
            mailSendService.sendMail(resMail);

        }
//        return "redirect:/mail/mailForm";
            return "mail/mailForm";
    }


//    @GetMapping("/read/{id}")
//    public String readMail(Model model, @PathVariable Long id){
//        List<Mail> mailList = new ArrayList<>();
//        mailList = mailRepository.findAllMailList();
//        model.addAttribute("mailList",mailList);
//        Mail mail = mailRepository.findById(id);
//        model.addAttribute("mail",mail);
//        return "mail/main";
//    }

    @PostMapping("/read")
    @ResponseBody
    public Mail readMail2(Model model, @RequestParam("mailId") Long mailId, HttpServletRequest request){
        Mail mail = mailService.getOneMail(mailId);
//        List<Mail> mailList = mailService.findLogMailList();
//        model.addAttribute("mailList",mailList);
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("user");
        if(mail.getReceiver().equals(member.getEmail())){
            mailService.updateMailView(mailId,member.getEmail());
        }
        model.addAttribute("mail",mail);
        return mail;
    }

    @GetMapping("/reply")
    public String replyMail(Model model, @RequestParam("reply")String reply) {
        model.addAttribute("reply",reply);
        return "mail/mailForm";
    }

    @GetMapping("/dashBoard")
    @ResponseBody
    public String mailDashBoard(Model model){
        List<Mail> mailList = mailService.findLogMailList();
        model.addAttribute("mailList",mailList);
        return "메일대쉬보드";
    }

}
