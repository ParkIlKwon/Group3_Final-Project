package com.jpa.intra.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //임베디드 객체화
@Getter
public class Calendar {
    private String title; //일정 제목
    private String login_id; //멤버아이디
    private String start_Date; //시작일
    private String end_Date; //끝일
    private String contents; //컨텐츠
    private String category; //구분(공지/할일/일정/연차/기념일)

    protected Calendar() {
    }

    public Calendar(String title, String mem_id, String start_Date, String end_Date, String contents, String category) {
        this.title = title;
        this.login_id = login_id;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.contents = contents;
        this.category = category;
    }

}
