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
    @ManyToOne
    @JoinColumn(name="mem_num")
    @Convert(converter= MemberConverter.class)
    private Member approverMemNum;


    private String approvalType;
    private String approvalStatus;

    private String dueDate;

    private String approvalInfo;
}
