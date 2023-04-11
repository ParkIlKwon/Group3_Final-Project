package com.jpa.intra.service;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.repository.File_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;



@RequiredArgsConstructor //notNull이나 final 붙은거 자동생성자주입
@Service
@Transactional(readOnly = true)
public class FileService {

    private String fileDir = "E:\\test\\"; //파일 저장경로 그냥 이렇게 함 . C로 해도 됨


    private final File_Repository fileRepository;

    @Transactional
    public Long saveFile(MultipartFile files, HttpServletRequest request) throws IOException {

        File fileObj = new File(fileDir);
        if (files.isEmpty()) {
            return null;
        } else if (fileObj.isDirectory() == false) { //해당위치에 폴더없으면 생성
            System.out.println("없습니다.");
            Path directoryPath = Paths.get(fileDir);
            Files.createDirectory(directoryPath);
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        HttpSession session = request.getSession();

        // 파일 엔티티 생성
        FileEntity file = FileEntity.builder()
                .userId(session.getId())
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();
        System.out.println(savedPath);

        // 실제로 로컬에 uuid를 파일명으로 저장

        files.transferTo(new java.io.File(savedPath));

        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = fileRepository.save(file);

        return savedFile.getId();
       // return null;
    }

}
