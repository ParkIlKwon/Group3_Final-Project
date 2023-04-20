package com.jpa.intra.repository;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.MailUploadFile;
import com.jpa.intra.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Upload_Repository extends JpaRepository <UploadFile,Long>{

}
