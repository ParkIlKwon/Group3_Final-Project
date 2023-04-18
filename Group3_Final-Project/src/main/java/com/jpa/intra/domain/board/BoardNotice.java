package com.jpa.intra.domain.board;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.util.MemberConverter;
import com.jpa.intra.util.TeamConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("NOTICE")
@Getter
@Setter
public class BoardNotice extends BoardCommon {
//    @ManyToOne
//    @JoinColumn(name="mem_num")
//    @Convert(converter = MemberConverter.class)
//    private Member responsibleMemNum;

//    @ManyToOne
//    @JoinColumn(name="team_num")
//    @Convert(converter = TeamConverter.class)
//    private Team teamNum;
//    private String progress;
//    private String startDate;
//    private String endDate;
}