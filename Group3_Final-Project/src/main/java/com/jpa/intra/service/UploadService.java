package com.jpa.intra.service;


import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.MailUploadFile;
import com.jpa.intra.domain.UploadFile;
import com.jpa.intra.repository.Upload_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UploadService {

    @Autowired
    Upload_Repository uploadRepository;

    private final Path uploadroot;


    public UploadService(String pro){
        this.uploadroot = Paths.get(pro);
        System.out.println(uploadroot.toString());
    }

    public UploadFile store(MultipartFile file) throws Exception {
        try {
            if(file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }

            String saveFileName = fileSave(uploadroot.toString(), file);
            UploadFile saveFile = new UploadFile();
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setSaveFileName(saveFileName);
            saveFile.setContentType(file.getContentType());
            saveFile.setSize(file.getResource().contentLength());
            saveFile.setRegisterDate(LocalDateTime.now());
            saveFile.setFilePath(uploadroot.toString().replace(File.separatorChar, '/') +'/' + saveFileName);
            uploadRepository.save(saveFile);
            return saveFile;

        }catch(IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }

    }


    public UploadFile load(Long fileId) {
        return uploadRepository.findById(fileId).get();
    }


    public String fileSave(String rootLocation, MultipartFile file) throws IOException {
        File uploadDir = new File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid.toString() + file.getOriginalFilename();
        File saveFile = new File(rootLocation, saveFileName);
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }







}
