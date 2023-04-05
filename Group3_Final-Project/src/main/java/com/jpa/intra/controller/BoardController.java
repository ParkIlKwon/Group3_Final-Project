package com.jpa.intra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board/free/new")
    public String callBoardFreeWriteForm(Model model) {
//        model.addAttribute("boardFreeDTO", new BoardFreeDTO());
        return "board/free/boardFreeWriteForm";
    }

}
