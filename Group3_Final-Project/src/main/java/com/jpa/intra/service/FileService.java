package com.jpa.intra.service;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.File_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor //notNull이나 final 붙은거 자동생성자주입
@Service
@Transactional(readOnly = true)
public class FileService {

    //파일 저장경로 그냥 이렇게 함 . C로 해도 됨

    private final File_Repository fileRepository;

    @Transactional
    public Long uploadFile(MultipartFile files, HttpServletRequest request) throws IOException {
        String fileDir = "C:\\Storage\\";
        File fileObj = new File(fileDir);
        if (fileObj.isDirectory() == false) { //해당위치에 폴더없으면 생성
            System.out.println("폴더가 없습니다.");
            Path directoryPath = Paths.get(fileDir);
            Files.createDirectory(directoryPath);
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        //확장자를 뺀 파일 이름 추출 (ex : calendar )
        String currentFilename = origName.substring(0, origName.lastIndexOf('.'));

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        HttpSession session = request.getSession();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        String formattedDate = now.format(formatter);
        Member m = (Member) session.getAttribute("user");

        // 파일 엔티티 생성
        FileEntity file = FileEntity.builder()
                .userId((String) session.getAttribute("log"))
                .orgNm(currentFilename)
                .savedNm(savedName)
                .savedPath(savedPath)
                .date(formattedDate)
                .teamName(m.getTeam().getTeam_name())
                .fileType(extension)
                .build();
        System.out.println(savedPath);

        // 실제로 로컬에 uuid를 파일명으로 저장

        files.transferTo(new java.io.File(savedPath));

        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = fileRepository.save(file);

        return savedFile.getId();
    }

    @Transactional
    public Long saveProfileImage(File file, Member m) throws IOException {
        String fileDir = "C:\\profile\\";

        // 원래 파일 이름 추출
        String origName = file.getName();

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + origName;

        // 파일 엔티티 생성
        FileEntity fileE = FileEntity.builder()
                .userId(null)
                .orgNm(origName)
                .savedNm(origName)
                .savedPath(savedPath)
                .date("2021년 04월 11일 09시 28분")
                .teamName("")
                .fileType("jpg")
                .build();

        Path savedFilePath = Paths.get(savedPath);
        //calen.jpg -> c:/ profile / calen.jpg

            Files.copy(file.toPath(), savedFilePath);


        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = fileRepository.save(fileE);

        m.setMem_img(savedPath);

        return savedFile.getId();
    }




    @Transactional
    public Long uploadProfileImage(MultipartFile files, Member m) throws IOException {
        String fileDir = "C:\\profile\\";

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + origName;

        // 파일 엔티티 생성
        FileEntity fileE = FileEntity.builder()
                .userId(null)
                .orgNm(origName)
                .savedNm(origName)
                .savedPath(savedPath)
                .date("2021년 04월 11일 09시 28분")
                .teamName("")
                .fileType("jpg")
                .build();


        files.transferTo(new java.io.File(savedPath));


        // 데이터베이스에 파일 정보 저장
        FileEntity savedFile = fileRepository.save(fileE);

        m.setMem_img(savedPath);

        return savedFile.getId();
    }










    @Transactional
    public void deleteFile(String path) {
        File fileobj = new File(path); //파일 경로를 받아와서 파일 객체를 만들어줌.
        fileobj.delete(); //로컬저장경로에서 삭제
        fileRepository.deleteFile(path); //DB에서 삭제

    }

    public void fileDownload(Long id) throws IOException{
        List<FileEntity> flist = fileRepository.findFilelistByFileId(id);
        File uploadedFile = new File(flist.get(0).getSavedPath());

        String originalName = uploadedFile.getName();
        String CurrentPath = "C:\\download\\" + originalName;

        Path downloadPath = Paths.get(CurrentPath);
        if(downloadPath.toFile().exists()){
           return;
        }

        Files.copy(uploadedFile.toPath(),downloadPath);

    }
}
