package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.query.BoardFreeDTO;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/free/new")
    public String callBoardFreeWriteForm(Model model) {
        model.addAttribute("boardFreeDTO", new BoardFreeDTO());
        return "board/free/boardFreeWriteForm";
    }

    @PostMapping("/board/free/new")
    public String writeNewBoardFree(BoardFreeDTO boardFreeDTO, BindingResult rst) {
        if(rst.hasErrors()) {return "board/free/boardFreeWriteForm";}

        BoardFree boardFree=new BoardFree();
        boardFree.setBoardTitle(boardFreeDTO.getBoardTitle());
        boardFree.setBoardContent(boardFreeDTO.getBoardContent());
        boardFree.setCreateDate(boardFreeDTO.getCreateDate());
        boardFree.setUpdateDate(null);
        boardFree.setViewCount(0);

        boardService.createBoardFree(boardFree);

        return "redirect:/";
    }
}
