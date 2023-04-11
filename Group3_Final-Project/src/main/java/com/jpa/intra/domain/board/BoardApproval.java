package com.jpa.intra.domain.board;

import com.jpa.intra.domain.Member;
import com.jpa.intra.util.MemberConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("APPROVAL")
@Getter
@Setter
public class BoardApproval extends BoardCommon {
    //받아주는 사람 (맴버 테이블 참조)
    //결재타입 (휴가결잰지 업무시간변경결잰지 야근신청결잰지 등등)
    //결재상태 (승인 반려 대기)
    //결재시작일 (시작일과)
    //결재마감일 (마감일을 통해 언제까지 받아주는 사람이 이걸 승인 반려해줄지 기간관련정보)
    //결재상세정보 (결재 타입에 따라 다른 데이터를 넣을 수 있도록 JSON 타입으로 생성)


    @ManyToOne
    @JoinColumn(name="mem_num")
    @Convert(converter= MemberConverter.class)
    private Member approverMemNum;


    private String approvalType;
    private String approvalStatus;

    private String dueDate;

    private String approvalInfo;


}
