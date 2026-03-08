package com.pr.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="attendance")
public class Attendance {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long employeeId;

	    private LocalDate date;

	    private LocalTime checkIn;

	    private LocalTime checkOut;

	    private String status;

		public Attendance() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Attendance(Long id, Long employeeId, LocalDate date, LocalTime checkIn, LocalTime checkOut,
				String status) {
			super();
			this.id = id;
			this.employeeId = employeeId;
			this.date = date;
			this.checkIn = checkIn;
			this.checkOut = checkOut;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public LocalTime getCheckIn() {
			return checkIn;
		}

		public void setCheckIn(LocalTime checkIn) {
			this.checkIn = checkIn;
		}

		public LocalTime getCheckOut() {
			return checkOut;
		}

		public void setCheckOut(LocalTime checkOut) {
			this.checkOut = checkOut;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "Attendance [id=" + id + ", employeeId=" + employeeId + ", status=" + status + "]";
		}
	    
	    
	    
	
	
	

}
