package com.jpa.intra.domain.board;

public enum ApprovalType {
//    VACATION,OVERTIME,WORK_HOUR_CHANGE
    VACATION("휴가신청"), OVERTIME("연장근무신청"), WORK_HOUR_CHANGE("근무시간변경");

    private final String value;

    private ApprovalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
