package com.jpa.intra.controller;

import com.jpa.intra.query.CalendarDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
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

//    @GetMapping()
//    @ResponseBody //값을 넘겨줌
//    public JSONArray getCalData() {
//        List<CalendarDTO> calDATA = new ArrayList<>(3);
//
//        JSONObject jobj;
//        JSONArray jsonArr = new JSONArray();
//
//        HashMap<String,Object> hash = new HashMap<>();
//
//        for (int i = 0 ; i < 3 ; i++){
//            CalendarDTO cdto = new CalendarDTO();
//            cdto.setTitle(i+"");
//            calDATA.add(cdto);
//        } //calDATA 어레이리스트에 테스트용으로 넣어보기 .
//
//
//        for (int i = 0; i < calDATA.size(); i++) {
//            hash.put("title", calDATA.get(i).getTitle());
//            hash.put("start", calDATA.get(i).getStart());
//            hash.put("end", calDATA.get(i).getEnd());
//
//            jobj = new JSONObject(hash);
//            jsonArr.put(jobj);
//        }
//
//        System.out.println(jsonArr);
//
//        return jsonArr;
//    }


    @GetMapping()
    @ResponseBody
    public List<Map<String, Object>> getEventList() {
        Map<String, Object> event = new HashMap<String, Object>();
        List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
        event.put("start", LocalDate.now());
        event.put("title", "test");
        event.put("end",LocalDate.now());
        eventList.add(event);
        event = new HashMap<String, Object>();
        event.put("start", LocalDate.now().plusDays(3));
        event.put("title", "test2");
        event.put("end",LocalDate.now().plusDays(4));
        eventList.add(event);
        return eventList;
    }





}
