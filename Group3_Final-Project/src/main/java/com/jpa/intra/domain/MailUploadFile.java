package com.jpa.intra.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

// SUMMERNOTE 이미지 파일 업로드
@Entity
@Data
public class MailUploadFile {
	
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
