package com.jpa.intra.controller;

import com.jpa.intra.imginit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final imginit imginit;

    @RequestMapping("/")
    public String home(/*Model model*/) {
        //페이지 이동시 (페이지제목/사이드바 active/top메뉴 변경을 위한 model)
//        model.addAttribute("page", "Dashboard"); //페이지 제목

            imginit.run();


        return "login";
    }
}