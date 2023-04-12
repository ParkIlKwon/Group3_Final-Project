package com.jpa.intra.controller;

import com.jpa.intra.query.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/admin") //공통된 폴더명 하나로 묶어서 사용
public class AdminController {

    @GetMapping()
    public String adminMain(Model model){

        model.addAttribute("page", "관리자");
        return "admin/main";
    }

    @GetMapping("/logout")
    public String Logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return "pages/loginForm";
    }

}
