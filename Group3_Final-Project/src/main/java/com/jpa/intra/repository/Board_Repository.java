package com.jpa.intra.repository;

import com.jpa.intra.domain.Team;
import com.jpa.intra.domain.board.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Board_Repository {
    private final EntityManager em;

    public void createBoardFree(BoardFree boardFree) {em.persist(boardFree);}
    public void createBoardTask(BoardTask boardTask) {em.persist(boardTask);}
    public void createBoardNotice(BoardNotice boardNotice) {em.persist(boardNotice);}

    public List<BoardTask> findBoardTaskById(String id){
        return em.createQuery("select t from BoardTask t where t.boardWriter = :id")
                .setParameter("id",id)
                .getResultList();
    }
    public List<BoardTask> findAllBoardTask() {return em.createQuery("SELECT t FROM BoardTask t", BoardTask.class).getResultList();}

    public BoardTask findTaskByBoardId(Long boardId) {
        return em.createQuery("SELECT t FROM BoardTask t WHERE t.id = :boardId", BoardTask.class)
                .setParameter("boardId", boardId).getSingleResult();
    }


    public void deleteBoardTaskById(Long boardId) {
        BoardTask boardTask = em.getReference(BoardTask.class, boardId);
        em.remove(boardTask); // 목록에서 얘만 지워줌 . commit 만 되어 있는 상태
        em.flush(); //적용
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

    public List<BoardApproval> findAllBoardApproval() {return em.createQuery("SELECT a FROM BoardApproval a", BoardApproval.class).getResultList();}

    // EntityManager의 내장 함수 find로 아이디 값을 참조하여 Team 객체를 뽑음
    public Team findById(Long id) {return em.find(Team.class, id);}

    // EntityManager의 내장 함수 find로 아이디 값을 참조하여 BoardCommon 객체를 뽑음
    public BoardCommon findByBoardId(Long id) {return em.find(BoardCommon.class, id);}

    public BoardCommon findByBoardUserIdAndTitle(String uid,String title) {
        TypedQuery<BoardCommon> query = em.createQuery( //멤버 아이디와 패스워드 동시에 일치 하면 멤버 받아오는 로직
                "SELECT b FROM BoardCommon b WHERE b.boardWriter = :id AND b.boardTitle = :title", BoardCommon.class);
        query.setParameter("id", uid);
        query.setParameter("title", title);
        try {
            return query.getSingleResult(); //성공시 하나의 일정(boardCommon)객체 받아옴
        } catch (NoResultException | NonUniqueResultException e) {
            return null; //받아온 값이 없을 때 Null을 반환
        }
    }

    public void update(BoardTask t) { //업데이트 문
        //제목 , 내용 , 일자만 바꿀것이므로 set에 수정할 쿼리문 작성 
        //UPDATE 테이블이름 객체이름 SET 수정할 내용 WHERE 해당되는 쿼리문 조건 (PK사용) 식으로 작성
        //. setParameter 로 각각의 파라미터를 넘겨줌. 최종적으로 executeUpdate 로 업데이트 명시
        em.createQuery("UPDATE BoardTask t SET t.boardTitle =:title" +
                ",t.boardContent =:content ,t.startDate =:startDate,t.endDate=:endDate WHERE t.id = :boardID")
                .setParameter("title",t.getBoardTitle())
                .setParameter("content",t.getBoardContent())
                .setParameter("startDate",t.getStartDate())
                .setParameter("endDate",t.getEndDate())
                .setParameter("boardID",t.getId())
                .executeUpdate();
    }

    public void deleteBoardApprovalById(Long boardId) {
        BoardApproval boardApproval = em.getReference(BoardApproval.class, boardId);
        em.remove(boardApproval); // 목록에서 얘만 지워줌 . commit 만 되어 있는 상태
        em.flush(); //적용
    }

    public List<BoardNotice> findAllNotice() {
        return em.createQuery("SELECT t FROM BoardNotice t order by t.id desc ", BoardNotice.class).getResultList();
    }





}
