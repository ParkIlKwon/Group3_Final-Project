package com.jpa.intra.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String home(Model model) {
        model.addAttribute("banner","banner1");
        model.addAttribute("side","sidebar1");
        return "pages/dashboard";
//        return "home";
    }
}
