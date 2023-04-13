package com.jpa.intra.repository;

import com.jpa.intra.domain.FileEntity;
import com.jpa.intra.domain.board.BoardTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<FileEntity> findFilelistById(String id){
        return em.createQuery("select f from FileEntity f where f.userId = :id")
                .setParameter("id",id)
                .getResultList();
    }

    public void deleteFile(String path){
        em.createQuery("delete from FileEntity f where f.savedPath =:path")
                .setParameter("path",path)
                .executeUpdate();
        em.clear(); //필수 .
    }

//    @Override
//    public void delete(Long articleId) {
//        em.createQuery("delete from Article a where a.id = :id")
//                .setParameter("id", articleId)
//                .executeUpdate();
//        em.clear();
//    }


//
//    public List<BoardTask> findBoardTaskById(String id){
//        return em.createQuery("select t from BoardTask t where t.boardWriter = :id")
//                .setParameter("id",id)
//                .getResultList();
//    }


}
