package com.jpa.intra;

import com.jpa.intra.domain.Member;
import com.jpa.intra.repository.Member_Repository;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
@Service
@Transactional
public class imginit {

    private final FileService fileService;
    private final Member_Repository repository;

    String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 위치를 수정하더라도 그에 따라 바뀜

    public void run(){

        //initFolder(new File("C:\\profile"));

        initFolder(new File("C:\\download"));

        initFolder(new File("C:\\Storage"));

        initFolder(new File("C:\\tempImg"));


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

        String url = "https://previews.123rf.com/images/aomarch/aomarch1509/aomarch150900162/45571489-%EC%82%AC%EB%9E%8C%EB%93%A4%EC%9D%B4-%ED%94%84%EB%A1%9C%ED%95%84-%EC%8B%A4%EB%A3%A8%EC%97%A3-%EB%B2%A1%ED%84%B0-%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8-%EB%A0%88%EC%9D%B4-%EC%85%98.jpg";
        String fileName =  "C:\\tempImg\\tempFile" + index + ".jpg";
        try (InputStream in = URI.create(url).toURL().openStream()){

            Files.copy(in, Paths.get(fileName));

            File file = new File(fileName);

            try {
                fileService.saveProfileImage(file,m);
            } catch (IOException e) {
                System.out.println("이미 있기 때문에 재생성");
                String fileDir = "C:\\profile\\";

                // 원래 파일 이름 추출
                String origName = "tempFile"+index+".jpg";

                // 파일을 불러올 때 사용할 파일 경로
                String savedPath = fileDir + origName;

                Member mem = new Member();
                String tid = index + "";
                mem.setId(Long.parseLong(tid));
                mem.setMem_img(savedPath);
                repository.updateImg(mem);

//                m.setMem_img(savedPath);

            }

        }catch (IOException e){
            System.out.println("이미 있기 때문에 재생성");
            String fileDir = "C:\\profile\\";

            // 원래 파일 이름 추출
            String origName = "tempFile"+index+".jpg";

            // 파일을 불러올 때 사용할 파일 경로
            String savedPath = fileDir + origName;

            Member mem = new Member();
            String tid = index + "";
            mem.setId(Long.parseLong(tid));
            mem.setMem_img(savedPath);
            repository.updateImg(mem);

            //m.setMem_img(savedPath);


        }






    }


}
