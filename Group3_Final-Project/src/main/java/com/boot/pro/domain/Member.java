package com.boot.pro.domain;


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
    private Long id;
    private String mem_id;
    private String mem_pw;
    private String mem_name;
    private String mem_img;
    private String gender;
    LocalDate birthday;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(insertable = false)
    private Team team;


}
