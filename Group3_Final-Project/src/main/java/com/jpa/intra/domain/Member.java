package com.jpa.intra.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_num")
    private Long id; //멤버 고유번호
    private String mem_id; //멤버 아이디
    private String mem_pw; //멤버 패스워드
    private String mem_name; //멤버 이름
    private String mem_img; //멤버 이미지
    private String gender; //멤버 성별
    private String birthday; //멤버 생일
    LocalDate reg_date;

    private String email; //멤버 이메일
    private String gitLink;

    private String inline_tel; // 사내 전화번호
    private String outline_tel; //멤버 전화번호

    private int vacation; //멤버 연차
    private int salary; //멤버 연봉
    private String emp_type; //직급 .
    private String inWorkTime;
    private String outWorkTime;
    private String vacationStart;
    private String vacationEnd;

    private String status; //현재 상태 (출퇴)
    //offline (퇴근) , online (출근) , holiday(휴가)


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_num")
    @JsonIgnore //stackoverflow 방지
    private Team team; //팀

    @Embedded //달력 객체 사용 .
    private Calendar cal; //달력정보

    @Embedded
    private Address address; //주소정보 (실주소명 - 도로명)

}
