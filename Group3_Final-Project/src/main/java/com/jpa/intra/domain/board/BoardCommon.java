package com.jpa.intra.domain.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpa.intra.domain.Member;
import com.jpa.intra.util.MemberConverter;
import com.jpa.intra.util.TeamConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="board_type")
@Getter
@Setter
public abstract class BoardCommon {

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;

    private String boardTitle;

    @Lob
    private String boardContent;
    private String createDate;
    private String updateDate;


    @ManyToOne
    @JoinColumn(name="mem_num")
    @Convert(converter = MemberConverter.class)
    private Member boardWriterObject;

    private String boardWriter;

}
