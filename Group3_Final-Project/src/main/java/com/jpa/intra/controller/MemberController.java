package com.jpa.intra.controller;

import com.jpa.intra.query.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/members/addmember")
    public String addMember(@Valid MemberDTO getmember){
                //DTO 클래스에서 특정 형식으로 객체를 만들어서 가져옴


        return "redirect:/";
    }
}
