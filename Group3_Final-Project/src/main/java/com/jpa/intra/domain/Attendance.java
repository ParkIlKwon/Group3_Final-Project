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
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Attendance_ID")
    private Long id; //출석 고유번호 1 2 3

    private String userId; //맴버 ID
 
    private String todayInWorkTime; //출근한 시간
    private String todayOutWorkTime; //퇴근한 시간
}
