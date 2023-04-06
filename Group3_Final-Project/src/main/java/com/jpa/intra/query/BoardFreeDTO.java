package com.jpa.intra.query;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
public class BoardFreeDTO {
    @NotEmpty(message="제목을 필수로 입력하다.")
    private String boardTitle;
    private String boardContent;
    private Date createDate;
}
