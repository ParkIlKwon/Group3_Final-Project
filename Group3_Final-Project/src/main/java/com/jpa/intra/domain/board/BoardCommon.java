package com.jpa.intra.domain.board;

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
    private String boardWriter;

}
