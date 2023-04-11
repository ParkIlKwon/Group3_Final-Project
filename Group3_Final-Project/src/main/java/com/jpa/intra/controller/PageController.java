package com.jpa.intra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pages")
public class PageController {

    @GetMapping("/dashboard")
    public String pagesDashboard(){
        return "/pages/dashboard";
    }

    @GetMapping("/project")
    public String pagesProject(){
        return "/pages/project";
    }

}
