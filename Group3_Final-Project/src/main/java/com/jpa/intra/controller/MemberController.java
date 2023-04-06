package com.jpa.intra.controller;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Member;
import com.jpa.intra.query.LoginDTO;
import com.jpa.intra.query.MemberDTO;
import com.jpa.intra.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/members") //공통된 폴더명 하나로 묶어서 사용
public class MemberController {

    private final MemberService service;
    @GetMapping("/addmember")
    public String addForm(Model model){

        model.addAttribute("memberDTO",new MemberDTO());
        //memberDTO 형식으로 생성자 만들어서 보내줌 .
        return "members/joinForm";
    }

    @PostMapping("/addmember") //form 에서 post형식으로
                                //받아오면 여기로 들어옴 .
    public String addPro(@Valid MemberDTO getmember, BindingResult result,Model model){

        if (result.hasErrors()) { //Valid 즉 DTO에 @NotNull 해둔 항목이 없으면 여기로 들어옴
            model.addAttribute("errorMsg", "내용을 전부 채워주세요.");
            model.addAttribute("MemberDTO",new MemberDTO());
            //model.addAttribute >> html로 객체 보내줌 . setAttribute랑 비슷한 역활  
            return "members/joinForm"; //폼으로 다시 감 .
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
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String LoginPro(Model model, LoginDTO logMem){

        model.addAttribute("memberDTO",new MemberDTO());
        //memberDTO 형식으로 생성자 만들어서 보내줌 .


        return "redirect:/";
    }



}
