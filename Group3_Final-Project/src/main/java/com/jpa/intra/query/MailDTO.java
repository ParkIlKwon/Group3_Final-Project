package com.jpa.intra.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDTO {

    private String title; //메일(쪽지) 제목 (필수)
    private String body; //메일(쪽지) 내용 (필수)
    private String content; //메일(쪽지)파일,업로드 (선택)

    private String sender; //보내는 사람 (필수)
    private String receiver; //받는대상 (필수)



}
