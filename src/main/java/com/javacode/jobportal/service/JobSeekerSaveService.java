package com.javacode.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.entity.JobSeekerSave;
import com.javacode.jobportal.repository.JobSeekerSaveRepository;

@Service
public class JobSeekerSaveService {
	private final JobSeekerSaveRepository jobSeekerSaveRepository;

	public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
		this.jobSeekerSaveRepository = jobSeekerSaveRepository;
	}
	
	public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
		return jobSeekerSaveRepository.findByUserId(userAccountId);
	}
	
	public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
		return jobSeekerSaveRepository.findByJob(job);
	}
}
