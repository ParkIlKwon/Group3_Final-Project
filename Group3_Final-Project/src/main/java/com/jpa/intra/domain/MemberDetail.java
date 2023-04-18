package com.jpa.intra.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class MemberDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_num")
    private Long id; //멤버 고유번호

    private String member_id; //맴버

    private String todayInWorkTime;
    private String todayOutWorkTime;
}
