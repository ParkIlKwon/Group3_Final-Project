package com.jpa.intra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    @GetMapping("/main")
    public String mailMain(){
        return "mail/main";
    }

    @GetMapping("/mailForm")
    public String mailForm(){
        return "mail/mailForm";
    }

    @PostMapping("/mailForm")
    public String mailFrom(){

        return "pages/moveMail";
    }

}
