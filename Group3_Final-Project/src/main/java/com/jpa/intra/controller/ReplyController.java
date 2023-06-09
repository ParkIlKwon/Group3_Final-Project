package com.jpa.intra.controller;

import com.jpa.intra.domain.Reply;
import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.ReplyDTO;
import com.jpa.intra.repository.Board_Repository;
import com.jpa.intra.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final Board_Repository boardRepository;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
    String formattedDate = now.format(formatter);

    @PostMapping("/writeNewReply")
    public String writeNewReply(HttpSession session, Long boardId, ReplyDTO replyDTO) {
        String replyWriter=(String)session.getAttribute("log");
        BoardCommon currentBoard=boardRepository.findByBoardId(boardId);

        Reply reply=new Reply();    // Reply 겍체
        reply.setCreateDate(formattedDate); // 댓글 작성일
        reply.setUpdateDate(null);  // 댓글 수정일
        reply.setReplyContent(replyDTO.getReplyContent());  // 댓글 내용
        reply.setReplyWriter(replyWriter);  // 댓글 쓴 놈
        reply.setCurrentBoard(currentBoard);    // 어느 게시글에 댓글 달았는지(보드커먼 겍체)

        replyService.createNewReply(reply);
        return "redirect:/moveProject";
    }

    @ResponseBody
    @GetMapping("/reply/curreplylist")
    public List<Reply> getCurrentReplyList(@RequestParam("boardId") String boardId) {

        System.out.println("받아온 보드 아이디 대이터 : "+boardId);

        List<Reply> curRpList=replyService.findReplyByBoardId(Long.parseLong(boardId));
        for(int i=0;i<curRpList.size();i++){
            System.out.println("부합하는 댓글 내용 : "+curRpList.get(i).getReplyContent());
        }
        return curRpList;
    }

    @DeleteMapping("/deletethisreply")
    public String deleteThisReply() {
        return "";
    }
}
