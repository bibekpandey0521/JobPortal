package com.javacode.jobportal.service;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerApply;
import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        // ✅ Ensure insert mode only
        jobSeekerApply.setId(null);

        // ✅ Prevent duplicate application
        boolean alreadyExists = jobSeekerApplyRepository.existsByUserIdAndJob(
            jobSeekerApply.getUserId(),
            jobSeekerApply.getJob()
        );

        if (alreadyExists) {
            throw new RuntimeException("User has already applied for this job.");
        }

        jobSeekerApplyRepository.save(jobSeekerApply);
    }
    
    
    public boolean existsByUserIdAndJob(JobSeekerProfile user, JobPostActivity job) {
        return jobSeekerApplyRepository.existsByUserIdAndJob(user, job);
    }
}
