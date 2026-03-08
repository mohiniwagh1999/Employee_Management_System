package com.pr.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pr.entity.Attendance;
import com.pr.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	  @Autowired
	    private AttendanceRepository attendanceRepository;

	    public void checkIn(Long employeeId) {

	        LocalDate today = LocalDate.now();

	        Attendance attendance = attendanceRepository
	                .findByEmployeeIdAndDate(employeeId, today);

	        if(attendance == null) {

	            attendance = new Attendance();
	            attendance.setEmployeeId(employeeId);
	            attendance.setDate(today);
	            attendance.setCheckIn(LocalTime.now());
	            attendance.setStatus("Present");

	            attendanceRepository.save(attendance);
	        }
	    }

	    public void checkOut(Long employeeId) {

	        LocalDate today = LocalDate.now();

	        Attendance attendance = attendanceRepository
	                .findByEmployeeIdAndDate(employeeId, today);

	        if(attendance != null) {

	            attendance.setCheckOut(LocalTime.now());

	            attendanceRepository.save(attendance);
	        }
	    }
	
	    public List<Attendance> getAttendanceByEmployee(Long employeeId){
	        return attendanceRepository.findByEmployeeId(employeeId);
	    }

}
