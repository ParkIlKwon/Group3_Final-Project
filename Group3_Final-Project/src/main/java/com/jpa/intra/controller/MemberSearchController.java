package com.jpa.intra.controller;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.service.MemberService;
import com.jpa.intra.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberSearchController {

    private final MemberService memberService;

    private final TeamService teamService;

    @GetMapping("/searchMember")
    public List<Member> searchMember(@RequestParam("query") String query) {
        return memberService.searchByName(query);
    }

    @GetMapping("/searchTeam")
    public List<Team> searchTeam(@RequestParam("query") String query) {
        return teamService.searchByTeamName(query);
    }

}
