package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardFreeDTO;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    String formattedDate = now.format(formatter);

    @GetMapping("/board/freeboardlist")
    public String showBoardFreeList(Model model) {
        return "board/boardFreeList";
    }

    @GetMapping("/board/newfreeboard")
    public String callBoardFreeWriteForm(Model model) {
        model.addAttribute("boardFreeDTO", new BoardFreeDTO());
        return "board/boardFreeWriteForm";
    }

    @PostMapping("/board/newfreeboard")
    public String writeNewBoardFree(BoardFreeDTO boardFreeDTO, BindingResult rst) {

        log.info("ttttt={}" , boardFreeDTO.getBoardContent());

        if(rst.hasErrors()) {return "board/boardFreeWriteForm";}

        BoardFree boardFree=new BoardFree();
        boardFree.setBoardTitle(boardFreeDTO.getBoardTitle());
        boardFree.setBoardContent(boardFreeDTO.getBoardContent());
        boardFree.setCreateDate(formattedDate);
        boardFree.setUpdateDate(null);
        boardFree.setViewCount(0);

        boardService.createBoardFree(boardFree);

        return "redirect:/";
    }

    @GetMapping("/board/newtaskboard")
    public String callBoardTaskWriteForm(Model model) {
        model.addAttribute("boardTaskDTO", new BoardTaskDTO());
        return "board/boardTaskWriteForm";
    }

    @PostMapping("/board/newtaskboard")
    public String writeNewBoardTask(BoardTaskDTO boardTaskDTO) {
        System.out.println("-----------------------------");
        System.out.println("비타이틀 : "+boardTaskDTO.getBoardTitle());
        System.out.println("리스폰시블맴넘 : "+boardTaskDTO.getResponsibleMemNum());
        System.out.println("팀넘 : "+boardTaskDTO.getTeamNum());
        System.out.println("크리애이트대이트 : "+formattedDate);

        BoardTask boardTask=new BoardTask();
        boardTask.setBoardTitle(boardTaskDTO.getBoardTitle());
        boardTask.setBoardContent(boardTaskDTO.getBoardContent());
        boardTask.setCreateDate(formattedDate);
        boardTask.setUpdateDate(null);
        boardTask.setResponsibleMemNum(boardTaskDTO.getResponsibleMemNum());
        Long id = boardTaskDTO.getResponsibleMemNum().getId();
        boardTask.setTeamNum(boardTaskDTO.getTeamNum());
        boardTask.setProgress(boardTaskDTO.getProgress());

        boardService.createBoardTask(boardTask);

        return "redirect:/";
    }
}
