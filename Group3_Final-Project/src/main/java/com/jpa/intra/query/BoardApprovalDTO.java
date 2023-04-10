package com.jpa.intra.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.intra.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardApprovalDTO {
    private String boardContent;
    private Member approverMemNum;
    private ObjectMapper approvalInfo;
}
