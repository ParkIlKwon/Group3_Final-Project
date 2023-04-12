package com.jpa.intra.domain;

import com.jpa.intra.domain.board.BoardCommon;
import com.jpa.intra.util.MemberConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue
    @Column(name="reply_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="board_id")
    @Convert(converter= MemberConverter.class)
    private BoardCommon currentBoard;

    private String replyWriter;
    private String replyContent;
    private String createDate;
    private String updateDate;
}
