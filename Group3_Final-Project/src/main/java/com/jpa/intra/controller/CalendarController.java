package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/Calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final BoardService boardService;

    @GetMapping()
    @ResponseBody
    public List<Map<String, Object>> getEventList(HttpServletRequest request) {
        Map<String, Object> event ;
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();

        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("log");

        List<BoardTask> boardTaskList2 = boardService.findTasks(userId);

        for (BoardTask t: boardTaskList2) {
            event = new HashMap<String, Object>();
            event.put("title", t.getBoardTitle());
            event.put("start", t.getStartDate());
            event.put("end",t.getEndDate());
            event.put("color","orange");
            eventList.add(event);
        }


        return eventList;
    }





}
