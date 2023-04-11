package com.jpa.intra.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        //페이지 이동시 (페이지제목/사이드바 active/top메뉴 변경을 위한 model)
        model.addAttribute("page", "Dashboard"); //페이지 제목
        return "pages/dashboard";
    }
}