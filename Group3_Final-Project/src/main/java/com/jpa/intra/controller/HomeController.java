package com.jpa.intra.controller;

import com.jpa.intra.imginit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //Simple Logging Facade for Java)
//오류 메시지 확인용도
@RequiredArgsConstructor
public class HomeController {

    private final imginit imginit;

    @RequestMapping("/")
    public String home() {
        imginit.run();
        return "login";
    }
}