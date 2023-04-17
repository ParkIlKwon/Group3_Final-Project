package com.jpa.intra.service;

import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.repository.Board_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor //notNull이나 final 붙은거 자동생성자주입
@Service
@Transactional(readOnly = true)
public class CalendarService {

    private final BoardService boardService;
    private final Board_Repository boardRepository;
    public List<Map<String, Object>> getEventList(String userId){

        Map<String, Object> event ;
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();

        List<BoardTask> boardTaskList = boardService.findTasks(userId);

        for (BoardTask t: boardTaskList) {
            event = new HashMap<String, Object>();
            event.put("title", t.getBoardTitle());
            event.put("start", t.getStartDate());
            event.put("end",t.getEndDate());
            event.put("color","orange");
            eventList.add(event);
        }

        return eventList;
    }


    public BoardTask getSingleCalendar(String uid, String title){
        BoardCommon tempCommon = boardRepository.findByBoardUserIdAndTitle(uid,title);

        return boardRepository.findTaskByBoardId(tempCommon.getId());
    }




}
