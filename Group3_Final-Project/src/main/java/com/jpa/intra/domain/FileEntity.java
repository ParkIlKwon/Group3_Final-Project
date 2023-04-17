package com.jpa.intra.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    private String userId;
    private String originalName;
    private String savedName;
    private String  savedPath;
    private String date;
    private String teamName;
    private String fileType;

    @Builder
    public FileEntity(String userId ,String orgNm, String savedNm, String savedPath,String date,String teamName,String fileType) {
        this.userId = userId;
        this.originalName = orgNm;
        this.savedName = savedNm;
        this.savedPath = savedPath;
        this.date = date;
        this.teamName = teamName;
        this.fileType = fileType;
    }

}
