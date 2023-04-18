package com.jpa.intra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Reply;
import com.jpa.intra.domain.board.BoardApproval;
import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardApprovalDTO;
import com.jpa.intra.query.BoardApprovalInfoDTO;
import com.jpa.intra.query.BoardFreeDTO;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.BoardService;
import com.jpa.intra.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final Member_Repository member_repository;

    // 현재 날짜와 시간 정보를 LocalDateTime을 통해 가져오고 Formatter를 이용하여 필요한 형식으로 치환하다.
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    String formattedDate = now.format(formatter);

    // 자유게시판(공지)으로 이동
    @GetMapping("/board/newfreeboard")
    public String callBoardFreeWriteForm(Model model) {
        model.addAttribute("boardFreeDTO", new BoardFreeDTO());
        return "board/boardFreeWriteForm";
    }

    // 자유게시판(공지) 작성
    @PostMapping("/board/newfreeboard")
    public String writeNewBoardFree(BoardFreeDTO boardFreeDTO, BindingResult rst) {

        if(rst.hasErrors()) {return "board/boardFreeWriteForm";}

        BoardFree boardFree=new BoardFree();    // boardFree객체
        boardFree.setBoardTitle(boardFreeDTO.getBoardTitle());  //제목
        boardFree.setBoardContent(boardFreeDTO.getBoardContent());  //내용
        boardFree.setCreateDate(formattedDate);    //작성일
        boardFree.setUpdateDate(null);    //수정일
        boardFree.setViewCount(0);      //조회수

        boardService.createBoardFree(boardFree);

        // 글 작성이 완료되면 경로이동
        return "redirect:/";
    }

    // 업무게시판으로 이동
    @GetMapping("/board/newtaskboard")
    public String callBoardTaskWriteForm(Model model) {
        model.addAttribute("boardTaskDTO", new BoardTaskDTO());
        return "board/boardTaskWriteForm";
    }

    // 업무게시판 작성
    @PostMapping("/board/newtaskboard")
    public String writeNewBoardTask(HttpSession session, BoardTaskDTO boardTaskDTO) {
        String boardWriter=(String)session.getAttribute("log");  // 세션에서 값 가져오기

        BoardTask boardTask=new BoardTask();    //boardTask객체
        boardTask.setBoardTitle(boardTaskDTO.getBoardTitle());  //제목
        boardTask.setBoardContent(boardTaskDTO.getBoardContent());  //내용
        boardTask.setCreateDate(formattedDate); //작성일
        boardTask.setUpdateDate(null);  //수정일
        boardTask.setBoardWriter(boardWriter);
        boardTask.setResponsibleMemNum(boardTaskDTO.getResponsibleMemNum());    //담당자번호
        boardTask.setStartDate(boardTaskDTO.getStartDate());    //업무시작 날짜
        boardTask.setEndDate(boardTaskDTO.getEndDate());    //업무 종료 날짜
        boardTask.setTeamNum(boardTaskDTO.getTeamNum());    //담당자의 팀번호
        boardTask.setProgress("TO_DO");  //진행상황("TO_DO","IN_PROGRESS","DONE") : 최초 저장은 무조건 "할 일"이기 때문에 "TO_DO"고정

        boardService.createBoardTask(boardTask);
        
        // 글 작성이 완료되면 경로이동
        return "redirect:/moveProject";
    }

    // 업무게시판 리스트
    @GetMapping("/board/boardtasklist")
    public String boardTaskList(Model model) {
        List<BoardTask> tlist=boardService.findTasks();
        List<Reply> rplist=replyService.findReply();
        model.addAttribute("tlist", tlist);
        model.addAttribute("rplist", rplist);
        return "board/boardTaskList";
    }

    // 업무게시판 삭제
    @DeleteMapping("/board/deleteboardtask")
    public ResponseEntity<Void> deleteBoardTask(@RequestBody Map<String, Object> reqData) {
        System.out.println("this is deleteBoardTask");
        Long boardId = Long.parseLong(reqData.get("boardId").toString());
        System.out.println("Welcome to another world episode of ajax, This is deleteBoardTask Method. I can help you with delete task board number "+boardId);

        boardService.deleteBoardTaskById(boardId);
        return ResponseEntity.noContent().build();
    }

    // 업무 진행상태 변경
    @PostMapping("/board/changetaskprogress")
    public ResponseEntity<Void> changeTaskProgress(@RequestParam Long boardId, @RequestParam String boardProgress) {
        boardService.changeTaskProgress(boardId, boardProgress);
        return ResponseEntity.noContent().build();
    }

    // 결재게시판으로 이동
    @GetMapping("/board/newapprovalvacationboard")
    public String callBoardApprovalWriteForm1(Model model) {
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appvacation";
    }

    @GetMapping("/board/newapprovalovertimeboard")
    public String callBoardApprovalWriteForm2(Model model) {
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appovertime";
    }

    @GetMapping("/board/newapprovalwocboard")
    public String callBoardApprovalWriteForm3(Model model) {
        List<Member> mlist = member_repository.getAllMemberList();
        Member hrGuy = pickOneHrGuy(mlist);
        model.addAttribute("boardApprovalDTO", new BoardApprovalDTO());
        model.addAttribute("boardApprovalInfoDTO", new BoardApprovalInfoDTO());
        model.addAttribute("hrGuy", hrGuy);
        return "approval/appwoc";
    }

    private BoardApproval createNewBoardApproval(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO, String boardTitle, String approvalType) {
        String boardWriter=(String)session.getAttribute("log");
        LocalDateTime plus7Days = now.plusDays(7);
        String sevenFormattedDate = plus7Days.format(formatter);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode infoNode = mapper.createObjectNode();

        Member hrGuy = member_repository.findById(memberId);

        switch (approvalType) {
            case "VACATION":
                infoNode.put("startDate", boardApprovalInfoDTO.getStartDate());
                infoNode.put("endDate", boardApprovalInfoDTO.getEndDate());
                infoNode.put("deduction", boardApprovalInfoDTO.getDeduction());
                break;
            case "OVERTIME":
                infoNode.put("endTime", boardApprovalInfoDTO.getEndTime());
                infoNode.put("bonusAllowance", boardApprovalInfoDTO.getBonusAllowance());
                break;
            case "WORK_HOUR_CHANGE":
                infoNode.put("goToOffice", boardApprovalInfoDTO.getGoToOffice());
                infoNode.put("leaveOffice", boardApprovalInfoDTO.getLeaveOffice());
                break;
        }

        String infoJson = infoNode.toString();

        boardApprovalDTO.setApprovalInfo(infoJson);

        BoardApproval boardApproval=new BoardApproval();
        boardApproval.setBoardTitle(boardTitle);
        boardApproval.setBoardContent(boardApprovalDTO.getBoardContent());
        boardApproval.setCreateDate(formattedDate); // 작성일, 즉 기안 확인 시작날짜
        boardApproval.setUpdateDate(null);  // 수정일
        boardApproval.setBoardWriter(boardWriter);  //requestorMember
        boardApproval.setDueDate(sevenFormattedDate); // 기안 확인 마감날짜 (작성일로부터 7일)
        boardApproval.setApprovalType(approvalType);
        boardApproval.setApprovalStatus("REQUESTED");
        boardApproval.setApproverMemNum(hrGuy);  // 승인하는 사람의 정보를 담은 맴버객체
        boardApproval.setApprovalInfo(boardApprovalDTO.getApprovalInfo());

        return boardApproval;
    }

