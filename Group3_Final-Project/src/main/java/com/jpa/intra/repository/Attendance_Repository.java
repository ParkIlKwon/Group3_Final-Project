package com.jpa.intra.repository;

import com.jpa.intra.domain.Attendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class Attendance_Repository {

    private final EntityManager em;

    public void addAttendance(Attendance attend){
        em.persist(attend);
    }


    public void setOutTime(Attendance attendance) {
        em.createQuery("UPDATE Attendance a SET a.todayOutWorkTime =:outTime WHERE a.userId =: userId")
                .setParameter("outTime",attendance.getTodayOutWorkTime())
                .setParameter("userId",attendance.getUserId())
                .executeUpdate();


    }

    public List<Attendance>getListByUserId(String userId) {
        return em.createQuery("select a from Attendance a where a.userId = :id")
                .setParameter("id",userId)
                .getResultList();
    }

}
