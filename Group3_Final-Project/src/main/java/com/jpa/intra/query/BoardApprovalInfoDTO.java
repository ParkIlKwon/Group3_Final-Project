package com.jpa.intra.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardApprovalInfoDTO {
    private String startDate;
    private String endDate;
    private int deduction;
}
