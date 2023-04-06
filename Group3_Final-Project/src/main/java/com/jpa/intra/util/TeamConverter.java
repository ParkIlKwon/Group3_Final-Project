package com.jpa.intra.util;

import com.jpa.intra.domain.Team;
import com.jpa.intra.repository.B_Board_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter implements Converter<String, Team>  {

    @Autowired
    private B_Board_Repository bBoardRepository;

    @Override
    public Team convert(String source) {
        Long teamNum=Long.parseLong(source);
        System.out.println("팀 컨버터 태스트 : "+bBoardRepository.findById(teamNum));
        return bBoardRepository.findById(teamNum);
    }
}
