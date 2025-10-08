package com.javacode.jobportal.service;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.entity.JobSeekerSave;
import com.javacode.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    @Autowired
    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    // Existing save method
//    public void addNew(JobSeekerSave jobSeekerSave) {
//        jobSeekerSaveRepository.save(jobSeekerSave);
//    }

    // ✅ Overloaded addNew method (this is what your controller calls)
    public void addNew(JobSeekerProfile seekerProfile, JobPostActivity jobPostActivity) {
        JobSeekerSave save = new JobSeekerSave();
        save.setUserId(seekerProfile);
        save.setJob(jobPostActivity);
        jobSeekerSaveRepository.save(save);
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userProfile) {
        return jobSeekerSaveRepository.findAll()
                .stream()
                .filter(save -> save.getUserId().equals(userProfile))
                .toList();
    }

    // 🔹 Add this method to fix your controller issue
    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

	public void deleteSavedJob(JobSeekerProfile jobSeekerProfile, JobPostActivity jobPostActivity) {
		// TODO Auto-generated method stub
		
	}
}
