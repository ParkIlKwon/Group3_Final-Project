package com.jpa.intra;

import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URL;
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

        initFolder(new File("C:\\profile"));

        initFolder(new File("C:\\download"));

        initFolder(new File("C:\\Storage"));


        List<Member> allMemberList = repository.getAllMemberList();
        for (int i = 0; i < allMemberList.size() ; i++){
            makeProfileImg(i,allMemberList.get(i));
            System.out.println(i);

            
        }
    }
    
    //폴더 지워주는 재생성해주는 로직
    //만든이유 : 계속 실행을 하다보면 폴더에 파일들이 쌓임 . ※ 폴더 내부에 파일을 모두 지워야 폴더삭제가능.
    //파일 객체를 받아와서 지워줌
    private void initFolder(File fileobj){

        try {
            File[] deleteFolderList = fileobj.listFiles();

            for (int j = 0; j < deleteFolderList.length; j++) {
                deleteFolderList[j].delete();
            } //폴더 내부에 파일이 존재하면 폴더 삭제불가 . 따라서 그걸 지워주는 로직


            fileobj.delete();
            fileobj.mkdir();

        }catch (NullPointerException e){ //폴더가 애초에 존재 하지 않을 시 Nullpoint 로 던져줌.
            fileobj.mkdir();
        }


    }
    

    @Transactional
    public void makeProfileImg(int index,Member m){
        String currentPath = "\\src\\main\\resources\\mem_img\\" + index + ".jpg"; //그뒤 나머지 경로

        Resource resource = null;
        String filePath = "";

        if(index == 1){

            try {
                Path testPath = Paths.get(File.separatorChar + "mem_img", File.separatorChar + "1.jpg");
                Path testPath2 = Paths.get(rootPath + currentPath);
                resource = new UrlResource("file:"+testPath2);
                System.out.println("resource = " + resource.getFile());
                filePath = resource.getFile().toString();
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("===================================================");
            System.out.println("===================================================");
        }

        System.out.println(filePath);
//        String filePath = resource.getFile();
        if(resource != null){
            File file = new File(filePath);
            try {
                fileService.saveProfileImage(file,m);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }



    }
    

}
