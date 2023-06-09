package com.jpa.intra.domain;

import lombok.Data;

import javax.persistence.*;

@Data //기본적으로 게터 세터 생성자 생성등이 들어가 있음
@Entity //엔터티 생성 테이블에 매핑하는 JPA 엔터티로 클래스를 표시
public class Mail {

        @Id //PK 선언
        @GeneratedValue //생성전략정의
        @Column(name="mail_id") //이름 정해주기
        private Long id;

        private String title; //메일(쪽지) 제목 (필수)
//        @Lob
        @Column(columnDefinition = "TEXT")
        private String body; //메일(쪽지) 내용 (필수)
        private String content; //메일(쪽지)파일,업로드 (선택)

        private String sender; //보내는 사람 id (필수)
        private String sender_name; // 보내는 사람 이름
        private String sender_email; // 보내는 사람 이메일
        private String receiver; //받는대상 이메일(필수)

        private String sendDate; //보낸날짜

        private int view; //읽었는지 체크
}
