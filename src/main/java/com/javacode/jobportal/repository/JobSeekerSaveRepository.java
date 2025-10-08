package com.javacode.jobportal.repository;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    // Find a saved job by user and job (useful for checking duplicates or deleting)
    Optional<JobSeekerSave> findByUserIdAndJob(JobSeekerProfile userId, JobPostActivity job);

    // Find all saved jobs of a specific user
    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    // Find all users who saved a specific job
    List<JobSeekerSave> findByJob(JobPostActivity job);
}
