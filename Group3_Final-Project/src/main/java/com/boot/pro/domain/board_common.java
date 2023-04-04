package com.boot.pro.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class board_common {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;
    private String board_title;

    @Lob
    private String board_content;

    LocalDate create_date;
    LocalDate update_date;

    //file 모르겠음 ./

}
