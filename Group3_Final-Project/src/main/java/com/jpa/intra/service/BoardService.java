package com.jpa.intra.service;

import com.jpa.intra.domain.board.BoardApproval;
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

    public List<BoardTask> findTasks() {return bBoardRepository.findAllBoardTask();}
    @Transactional
    public void deleteBoardTaskById(Long boardId) {bBoardRepository.deleteBoardTaskById(boardId);}

    @Transactional
    public void changeTaskProgress(Long boardId, String boardProgress) {
        bBoardRepository.changeTaskProgress(boardId, boardProgress);
    }

    @Transactional
    public Long createBoardApproval1(BoardApproval boardApproval) {
        bBoardRepository.createBoardApproval1(boardApproval);
        return boardApproval.getId();
    }

    @Transactional
    public Long createBoardApproval2(BoardApproval boardApproval) {
        bBoardRepository.createBoardApproval2(boardApproval);
        return boardApproval.getId();
    }

    @Transactional
    public Long createBoardApproval3(BoardApproval boardApproval) {
        bBoardRepository.createBoardApproval3(boardApproval);
        return boardApproval.getId();
    }

    public List<BoardApproval> findApproval1() {return bBoardRepository.findAllBoardApproval1();}

}
