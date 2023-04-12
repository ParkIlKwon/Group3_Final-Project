package com.jpa.intra.repository;

import com.jpa.intra.domain.Team;
import com.jpa.intra.domain.board.BoardApproval;
import com.jpa.intra.domain.board.BoardFree;
import com.jpa.intra.domain.board.BoardTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Board_Repository {
    private final EntityManager em;

    public void createBoardFree(BoardFree boardFree) {em.persist(boardFree);}
    public void createBoardTask(BoardTask boardTask) {em.persist(boardTask);}

    public List<BoardTask> findAllBoardTask() {return em.createQuery("SELECT t FROM BoardTask t", BoardTask.class).getResultList();}

    public List<BoardTask> findBoardTaskById(String id){
        return em.createQuery("select t from BoardTask t where t.boardWriter = :id")
                .setParameter("id",id)
                .getResultList();
    }

    public void deleteBoardTaskById(Long boardId) {
        BoardTask boardTask = em.getReference(BoardTask.class, boardId);
        em.remove(boardTask);
        em.flush();
    }

    public void changeTaskProgress(Long boardId, String boardProgress) {
        BoardTask boardTask = em.find(BoardTask.class, boardId);
        boardTask.setProgress(boardProgress);
        em.merge(boardTask);
        em.flush();
    }

    public void createBoardApproval1(BoardApproval boardApproval) {em.persist(boardApproval);}

    public void createBoardApproval2(BoardApproval boardApproval) {em.persist(boardApproval);}

    public void createBoardApproval3(BoardApproval boardApproval) {em.persist(boardApproval);}

    public List<BoardApproval> findAllBoardApproval1() {return em.createQuery("SELECT a FROM BoardApproval a", BoardApproval.class).getResultList();}

    // EntityManager의 내장 함수 find로 아이디 값을 참조하여 Team 객체를 뽑음
    public Team findById(Long id) {return em.find(Team.class, id);}


}
