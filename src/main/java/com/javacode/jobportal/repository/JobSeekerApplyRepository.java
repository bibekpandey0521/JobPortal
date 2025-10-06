package com.javacode.jobportal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerApply;
import com.javacode.jobportal.entity.JobSeekerProfile;

public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);
    List<JobSeekerApply> findByJob(JobPostActivity job);

    // ✅ to prevent duplicate application
    boolean existsByUserIdAndJob(JobSeekerProfile userId, JobPostActivity job);
}
