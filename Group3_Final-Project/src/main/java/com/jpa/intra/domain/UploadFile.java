package com.jpa.intra.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class UploadFile {

    @Id @GeneratedValue
    private Long id;

    @Column
    private String fileName;

    @Column
    private String saveFileName;

    @Column
    private String filePath;

    @Column
    private String contentType;

    private long size;

    private LocalDateTime registerDate;



}
