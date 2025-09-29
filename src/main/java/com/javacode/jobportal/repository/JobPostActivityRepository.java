package com.javacode.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javacode.jobportal.entity.JobPostActivity;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity,Integer> {
	
}
