package com.jpa.intra.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("APPROVAL")
@Getter
@Setter
public class BoardApproval extends BoardCommon {
}
