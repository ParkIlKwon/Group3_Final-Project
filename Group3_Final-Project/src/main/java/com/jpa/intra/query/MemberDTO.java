package com.jpa.intra.query;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.Date;

@Data
public class MemberDTO {

    @NotEmpty
    private String id; //사용자아이디

    @NotEmpty
    private String pw; //사용자 비번
    
    private String pkid; // 사용자 고유번호

    private String name; // 사용자 이름

    private String address_name; //사용자 주소
    
    private String address_road; //사용자 주소 (도로명)

    private MultipartFile profileFile;

    private String birthday;

    private String gender;

    private String memail;
}
