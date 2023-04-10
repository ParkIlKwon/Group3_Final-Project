package com.jpa.intra.repository;

import com.jpa.intra.domain.FileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor //생성자 주입 final이 붙거나 @Not Null 이 붙은 필드의 생성자를
                        //자동 생성해주는 어노테이션이다.
public class File_Repository {
    private final EntityManager em;

    public FileEntity save(FileEntity file){ //파일정보 저장
        em.persist(file); //1차 캐시에 저장. 아직 DB에 안 보냄
        //최종적으로 DB에 적재를 해주기 위해선 @Transactional 으로 다른 클래스에서 해줌
        return file;
    }
}
