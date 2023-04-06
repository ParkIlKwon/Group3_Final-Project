package com.jpa.intra.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TASK")
@Getter
@Setter
public class BoardTask extends BoardCommon {
}
