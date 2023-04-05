package com.jpa.intra.query;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberDTO {

    @NotEmpty(message = "필수입력항목입니다.")
    private String id;

    private String pw;
    private String name;


}
