package com.jpa.intra.service;

import com.jpa.intra.domain.Team;
import com.jpa.intra.repository.Team_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamService {

    private final Team_Repository tTeamRepository;

    public List<Team> searchByTeamName(String query) {
        return tTeamRepository.findByTeamNameContainingIgnoreCase(query);
    }
}
