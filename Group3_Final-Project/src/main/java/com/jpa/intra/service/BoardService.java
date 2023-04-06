package com.jpa.intra.service;

import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.repository.B_Board_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BoardService {
    private final B_Board_Repository bBoardRepository;

    @Transactional
    public Long createBoardFree(BoardFree boardFree) {
        bBoardRepository.createBoardFree(boardFree);
        return boardFree.getId();
    }
}
