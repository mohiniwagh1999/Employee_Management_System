package com.pr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pr.entity.Employee;
import com.pr.service.impl.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private   EmployeeService employeeService;
	
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/home")
	public String getHome(Authentication authentication, HttpSession session)
	{
	    // Logged-in username (email)
	    String email = authentication.getName();

	    // Get employee from database
	    Employee emp = employeeService.findByEmail(email);

	    // Store data in session
	    session.setAttribute("loginUser", emp.getFirstName());
	    session.setAttribute("loginUserId", emp.getId());

	    return "home";
	}

}
