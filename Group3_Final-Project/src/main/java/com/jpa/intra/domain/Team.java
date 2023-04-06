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
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int team_num ;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="team_num")
    private Long id;

    private String team_name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();
}
