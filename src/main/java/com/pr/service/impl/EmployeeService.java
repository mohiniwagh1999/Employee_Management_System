package com.pr.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pr.entity.Employee;
import com.pr.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeService {
	 @Autowired
	   private  EmployeeRepository employeeRepository;

	
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void deleteById(Long id) {
		employeeRepository.deleteById(id);
	}

	
	    public Employee findByEmail(String email) {
	        return employeeRepository.findByEmail(email);
	    }
}

