package com.jpa.intra.query;

import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardTaskDTO {
    @NotEmpty(message="제목을 필수로 입력하다.")
    private String boardTitle;
    private String boardContent;
    private String createDate;
    private Member responsibleMemNum;
    private Team teamNum;
    private String progress;
}
