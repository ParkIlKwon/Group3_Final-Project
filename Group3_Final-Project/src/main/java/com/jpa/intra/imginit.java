package com.jpa.intra;

import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class imginit {

    private final FileService fileService;
    private final Member_Repository repository;

    String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 위치를 수정하더라도 그에 따라 바뀜

    public void run(){

        String fileDir = "C:\\profile";
        File fileObj = new File(fileDir);

        if(fileObj.exists() == false) { //해당위치에 폴더없으면 생성
            System.out.println("폴더가 없습니다.");
                fileObj.mkdir();

        }else{ //있으면 지우고 재 생성 이걸 안 해주면 uuid로 쓰는경우 켤때마다
            //폴더가 있으면 그안에 이미지가 계속 쌓임 ...
            System.out.println("폴더가 있으므로 재생성합니다.");

            File[] deleteFolderList = fileObj.listFiles();

            for (int j = 0; j < deleteFolderList.length; j++) {
                deleteFolderList[j].delete();
            }
            // 폴더 내부에 파일 하나라도 있으면 폴더가 삭제 안 됨 .
            fileObj.delete();
            fileObj.mkdir();
        }


        List<Member> allMemberList = repository.getAllMemberList();
        for (int i = 0; i < allMemberList.size() ; i++){
            makeProfileImg(i,allMemberList.get(i));
            System.out.println(i);
        }
    }


    @Transactional
    public void makeProfileImg(int index,Member m){
        String currentPath = "\\src\\main\\resources\\image\\" + index + ".jpg"; //그뒤 나머지 경로
        String filePath = rootPath + currentPath;
        File file = new File(filePath);

            try {
                fileService.saveProfileImage(file,m);
            } catch (IOException e) {
                e.getStackTrace();
            }



    }
}
