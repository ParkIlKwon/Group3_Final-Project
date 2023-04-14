package com.jpa.intra.service;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.repository.Member_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final Member_Repository aMemberRepository;
    @Transactional //DB에 적용시켜주는 어노테이션
    public void Join(Member member , Team team){

        member.setTeam(team);
        aMemberRepository.save(member);
                  //따로 리포지토리에서 persist를 해줘야 한다 .
        team.getMembers().add(member);
    }

    public Member Login(String id,String pw){
        Member m = aMemberRepository.login(id,pw); //리포지 >> 로그인 로직

        return m != null ? m : null ;
    }

    @Transactional
    public void Update(Member m){
        aMemberRepository.update(m); //리포지 >> 업데이트 로직
            }


}
