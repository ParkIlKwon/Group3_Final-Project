package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardFreeDTO;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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
    public String writeNewBoardTask(BoardTaskDTO boardTaskDTO) {

        BoardTask boardTask=new BoardTask();    //boardTask객체
        boardTask.setBoardTitle(boardTaskDTO.getBoardTitle());  //제목
        boardTask.setBoardContent(boardTaskDTO.getBoardContent());  //내용
        boardTask.setCreateDate(formattedDate); //작성일
        boardTask.setUpdateDate(null);  //수정일
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
        List<BoardTask> tlist = boardService.findTasks();
        model.addAttribute("tlist", tlist);
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

    @PostMapping("/board/changetaskprogress")
    public ResponseEntity<Void> changeTaskProgress(@RequestParam Long boardId, @RequestParam String boardProgress) {
        boardService.changeTaskProgress(boardId, boardProgress);
        return ResponseEntity.noContent().build();
    }


}
