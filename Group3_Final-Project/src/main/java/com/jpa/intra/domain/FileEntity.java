package com.jpa.intra.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "file")
@Getter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String originalName;
    private String savedName;
    private String  savedPath;

    @Builder
    public FileEntity(Long id, String orgNm, String savedNm, String savedPath) {
        this.id = id;
        this.originalName = orgNm;
        this.savedName = savedNm;
        this.savedPath = savedPath;
    }

}
