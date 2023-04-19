package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.service.BoardService;
import com.jpa.intra.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/Calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final BoardService boardService;
    private final CalendarService calendarService;

    @GetMapping("/getCalendarData")
    @ResponseBody
    public List<Map<String, Object>> getEventList(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("log");

        List<Map<String, Object>> eventList = calendarService.getEventList(userId);
        return eventList;
    }


    //현재 로그인 된 회원의 캘린더에서 클릭이벤트 발생시
    //해당 일정을 보내줌.
    @PostMapping("/getSingleData")
    @ResponseBody
    public BoardTask getBoard (@RequestParam("title")String title, HttpServletRequest request){
        if(title.equals("birthday")){
            return null;
        }
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("log");

        //해당일정은 제목 , 현재 로그인된 회원아이디값을 넘겨줘서 찾음 .
        //System.out.println(user  + " " +title);
        BoardTask tc = calendarService.getSingleCalendar(user,title);

        return tc;
    }


    @PostMapping("/deleteData")
    @ResponseBody
    public String deleteData(@RequestParam("boardId")String boardId){
        //System.out.println(boardId); //보드 아이디(pk)로 삭제
        boardService.deleteBoardTaskById(Long.parseLong(boardId));
        return "ok";
    }

    @PostMapping("/editData")
    @ResponseBody
    public String editData(BoardTaskDTO taskDTO){ 
        //DTO로 내가 받고 싶은 필드만 넣어줘서 받아옴 .
        //서비스에서 수정로직으로 DTO 를 넘겨주고
        System.out.println(taskDTO.getBoardContent());
        calendarService.EditTask(taskDTO);
        return "ok";
    } //수정할 필드만 따로 서비스에서 정해서 DB에 반영 .



}
