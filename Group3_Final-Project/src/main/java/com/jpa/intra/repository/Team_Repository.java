package com.jpa.intra.repository;

import com.jpa.intra.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Team_Repository {
    private final EntityManager em;

    public List<Team> findByTeamNameContainingIgnoreCase(String query) {
        return em.createQuery("SELECT t FROM Team t WHERE lower(t.team_name) LIKE lower(concat('%', :query, '%'))", Team.class)
                .setParameter("query", query)
                .getResultList();
    }
}
