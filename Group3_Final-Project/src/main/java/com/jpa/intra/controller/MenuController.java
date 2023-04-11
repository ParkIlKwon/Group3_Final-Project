package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardApproval;
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

    //대시보드 페이지 이동
    @GetMapping("/moveDashboard")
    public String MoveDashboard(Model model){
        //페이지 이동시 (페이지제목/사이드바 active/top메뉴 변경을 위한 model)
        model.addAttribute("page", "Dashboard");
        return "/pages/dashboard";
    }

    //프로젝트 페이지 이동
    @GetMapping("/moveProject")
    public String MoveProject(Model model){
        List<BoardTask> tlist = boardService.findTasks();
        model.addAttribute("tlist", tlist);
        model.addAttribute("page","프로젝트");
        return "/project/main";
    }

    //캘린더 페이지 이동
    @GetMapping("/moveCalendar")
    public String MoveCalender(Model model){
        model.addAttribute("page", "캘린더");
       return "/calendar/main";
    }

    //드라이브 페이지 이동
    @GetMapping("/moveDrive")
    public String MoveDrive(Model model){
        model.addAttribute("page", "드라이브");
        return "/drive/main";

    }

    // 주소록 페이지 이동
    @GetMapping("/moveMembers")
    public String MoveMember(Model model){
        model.addAttribute("page", "주소록");
        return "/members/main";
    }

    //결재 페이지 이동
    @GetMapping("/moveApproval")
    public String MoveConfirm(Model model){

        List<BoardApproval> alist = boardService.findApproval1();
        model.addAttribute("alist", alist);
        model.addAttribute("page", "결재");
        return "/approval/main";
    }

    //관리자 페이지 이동
    @GetMapping("/moveAdmin")
    public String MoveAdmin(Model model){
        model.addAttribute("page", "관리자");
        return "/admin/main"; //관리자 화면 구성후 링크 수정예정

    }

    @GetMapping("/sendMail")
    public String SendMail(){

        return "/mail/mailForm";
    }


}
