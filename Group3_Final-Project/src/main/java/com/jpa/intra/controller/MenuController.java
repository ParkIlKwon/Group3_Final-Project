package com.jpa.intra.controller;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.Mail;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Reply;
import com.jpa.intra.domain.board.BoardApproval;
import com.jpa.intra.domain.board.BoardNotice;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardNoticeDTO;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.query.ReplyDTO;
import com.jpa.intra.repository.File_Repository;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.BoardService;
import com.jpa.intra.service.MailService;
import com.jpa.intra.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final BoardService boardService;
    private final ReplyService replyService;
    private final MailService mailService;
    
    private final Member_Repository member_repository;

    //대시보드 페이지 이동
    @GetMapping("/moveDashboard")
    public String MoveDashboard(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null){
            return "login";
        }
        //페이지 이동시 (페이지제목/사이드바 active 변경을 위한 model)
        model.addAttribute("page", "Dashboard");

        //대시보드에 노출되는 메일 목록
        List<Mail> mailList = mailService.findLogMailList();
        model.addAttribute("mailList",mailList);
        //대시보드에 노출되는 공지 목록
        List<BoardNotice> noticeList = boardService.getNoticeList();
        model.addAttribute("notiList",noticeList);
        //대시보드에 노출되는 프로젝트 목록
        List<BoardTask> tlist=boardService.findTasks();
        model.addAttribute("tlist", tlist);
        return "dashboard/main";
    }

    //공지 페이지 이동
    @GetMapping("/moveNotice")
    public String MoveNotice(Model model){
        model.addAttribute("page", "공지사항");
        //공지사항 리스트
        List<BoardNotice> nlist=boardService.getNoticeList();
        model.addAttribute("nlist", nlist);
        return "board/notice";
    }

    //프로젝트 페이지 이동
    @GetMapping("/moveProject")
    public String MoveProject(Model model){
        List<BoardTask> tlist=boardService.findTasks();
        List<Reply> rplist=replyService.findReply();
        model.addAttribute("tlist", tlist);
        model.addAttribute("replyDTO", new ReplyDTO());
        model.addAttribute("page","프로젝트");
        model.addAttribute("rplist", rplist);
        return "project/main";
    }

    //캘린더 페이지 이동
    @GetMapping("/moveCalendar")
    public String MoveCalender(Model model){
        model.addAttribute("page", "캘린더");
       return "calendar/main";
    }

    @GetMapping("/projectCalendar")
    public String projectCalendar(Model model){
        model.addAttribute("page", "캘린더");
        return "calendar/getProject";
    }

    @GetMapping("/holidayCalendar")
    public String holidayCalendar(Model model){
        model.addAttribute("page", "캘린더");
        return "calendar/holiday";
    }




    final private File_Repository fileRepository;
    //드라이브 페이지 이동
    @GetMapping("/moveDrive")
    public String MoveDrive(Model model, HttpServletRequest request){
        model.addAttribute("page", "드라이브");
        HttpSession session = request.getSession();

        List<FileEntity> flist = fileRepository.findFilelistById(
                (String) session.getAttribute("log"));
        model.addAttribute("fileList" , flist);

        return "/drive/main";

    }

    // 주소록 페이지 이동
    @GetMapping("/moveMembers")
    public String MoveMember(Model model){
        model.addAttribute("page", "주소록");
        // 전체 사원 목록
        List<Member> memberList = member_repository.getAllMemberList();
        model.addAttribute("memberList",memberList);
        return "members/main";
    }

    //결재 페이지 이동
    @GetMapping("/moveApproval")
    public String MoveConfirm(HttpSession session, Model model){
        Member curUser=(Member)session.getAttribute("user");
        List<BoardApproval> alist = boardService.findApproval();
        //모든 결재리스트를 불러와서 현재 로그인된 회원에 해당되는 결재객체만 뽑아서 담아주는 로직
        List<BoardApproval> myAList = boardService.findMyApprovalList(alist,curUser.getMem_name());

        if(curUser.getTeam().getTeam_name().equals("인사부")) model.addAttribute("alist", alist);
        else model.addAttribute("alist", myAList);

        model.addAttribute("page", "결재");
        model.addAttribute("curUser",curUser);

        return "approval/main";
    }

    //관리자 페이지 이동
    @GetMapping("/moveAdmin")
    public String MoveAdmin(Model model){
        model.addAttribute("page", "관리자");
        return "admin/main"; //관리자 화면 구성후 링크 수정예정

    }

    @GetMapping("/sendMail")
    public String SendMail(){

        return "mail/mailForm";
    }


}
