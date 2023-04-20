package com.jpa.intra.service;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.query.BoardTaskDTO;
import com.jpa.intra.repository.Board_Repository;
import com.jpa.intra.repository.Member_Repository;
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
    public List<Map<String, Object>> getEventList(Member m){

        Map<String, Object> event ;
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();

        List<BoardTask> boardTaskList = boardRepository.findBoardTaskByName(m.getMem_name());

        for (BoardTask t: boardTaskList) {
            event = new HashMap<String, Object>();
            event.put("title", t.getBoardTitle());
            event.put("start", t.getStartDate());
            event.put("end",t.getEndDate());
            event.put("color","orange");
            eventList.add(event);
        }


        event = new HashMap<String,Object>();
        event.put("title","birthday");
        event.put("start", m.getBirthday());
        event.put("end",m.getBirthday());
        event.put("color","skyblue");
        eventList.add(event);


        event = new HashMap<String,Object>();
        event.put("title","(연차)");
        event.put("start",m.getVacationStart());
        event.put("end",m.getVacationEnd());
        event.put("color","gray");




        return eventList;
    }


    public BoardTask getSingleCalendar(String name, String title){
        BoardCommon tempCommon = boardRepository.findByBoardUserIdAndTitle(name,title);

        return boardRepository.findTaskByBoardId(tempCommon.getId());
    }

    @Transactional
    public void EditTask(BoardTaskDTO taskDTO){
        //수정할 보드에 해당되는 객체를 불러와서 수정할 내용만 넣어서 영속성객체로 넘김

        BoardTask temp = boardService.findTaskByBoardId(taskDTO.getBoardId());
        temp.setBoardTitle(taskDTO.getBoardTitle());
        temp.setBoardContent(taskDTO.getBoardContent());
        temp.setStartDate(taskDTO.getStartDate());
        temp.setEndDate(taskDTO.getEndDate());

        boardRepository.update(temp);
    }


}
