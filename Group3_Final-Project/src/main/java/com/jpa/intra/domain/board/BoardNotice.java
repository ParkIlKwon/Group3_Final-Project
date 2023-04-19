package com.jpa.intra.domain.board;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.util.MemberConverter;
import com.jpa.intra.util.TeamConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
public class BoardNotice{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_num")
    private Long boardId;

    private String boardTitle;
    private String boardContent;
    private String createDate;
    private String boardWriter;

}