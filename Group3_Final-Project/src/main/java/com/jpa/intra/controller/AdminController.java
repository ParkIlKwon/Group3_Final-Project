package com.jpa.intra.controller;

import com.jpa.intra.domain.Member;
import com.jpa.intra.query.MemberDTO;
import com.jpa.intra.repository.Member_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/admin") //공통된 폴더명 하나로 묶어서 사용
public class AdminController {

    private final Member_Repository member_repository;

    //관리자_메인_페이지 (아직 미구현)
    @GetMapping()
    public String adminMain(Model model){

        model.addAttribute("page", "관리자");
        return "admin/main";
    }

    //관리자_사원관리 페이지
    @GetMapping("/admin_member")
    public String admin_member(Model model){
        List<Member> memberList = member_repository.getAllMemberList();
        model.addAttribute("memberList",memberList);
        model.addAttribute("page", "관리자");
        return "admin/admin_member";
    }

    //관리자_사원관리_사원등록 페이지
    @GetMapping("/join")
    public String memberJoin(Model model){
        model.addAttribute("page", "관리자");
//        return "pages/joinFormAlpa";
        return "admin/joinForm";
    }

}
