package com.pr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pr.entity.Attendance;
import com.pr.service.AttendanceService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AttendanceController {
	
	@Autowired
    private AttendanceService attendanceService;
	
	
	@GetMapping("/attendance")
	public String attendancePage(HttpSession session, Model model) {

	    Long employeeId = (Long) session.getAttribute("loginUserId");

	    List<Attendance> attendanceList = attendanceService.getAttendanceByEmployee(employeeId);

	    model.addAttribute("attendanceList", attendanceList);

	    return "attendance";
	}
	  
	  
	  @PostMapping("/checkin")
	  public String checkIn(HttpSession session) {

	      Long employeeId = (Long) session.getAttribute("loginUserId");

	      if(employeeId != null) {
	          attendanceService.checkIn(employeeId);
	      }

	      return "redirect:/attendance";
	  }

	  @PostMapping("/checkout")
	  public String checkOut(HttpSession session) {

	      Long employeeId = (Long) session.getAttribute("loginUserId");

	      if(employeeId != null) {
	          attendanceService.checkOut(employeeId);
	      }

	      return "redirect:/attendance";
	  }
	
	

}
