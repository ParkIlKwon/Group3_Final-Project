package com.jpa.intra.query;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    // 기본 생성자, 파라미터가 있는 생성자, getter, setter 생략

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}