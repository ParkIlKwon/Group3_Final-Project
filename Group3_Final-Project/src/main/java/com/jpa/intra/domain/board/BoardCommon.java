package com.jpa.intra.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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
    private Date createDate;
    private Date updateDate;

}
