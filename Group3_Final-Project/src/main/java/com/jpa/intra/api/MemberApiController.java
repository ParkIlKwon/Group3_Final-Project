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
import java.beans.ConstructorProperties;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberApiController {



    @PostMapping(value = "/join")
    public AddMember add(@RequestBody @Valid AddCheckEffect add){
        Member member = new Member();
        member.setMem_id(add.getId());
        System.out.println(member.getId());
       // member.setId();
        return null;
    }











    @Data
    @AllArgsConstructor
    static class AddMember{
        private String name;

    }

    @Data
    static class AddCheckEffect{
        @NotBlank(message = "아이디입력필수")
        private String id;
        @ConstructorProperties({"id"})
        public AddCheckEffect(String id){
            this.id = id;
        }


    }

}
