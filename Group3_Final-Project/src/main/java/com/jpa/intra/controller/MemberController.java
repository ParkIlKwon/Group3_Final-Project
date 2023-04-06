package com.jpa.intra.controller;

import com.jpa.intra.query.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/members") //공통된 폴더명 하나로 묶어서 사용
public class MemberController {

    @GetMapping("/addmember")
    public String addForm(Model model){

        model.addAttribute("memberDTO",new MemberDTO());
        //memberDTO 형식으로 생성자 만들어서 보내줌 .
        return "members/joinForm";
    }

    @PostMapping("/addmember") //form 에서 post형식으로
                                //받아오면 여기로 들어옴 .
    public String addAction(@Valid MemberDTO getmember){
        //DTO 클래스에서 특정 형식으로 객체를 만들어서 가져옴

        return "redirect:/"; //홈 페이지로 리디렉션.
    }


}
