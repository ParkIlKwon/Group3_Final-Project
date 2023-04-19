package com.jpa.intra.domain;

import com.jpa.intra.util.MemberConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ChatRoom {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="mem_num")
    @Convert(converter=MemberConverter.class)
    private Member creator;

    private String roomName;
    private String roomPwd;
    private int maxUserCnt;
    private String secretChk;
    private String createDateTime;
    private String destroyDateTime;
    private int curUserCnt;

}
