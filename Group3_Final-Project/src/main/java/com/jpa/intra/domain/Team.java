package com.jpa.intra.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int team_num ;

    private String team_name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
