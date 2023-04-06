package com.jpa.intra.controller;

import com.jpa.intra.query.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("banner","banner1");
        model.addAttribute("side","sidebar1");
        return "/home";
    }

}
