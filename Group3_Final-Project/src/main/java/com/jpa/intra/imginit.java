package com.jpa.intra;

import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class imginit {

    private final FileService fileService;
    private final Member_Repository repository;

    String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 위치를 수정하더라도 그에 따라 바뀜


    public void run(){
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

        if (file.exists()) {
            System.out.println("======프로필 사진이 정상적으로 존재합니다.");

            try {
                fileService.saveProfileImage(file, m.getMem_id());
            } catch (IOException e) {
                e.getStackTrace();
            }
        }


    }
}
