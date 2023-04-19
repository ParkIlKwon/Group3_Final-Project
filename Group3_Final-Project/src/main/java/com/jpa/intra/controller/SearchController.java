package com.jpa.intra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller //model 을 지정 , view를 반환 시켜주는 컨트롤러 역활
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/Search") //공통된 폴더명 하나로 묶어서 사용
public class SearchController {
    @PostMapping
    @ResponseBody
    public String Searching(@RequestParam("word")String word){
        Map<String,String> searching = new HashMap<>();
        
        searching.put("드라이브" , "/moveDrive");
        searching.put("공지사항","/moveNotice");
        searching.put("캘린더","/moveCalendar");
        searching.put("메일","/mail/main");
        searching.put("어드민" , "/moveAdmin");
        searching.put("프로젝트","/moveProject");
        searching.put("주소록","/moveMembers");

        String result = searching.get(word);
        

        return result;
    }

}
