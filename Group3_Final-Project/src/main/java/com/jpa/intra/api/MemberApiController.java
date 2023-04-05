package com.jpa.intra.api;

import com.jpa.intra.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {



    @PostMapping(value = "/join")
    public AddMember add(@RequestBody @Valid ){
        Member member = new Member();
        member.setId();

    }











    @Data
    @AllArgsConstructor
    static class AddMember{
        private String name;

    }

    @Data
    static class CheckEffect{
        @NotBlank(message = "아이디입력필수")
        private String id;



    }


}
