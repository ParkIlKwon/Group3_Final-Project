package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.domain.board.BoardTask;
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
        System.out.println("fdsdafsdafsdafsdasfda");
        System.out.println(eventList.size());
        return eventList;
    }

    //현재 로그인 된 회원의 캘린더에서 클릭이벤트 발생시
    //해당 일정을 보내줌.
    @PostMapping("/getSingleData")
    @ResponseBody
    public BoardCommon getBoard (@RequestParam("title")String title, HttpServletRequest request){
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("log");

        //해당일정은 제목 , 현재 로그인된 회원아이디값을 넘겨줘서 찾음 .
        System.out.println(user  + " " +title);
        BoardCommon tc = calendarService.getSingleCalendar(user,title);


        return tc;
    }




}
