package com.pr.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pr.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	
	  Attendance findByEmployeeIdAndDate(Long employeeId, LocalDate date);
	  List<Attendance> findByEmployeeId(Long employeeId);

}
