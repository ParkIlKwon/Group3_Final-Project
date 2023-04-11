package com.jpa.intra;

import com.jpa.intra.domain.Address;
import com.jpa.intra.domain.Member;
import com.jpa.intra.domain.Team;
import com.jpa.intra.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Component //자동으로 객체를 생성해줌
@RequiredArgsConstructor //final 붙은 값을 넣어줌
public class init {

    private final InitService initService;
    private final FileService fileService;


//생성자가 만들어지면 바로 호출하는 메서드
    @PostConstruct
    public void init() {
        Random ran = new Random();

        for (int i = 0;i < 5 ; i++){
            int num = ran.nextInt(6);
            initService.makeMemberDummy(num);
        }
        //initService.dbInit1();



    }

    @Component
    @Transactional //db에 값을 넣어주는 어노테이션.
    @RequiredArgsConstructor //생성자 의존성 주입 생략
    class InitService {
        int index = 0;

        private final EntityManager em;

        public void makeMemberDummy(@Lazy int num){


            String TeamArr [] = {"영업부","인사부","기획부",
            "관리부","회계부","총무부"};
            Team t=new Team();
            t.setTeam_name(TeamArr[index]);
            em.persist(t);

            Member m = new Member();
            String userID = "test" + index;
            m.setMem_id(userID);
            m.setMem_pw("321");
            m.setMem_name(userID);

            String RandomAddress [] = {"경기도 시흥시"
            ,"서울시","강릉시","강원도","김포시","안산시"};

            String RandomRoadName [] = {"정왕대로 54번길",
           "언주로87길","강릉대로203번길","강원9번길",
            "김포7번길","안산62번길"};

            Address address = new Address(
            RandomAddress[num],RandomRoadName[num]);

            m.setAddress(address);

            em.persist(m);

            t.getMembers().add(m);
            m.setTeam(t);

            String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로 위치를 수정하더라도 그에 따라 바뀜
            String currentPath = "\\src\\main\\resources\\image\\" + index + ".jpg"; //그뒤 나머지 경로

            String filePath = rootPath + currentPath;
            File file = new File(filePath);

//            if (file.exists()){
//                System.out.println("======프로필 사진이 정상적으로 존재합니다.");
//                try{
//                    fileService.saveProfileImage(file,m.getMem_id());
//                }catch (IOException e){
//                    e.getStackTrace();
//                }
//
//            }
            
            index++;
        }



        public void dbInit1() {
            // add Team DataSample
            Team t=new Team();
            t.setTeam_name("영업부");
            em.persist(t);

            // add Member DataSample
            Member m = new Member();
            m.setMem_id("test1");
            m.setMem_pw("321");
            m.setMem_name("shoichi");
            em.persist(m);

            t.getMembers().add(m);
            m.setTeam(t);
        }











    }
}

