package com.jpa.intra.controller;

import com.jpa.intra.query.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
//내비게이션 메뉴 선택에 따라 변동된는 sidebar와 css를 위한 컨트롤러
    @GetMapping("/moveProject")
    public String MoveProject(Model model){
        model.addAttribute("banner","banner2");
        model.addAttribute("side","sidebar2");
        return "/project/main";
    }
    @GetMapping("/moveMail")
    public String MoveMail(Model model){
        model.addAttribute("banner","banner3");
        model.addAttribute("side","sidebar3");
        return "/mail/main";
    }
    @GetMapping("/moveCalender")
    public String MoveCalender(Model model){
        model.addAttribute("banner","banner4");
        model.addAttribute("side","sidebar4");
        return "/calender/main"; //캘린더화면 구현후 /calender/main으로 변경
    }
    @GetMapping("/moveDrive")
    public String MoveDrive(Model model){
        model.addAttribute("banner","banner5");
        model.addAttribute("side","sidebar5");
        return "/drive/main";
    }
    @GetMapping("/moveMembers")
    public String MoveMember(Model model){
        model.addAttribute("banner","banner6");
        model.addAttribute("side","sidebar6");
        return "/members/list"; //조직도 화면 구성후 조직도 링크로 변경예정
    }
    @GetMapping("/moveConfirm")
    public String MoveConfirm(Model model){
        model.addAttribute("banner","banner7");
        model.addAttribute("side","sidebar7");
        return "/home"; //결재 화면 구성후 결재 링크로 변경예정
    }
    @GetMapping("/moveBoard")
    public String MoveAdmin(Model model){
        model.addAttribute("banner","banner8");
        model.addAttribute("side","sidebar8");
        return "/board/boardFreeList"; //관리자 화면 구성후 링크 수정예정
    }

}
