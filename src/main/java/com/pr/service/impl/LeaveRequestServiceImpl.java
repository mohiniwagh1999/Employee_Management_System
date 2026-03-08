package com.pr.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pr.entity.LeaveRequest;
import com.pr.repository.LeaveRequestRepository;
import com.pr.service.LeaveRequestService;

@Service
@Transactional
public class LeaveRequestServiceImpl implements LeaveRequestService {

	private final LeaveRequestRepository leaveRequestRepository;

	public LeaveRequestServiceImpl(LeaveRequestRepository leaveRequestRepository) {
		this.leaveRequestRepository = leaveRequestRepository;
	}

	@Override
	public LeaveRequest save(LeaveRequest leaveRequest) {
		return leaveRequestRepository.save(leaveRequest);
	}

	@Override
	public List<LeaveRequest> findByEmployeeId(Long employeeId) {
		return leaveRequestRepository.findByEmployeeId(employeeId);
	}

	@Override
	public Optional<LeaveRequest> findById(Long id) {
		return leaveRequestRepository.findById(id);
	}

	@Override
	public List<LeaveRequest> findAll() {
		return leaveRequestRepository.findAll();
	}
}

