package com.jpa.intra.repository;

import com.jpa.intra.domain.board.BoardFree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class B_Board_Repository {
    private final EntityManager em;

    public void createBoardFree(BoardFree boardFree) {em.persist(boardFree);}
}
