package com.jpa.intra.query;

import lombok.Data;

@Data
public class LoginDTO { //별도로 만든 로그인 때 쓸 DTO
        //id , pw 만 필요해서 따로 만든거
    private String id ;

    private String pw;
        //NotNull 안 넣고 script에서 처리.
}
