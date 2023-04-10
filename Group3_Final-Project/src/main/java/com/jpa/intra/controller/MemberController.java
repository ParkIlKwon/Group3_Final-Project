package com.jpa.intra.controller;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Member;
import com.jpa.intra.query.LoginDTO;
import com.jpa.intra.query.MemberDTO;
import com.jpa.intra.service.MailService;
import com.jpa.intra.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/members") //공통된 폴더명 하나로 묶어서 사용
public class MemberController {

    private final MemberService service;
    @GetMapping()
    public String addForm(Model model){

        model.addAttribute("memberDTO",new MemberDTO());
        //memberDTO 형식으로 생성자 만들어서 보내줌 .
        return "pages/joinForm";
    }

    @PostMapping() //form 에서 post형식으로
                                //받아오면 여기로 들어옴 .
    public String addPro(@Valid MemberDTO getmember, BindingResult result,Model model){

        if (result.hasErrors()) { //Valid 즉 DTO에 @NotNull 해둔 항목이 없으면 여기로 들어옴
            model.addAttribute("errorMsg", "내용을 전부 채워주세요.");
            model.addAttribute("MemberDTO",new MemberDTO());
            //model.addAttribute >> html로 객체 보내줌 . setAttribute랑 비슷한 역활  
            return "pages/joinForm"; //폼으로 다시 감 .
        }

        Member m = new Member(); // 받아온 정보 Member 객체로 파싱
        m.setMem_id(getmember.getId());
        m.setMem_pw(getmember.getPw());
        m.setMem_name(getmember.getName());
        Address address = new Address(getmember.getAddress_name(),getmember.getAddress_road());
        m.setAddress(address); //Address 객체생성후 받아온 주소정보 각각 넣어줌 
        service.Join(m); //서비스로 등록 영속성 - DB 반영

        return "redirect:/"; //홈 페이지로 리디렉션.
    }

    @GetMapping("/login")
    public String LoginForm(Model model){

        model.addAttribute("memberDTO",new MemberDTO());
        //memberDTO 형식으로 생성자 만들어서 보내줌 .
        return "pages/loginForm";
    }

    @PostMapping("/login") //로그인 세션 사용       ,  memberDTO 형식으로 생성자 만들어서 보내줌 .
    public String LoginPro(HttpServletRequest request, LoginDTO logMem){
        HttpSession session = request.getSession();

        Member m = service.Login(logMem.getId(),logMem.getPw());

        //세션으로 로그인 아이디를 넘겨줌 .
        if(m == null){
            return "pages/loginForm";
        }
        session.setAttribute("log",m.getMem_id());
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String Logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }


    @Autowired
    MailService registerMail;


    @PostMapping("/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        String code = registerMail.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }


}
