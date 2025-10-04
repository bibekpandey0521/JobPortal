package com.javacode.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerApply;
import com.javacode.jobportal.entity.JobSeekerProfile;

@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply,Integer> {
	List<JobSeekerApply> findByUserId(JobSeekerProfile  userId);
	
	List<JobSeekerApply> findByJob(JobPostActivity  userId);	
}

