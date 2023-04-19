package com.jpa.intra.service;

import com.jpa.intra.domain.Attendance;
import com.jpa.intra.repository.Attendance_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class AttendanceService {

    private final Attendance_Repository attendanceRepository;
    @Transactional
    public void addAttendance(Attendance attendance){
        attendanceRepository.addAttendance(attendance);
    }

    @Transactional
    public void setoutTime(Attendance attendance){
       attendanceRepository.setOutTime(attendance);
    }


    public List<Attendance>getAllAttendList(String userId){
       return attendanceRepository.getListByUserId(userId);
    }

}
