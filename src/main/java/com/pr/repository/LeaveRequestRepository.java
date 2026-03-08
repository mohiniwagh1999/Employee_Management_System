package com.pr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pr.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findByEmployeeId(Long employeeId);
}

