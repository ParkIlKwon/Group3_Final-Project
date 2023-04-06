package com.jpa.intra.query;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberDTO {

    @NotEmpty
    private String id; //사용자아이디

    @NotEmpty
    private String pw; //사용자 비번

    private String name; // 사용자 이름

    private String address_name; //사용자 주소
    
    private String address_road; //사용자 주소 (도로명)

}
