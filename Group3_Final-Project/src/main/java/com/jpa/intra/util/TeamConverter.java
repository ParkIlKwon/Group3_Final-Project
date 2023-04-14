package com.jpa.intra.util;

import com.jpa.intra.domain.Team;
import com.jpa.intra.repository.Board_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TeamConverter implements Converter<String, Team>  {

    @Autowired
    private Board_Repository bBoardRepository;

    // Team타입의 객체를 String으로 받아오기 때문에 다시 Team타입으로 변환해주다.
    @Override
    @Transactional
    public Team convert(String source) {
        Long teamNum=Long.parseLong(source);
        System.out.println("팀 컨버터 태스트 : "+bBoardRepository.findById(teamNum));
        return bBoardRepository.findById(teamNum);
    }
}
