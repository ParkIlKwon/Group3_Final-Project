package com.jpa.intra.controller;

import com.jpa.intra.domain.Member;
import com.jpa.intra.query.MemberDTO;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.MailService;
import com.jpa.intra.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/admin") //공통된 폴더명 하나로 묶어서 사용
public class AdminController {

    private final Member_Repository member_repository;
    private final MemberService memberService;

    //관리자_메인_페이지 (사원관리 페이지)
    @GetMapping()
    public String adminMain(Model model){
        List<Member> memberList = member_repository.getAllMemberList();
        model.addAttribute("memberList",memberList);
        model.addAttribute("page", "관리자");
        return "admin/main";
    }

    //관리자_사원관리_사원등록 페이지
    @GetMapping("/join")
    public String memberJoin(Model model){
        model.addAttribute("page", "관리자");
        return "admin/joinForm";
    }

    @Autowired
    MailService registerMail;
    @PostMapping("/mailConfirm")
    @ResponseBody
    public String mailConfirm(@RequestParam("email") String email) throws Exception {

        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

//  비밀번호 찾기시 이메일 인증 : 아이디와 이메일 값이 등록된 정보와 일치해야만 인증 코드가 발송 , 아닐 시 error 리턴
    @PostMapping("/mailConfirmForFindPw")
    @ResponseBody
    public String mailConfirmForFindPw(@RequestParam("email") String email, @RequestParam("modalId") String modalId) throws Exception {
        Member member = memberService.updateDefaultPw(modalId, email);
        if(member == null) {
            return "error";
        }
        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }
}
