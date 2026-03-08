package com.pr.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pr.entity.Employee;
import com.pr.entity.LeaveRequest;
import com.pr.service.AttendanceService;
import com.pr.service.LeaveRequestService;
import com.pr.service.impl.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/leaves")
public class LeaveController {

	private final LeaveRequestService leaveRequestService;
	
	@Autowired
	private  EmployeeService employeeService;

	public LeaveController(LeaveRequestService leaveRequestService) {
		this.leaveRequestService = leaveRequestService;
		
	}

	@GetMapping("/apply/{employeeId}")
	public String showApplyForm(@PathVariable Long employeeId, Model model) {
		Employee employee = employeeService.findById(employeeId).orElse(null);
		if (employee == null) {
			return "redirect:/employees";
		}

		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setStartDate(LocalDate.now());
		leaveRequest.setEndDate(LocalDate.now());
		leaveRequest.setStatus("PENDING");

		model.addAttribute("employee", employee);
		model.addAttribute("leaveRequest", leaveRequest);
		return "leave-form";
	}

	@PostMapping("/apply")
	public String submitLeave(@ModelAttribute("leaveRequest") LeaveRequest leaveRequest, BindingResult bindingResult,
			@RequestParam("employeeId") Long employeeId, Model model) {
		if (bindingResult.hasErrors()) {
			Employee employee = employeeService.findById(employeeId).orElse(null);
			if (employee == null) {
				return "redirect:/employees";
			}
			model.addAttribute("employee", employee);
			return "leave-form";
		}

		Employee employee = employeeService.findById(employeeId).orElse(null);
		if (employee == null) {
			return "redirect:/employees";
		}

		leaveRequest.setEmployee(employee);
		if (leaveRequest.getStatus() == null) {
			leaveRequest.setStatus("PENDING");
		}
		leaveRequest.setAppliedOn(LocalDate.now());

		leaveRequestService.save(leaveRequest);
		return "redirect:/leaves/employee/" + employeeId;
	}

	@GetMapping("/employee/{employeeId}")
	public String viewEmployeeLeaves(@PathVariable Long employeeId, Model model) {
		Employee employee = employeeService.findById(employeeId).orElse(null);
		if (employee == null) {
			return "redirect:/employees";
		}
		List<LeaveRequest> leaves = leaveRequestService.findByEmployeeId(employeeId);

		model.addAttribute("employee", employee);
		model.addAttribute("leaves", leaves);
		return "employee-leaves";
	}

	@GetMapping("/edit/{leaveId}")
	public String editLeave(@PathVariable Long leaveId, Model model) {
		LeaveRequest leave = leaveRequestService.findById(leaveId).orElse(null);
		if (leave == null || leave.getEmployee() == null) {
			return "redirect:/employees";
		}
		if (!"PENDING".equalsIgnoreCase(leave.getStatus())) {
			return "redirect:/leaves/employee/" + leave.getEmployee().getId();
		}

		Employee employee = leave.getEmployee();
		model.addAttribute("employee", employee);
		model.addAttribute("leaveRequest", leave);
		return "leave-form";
	}

	@GetMapping("/cancel/{leaveId}")
	public String cancelLeave(@PathVariable Long leaveId) {
		LeaveRequest leave = leaveRequestService.findById(leaveId).orElse(null);
		if (leave == null || leave.getEmployee() == null) {
			return "redirect:/employees";
		}
		if ("PENDING".equalsIgnoreCase(leave.getStatus())) {
			leave.setStatus("CANCELLED");
			leaveRequestService.save(leave);
		}
		return "redirect:/leaves/employee/" + leave.getEmployee().getId();
	}

	@PostMapping("/review")
	public String reviewLeave(@RequestParam("leaveId") Long leaveId,
			@RequestParam(value = "managerComment", required = false) String managerComment,
			@RequestParam("action") String action) {
		LeaveRequest leave = leaveRequestService.findById(leaveId).orElse(null);
		if (leave == null || leave.getEmployee() == null) {
			return "redirect:/employees";
		}

		if ("approve".equalsIgnoreCase(action)) {
			leave.setStatus("APPROVED");
		} else if ("reject".equalsIgnoreCase(action)) {
			leave.setStatus("REJECTED");
		}

		if (managerComment != null && !managerComment.isBlank()) {
			leave.setManagerComment(managerComment);
		}

		leaveRequestService.save(leave);
		return "redirect:/leaves/employee/" + leave.getEmployee().getId();
	}

	@GetMapping("/calendar/{employeeId}")
	public String viewEmployeeLeaveCalendar(@PathVariable Long employeeId, Model model) {
		Employee employee = employeeService.findById(employeeId).orElse(null);
		if (employee == null) {
			return "redirect:/employees";
		}

		List<LeaveRequest> leaves = leaveRequestService.findByEmployeeId(employeeId);
		YearMonth currentMonth = YearMonth.now();
		LocalDate firstDay = currentMonth.atDay(1);
		LocalDate lastDay = currentMonth.atEndOfMonth();

		List<LocalDate> daysOfMonth = new ArrayList<>();
		for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
			daysOfMonth.add(date);
		}

		Map<LocalDate, List<LeaveRequest>> leavesByDate = new HashMap<>();
		for (LeaveRequest leave : leaves) {
			if (leave.getStartDate() == null || leave.getEndDate() == null) {
				continue;
			}
			LocalDate from = leave.getStartDate().isBefore(firstDay) ? firstDay : leave.getStartDate();
			LocalDate to = leave.getEndDate().isAfter(lastDay) ? lastDay : leave.getEndDate();

			for (LocalDate d = from; !d.isAfter(to); d = d.plusDays(1)) {
				leavesByDate.computeIfAbsent(d, k -> new ArrayList<>()).add(leave);
			}
		}

		model.addAttribute("employee", employee);
		model.addAttribute("yearMonth", currentMonth);
		model.addAttribute("daysOfMonth", daysOfMonth);
		model.addAttribute("leavesByDate", leavesByDate);

		return "employee-leaves-calendar";
	}
	
	  

	  
	
}

