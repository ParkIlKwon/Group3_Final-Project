package com.jpa.intra.controller;

import com.jpa.intra.query.CalendarDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/Calendar")
@RequiredArgsConstructor
public class CalendarController {

    @GetMapping()
    @ResponseBody //값을 넘겨줌
    public JSONArray getCalData() {
        List<CalendarDTO> calDATA = new ArrayList<>(3);

        JSONObject jobj;
        JSONArray jsonArr = new JSONArray();

        HashMap<String,Object> hash = new HashMap<>();

        CalendarDTO cdto = new CalendarDTO();
        
        cdto.setTitle("1");
        calDATA.add(cdto);

        cdto.setTitle("2");
        calDATA.add(cdto);

        cdto.setTitle("3");
        calDATA.add(cdto);


        for (int i = 0; i < calDATA.size(); i++) {
            hash.put("title", calDATA.get(i).getTitle());
            hash.put("start", calDATA.get(i).getStart());
            hash.put("end", calDATA.get(i).getEnd());

            jobj = new JSONObject(hash);
            jsonArr.put(jobj);
        }

        System.out.println(jsonArr);

        return jsonArr;
    }





}
