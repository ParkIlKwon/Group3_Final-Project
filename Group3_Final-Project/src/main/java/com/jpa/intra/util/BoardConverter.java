package com.jpa.intra.util;

import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.repository.Board_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter implements Converter<String, BoardCommon> {

    @Autowired
    private Board_Repository bBoardRepository;

    // BoardCommon타입의 객체를 String으로 받아오기 때문에 다시 BoardCommon타입으로 변환해주다.
    @Override
    public BoardCommon convert(String source) {
        Long boardId=Long.parseLong(source);
        System.out.println("보드 컨버터 테스트 : "+bBoardRepository.findById(boardId));
        return bBoardRepository.findByBoardId(boardId);
    }
}
