package com.jpa.intra.domain.board;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("TASK")
@Getter
@Setter
public class BoardTask extends BoardCommon {
    @ManyToOne
    @JoinColumn(name="mem_num")
    private Member responsibleMemName;

    @ManyToOne
    @JoinColumn(name="team_num")
    private Team teamNum;
    private String progress;
}
