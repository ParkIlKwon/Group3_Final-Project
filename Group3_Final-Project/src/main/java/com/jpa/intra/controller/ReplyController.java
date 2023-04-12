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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:/";
    }

//    @GetMapping("/board/getCurrentBoardReplyList")
//    public String giveCurrentReplySession(@RequestParam("boardId") Long boardId, Model model) {
//        List<Reply> curRplist=replyService.findReplyByBoardId(boardId);
//        for(int i=0;i<curRplist.size();i++) {
//            System.out.println("컬리플라이리스트 컨탠트 : "+curRplist.get(i).getReplyContent());
//            System.out.println("컬리플라이리스트 보드넘 : "+curRplist.get(i).getCurrentBoard().getId());
//        }
//        model.addAttribute("curRplist", curRplist);
//        System.out.println("AJAX가 발동하면 여기로 와야하는데... 될랑가 안될랑가 모르겠네... 집에 존나 가고 싶다.");
//        return "/project/main";
//    }
}
