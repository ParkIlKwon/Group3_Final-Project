package com.jpa.intra.controller;

import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/Calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final BoardService boardService;

    @GetMapping()
    @ResponseBody
    public List<Map<String, Object>> getEventList() {
        Map<String, Object> event ;
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();

        List<BoardTask> boardTaskList = boardService.findTasks();
        System.out.println(boardTaskList.size());
        for (BoardTask t: boardTaskList) {
            event = new HashMap<String, Object>();
            event.put("title", t.getBoardTitle());
            event.put("start", t.getStartDate());
            event.put("end",t.getEndDate());
            eventList.add(event);
        }

//        event.put("start", LocalDate.now());
//        event.put("title", "test");
//        event.put("end",LocalDate.now());
//        eventList.add(event);
//        event = new HashMap<String, Object>();
//        event.put("start", LocalDate.now().plusDays(3));
//        event.put("title", "test2");
//        event.put("end",LocalDate.now().plusDays(4));
//        eventList.add(event);
        return eventList;
    }





}
