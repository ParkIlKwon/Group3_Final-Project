package com.jpa.intra.service;

import com.jpa.intra.domain.Reply;
import com.jpa.intra.domain.board.BoardApproval;
import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardNotice;
import com.jpa.intra.domain.board.BoardTask;
import com.jpa.intra.repository.Board_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<BoardTask> findTasks(String id){ //메서드 오버 로딩
        return bBoardRepository.findBoardTaskById(id);
    }

    @Transactional
    public Long createBoardTask(BoardTask boardTask) {
        bBoardRepository.createBoardTask(boardTask);
        return boardTask.getId();
    }

    public List<BoardTask> findTasks() {return bBoardRepository.findAllBoardTask();}

    public BoardTask findTaskByBoardId(Long boardId) {return bBoardRepository.findTaskByBoardId(boardId);}
    @Transactional
    public void deleteBoardTaskById(Long boardId) {bBoardRepository.deleteBoardTaskById(boardId);}

    @Transactional
    public void changeTaskProgress(Long boardId, String boardProgress) {bBoardRepository.changeTaskProgress(boardId, boardProgress);}

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

    public List<BoardApproval> findApproval() {return bBoardRepository.findAllBoardApproval();}


    @Transactional
    public void deleteBoardApprovalById(Long boardId) {bBoardRepository.deleteBoardApprovalById(boardId);}

    @Transactional
    public void changeApprovalStatus(Long boardId, String approvalStatus) {bBoardRepository.changeApprovalStatus(boardId, approvalStatus);}

    @Transactional
    public void expireApprovals() {
        System.out.println("서비스 문제없이 실행되다");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String formattedNow = now.format(formatter);

        List<BoardApproval> approvals = bBoardRepository.findByApprovalStatusAndDueDateBefore("REQUESTED", formattedNow);
        for (BoardApproval approval : approvals) {
            approval.setApprovalStatus("EXPIRED");
        }
    }

    public List<BoardApproval> findMyApprovalList(List<BoardApproval> boardApprovalList, String memberId) {
        List<BoardApproval> myApprovalList = new ArrayList<>();
        for (BoardApproval boardApproval : boardApprovalList) {
            if (boardApproval.getBoardWriter().equals(memberId)) {
                myApprovalList.add(boardApproval);
            }
        }
        return myApprovalList;
    }

    public List<BoardNotice> getNoticeList() {
        return bBoardRepository.findAllNotice();
    }

    @Transactional
    public Long createBoardNotice(BoardNotice boardNotice) {
        bBoardRepository.createBoardNotice(boardNotice);
        return boardNotice.getId();
    }



}
