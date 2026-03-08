package com.pr.service;

import java.util.List;
import java.util.Optional;

import com.pr.entity.LeaveRequest;

public interface LeaveRequestService {

	LeaveRequest save(LeaveRequest leaveRequest);

	List<LeaveRequest> findByEmployeeId(Long employeeId);

	Optional<LeaveRequest> findById(Long id);

	List<LeaveRequest> findAll();
}

