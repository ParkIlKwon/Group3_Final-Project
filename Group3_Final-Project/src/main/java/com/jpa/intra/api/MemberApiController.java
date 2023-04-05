package com.jpa.intra.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {

    @PostMapping(value = "/join")












    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }




}