//    @PostMapping("/board/newapprovalvacationboard")
//    public String writeNewBoardApprovalForm1(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO) {
//        BoardApproval boardApproval=createNewBoardApproval(session, memberId, boardApprovalDTO, boardApprovalInfoDTO, "approval vacation title", "VACATION");
//        boardService.createBoardApproval1(boardApproval);
//        return "redirect:/moveApproval";
//    }
    @PostMapping("/board/newapprovalvacationboard")
    @ResponseBody
    public String writeNewBoardApprovalForm1Ajax(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO) {
        BoardApproval boardApproval = createNewBoardApproval(session, memberId, boardApprovalDTO, boardApprovalInfoDTO, "approval vacation title", "VACATION");
        boardService.createBoardApproval1(boardApproval);
        return "success";
    }


    @PostMapping("/board/newapprovalovertimeboard")
    public String writeNewBoardApprovalForm2(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO) {
        BoardApproval boardApproval=createNewBoardApproval(session, memberId, boardApprovalDTO, boardApprovalInfoDTO, "approval overtime title", "OVERTIME");
        boardService.createBoardApproval2(boardApproval);
        return "redirect:/moveApproval";
    }

    @PostMapping("/board/newapprovalwocboard")
    public String writeNewBoardApprovalForm3(HttpSession session, Long memberId, BoardApprovalDTO boardApprovalDTO, BoardApprovalInfoDTO boardApprovalInfoDTO) {
        BoardApproval boardApproval=createNewBoardApproval(session, memberId, boardApprovalDTO, boardApprovalInfoDTO, "approval overtime title", "WORK_HOUR_CHANGE");
        boardService.createBoardApproval3(boardApproval);
        return "redirect:/moveApproval";
    }

    public Member pickOneHrGuy(List<Member> mlist) {
        // "인사부" 팀에 속한 멤버들을 찾다.
        List<Member> hrMembers=mlist.stream()
                .filter(member->member.getTeam().getTeam_name().equals("인사부"))
                .collect(Collectors.toList());

        // 추출된 멤버들 중에서 랜덤으로 하나를 선택합니다.
        int r=(int)(Math.random()*hrMembers.size());
        Member hrGuy=hrMembers.get(r);

        System.out.println("랜덤 인사부 사원 이름 : "+hrGuy.getMem_name());
        return hrGuy;
    }

    @GetMapping("/board/changeSession")
    public String sessionChange(HttpServletRequest req){
        System.out.println("you ve reached here");
        HttpSession session=req.getSession();
        String onOff=(String)session.getAttribute("onOff");
        onOff="on";
        session.setAttribute("onOff",onOff);
        System.out.println("bno");
        return "redirect:/moveProject";
    }

    @PostMapping("/test")
    @ResponseBody
    public String selfcloseTest(){
        System.out.println("This is selfcloseTest please check this message, i'll show my data");


        System.out.println("sry, null");

       return "test";
    }


}
