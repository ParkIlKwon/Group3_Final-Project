package com.jpa.intra.service;

import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.repository.Board_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BoardService {
    private final Board_Repository bBoardRepository;
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
    @Transactional
    public void deleteBoardTaskById(Long boardId) {bBoardRepository.deleteBoardTaskById(boardId);}

    @Transactional
    public void changeTaskProgress(Long boardId, String boardProgress) {
        bBoardRepository.changeTaskProgress(boardId, boardProgress);
    }

}
