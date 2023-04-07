package com.jpa.intra.service;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.repository.A_Member_Repository;
import com.jpa.intra.repository.B_Board_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public Long createBoardTask(BoardTask boardTask) {
        bBoardRepository.createBoardTask(boardTask);
        return boardTask.getId();
    }

    public List<BoardTask> findTasks() {return bBoardRepository.findAll();}
}
