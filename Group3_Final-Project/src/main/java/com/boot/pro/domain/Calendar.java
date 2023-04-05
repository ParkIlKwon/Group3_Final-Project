package com.boot.pro.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable //임베디드 객체화
@Getter
public class Calendar {
    private String title; //일정 제목
    private String mem_id; //멤버아이디
    private String start_Date; //시작일
    private String end_Date; //끝일
    private String contents; //컨텐츠

    protected Calendar() {
    }

    public Calendar(String title, String mem_id, String start_Date, String end_Date, String contents) {
        this.title = title;
        this.mem_id = mem_id;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.contents = contents;
    }

}
