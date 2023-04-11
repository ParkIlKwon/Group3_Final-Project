package com.jpa.intra.query;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//public class BoardApprovalInfoDTO {
//    private String startDate;
//    private String endDate;
//    private int deduction;
//}
@Getter
@Setter
public class BoardApprovalInfoDTO {
    //휴우가 네지
    private String startDate;
    private String endDate;
    private int deduction;

    //연장근무
    private String endTime;
    private int bonusAllowance;

    //출퇴근시간변경
    private String goToOffice;
    private String leaveOffice;
}