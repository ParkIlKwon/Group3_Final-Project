package com.jpa.intra.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FREE")
@Getter
@Setter
public class BoardFree extends BoardCommon {
    private int viewCount;
}
