package com.jpa.intra.query;

public class MailDTO {

    private String title; //메일(쪽지) 제목 (필수)
    private String body; //메일(쪽지) 내용 (필수)
    private String content; //메일(쪽지)파일,업로드 (선택)

    private String sender; //보내는 사람 (필수)
    private String to; //받는대상 (필수)



}
