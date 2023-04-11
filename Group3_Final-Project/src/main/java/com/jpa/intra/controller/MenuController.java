package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final BoardService boardService;
//내비게이션 메뉴 선택에 따라 변동된는 topMenu(sidebar)를 위한 컨트롤러

    @GetMapping("/moveDashboard")
    public String MoveDashboard(Model model){
        model.addAttribute("gnb","topMenu1");
        return "/pages/dashboard";
    }
    @GetMapping("/moveProject")
    public String MoveProject(Model model){
        List<BoardTask> tlist = boardService.findTasks();
        model.addAttribute("tlist", tlist);
        model.addAttribute("side","sidebar2");
        model.addAttribute("gnb","topMenu2");
        return "/project/main";
    }

    @GetMapping("/moveMail")
    public String MoveMail(Model model){
        model.addAttribute("side","sidebar3");
        model.addAttribute("gnb","topMenu3");
        return "/mail/main";
    }

    @GetMapping("/moveCalendar")
    public String MoveCalender(Model model){
        model.addAttribute("side","sidebar4");
        model.addAttribute("gnb","topMenu4");

       return "/calendar/main";

    }

    @GetMapping("/moveDrive")
    public String MoveDrive(Model model){
        model.addAttribute("side","sidebar5");
        model.addAttribute("gnb","topMenu5");
        return "/drive/main";

    }

    @GetMapping("/moveMembers")
    public String MoveMember(Model model){
        model.addAttribute("side","sidebar6");
        model.addAttribute("gnb","topMenu6");
        return "/members/main";
        /*return "/members/list"; //조직도 화면 구성후 조직도 링크로 변경예정*/
    }

    @GetMapping("/moveApproval")
    public String MoveConfirm(Model model){
        model.addAttribute("side","sidebar7");
        model.addAttribute("gnb","topMenu7");
        return "/approval/main";
        /*return "/home"; //결재 화면 구성후 결재 링크로 변경예정*/
    }

    @GetMapping("/moveAdmin")
    public String MoveAdmin(Model model){
        model.addAttribute("side","sidebar8");
        model.addAttribute("gnb","topMenu8");
        return "/admin/main"; //관리자 화면 구성후 링크 수정예정

    }

    @GetMapping("/sendMail")
    public String SendMail(){

        return "/mail/mailForm";
    }


}
