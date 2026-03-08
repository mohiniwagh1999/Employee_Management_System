package com.pr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pr.entity.Employee;
import com.pr.service.impl.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private  EmployeeService employeeService;

	@Value("${employee.profile.images-dir}")
	private String imagesDir;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeService.findAll());
		return "employees-list";
	}

	@GetMapping("/new")
	public String showCreateForm(Model model) {
		Employee employee = new Employee();
		employee.setHireDate(LocalDate.now());
		model.addAttribute("employee", employee);
		return "employee-form";
	}

	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		Employee employee = employeeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + id));
		model.addAttribute("employee", employee);
		return "employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, BindingResult bindingResult,
			@RequestParam("profileImageFile") MultipartFile profileImageFile) {
		if (bindingResult.hasErrors()) {
			return "employee-form";
		}

		// Handle profile image upload if a new file is provided
		if (profileImageFile != null && !profileImageFile.isEmpty()) {
			try {
				Path uploadDir = Paths.get(imagesDir).toAbsolutePath().normalize();
				Files.createDirectories(uploadDir);

				String originalFilename = profileImageFile.getOriginalFilename();
				String extension = "";
				if (originalFilename != null && originalFilename.contains(".")) {
					extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
				}
				String filename = UUID.randomUUID().toString() + extension;

				Path filePath = uploadDir.resolve(filename);
				Files.copy(profileImageFile.getInputStream(), filePath);

				employee.setProfileImagePath("/profile-images/" + filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		employeeService.save(employee);
		return "redirect:/employees";
	}

	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeService.deleteById(id);
		return "redirect:/employees";
	}

	@GetMapping("/profile/{id}")
	public String viewProfile(@PathVariable Long id, Model model) {
		Employee employee = employeeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + id));
		model.addAttribute("employee", employee);
		return "employee-profile";
	}

	@GetMapping("/home")
	public String home() {
		return "redirect:/employees";
	}
}

