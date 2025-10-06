package com.javacode.jobportal.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.javacode.jobportal.entity.JobPostActivity;
import com.javacode.jobportal.entity.JobSeekerApply;
import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.entity.JobSeekerSave;
import com.javacode.jobportal.entity.RecruiterProfile;
import com.javacode.jobportal.entity.Users;
import com.javacode.jobportal.service.JobPostActivityService;
import com.javacode.jobportal.service.JobSeekerApplyService;
import com.javacode.jobportal.service.JobSeekerProfileService;
import com.javacode.jobportal.service.JobSeekerSaveService;
import com.javacode.jobportal.service.RecruiterProfileService;
import com.javacode.jobportal.service.UsersService;

@Controller
public class JobSeekerApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final JobSeekerApplyService jobSeekerApplyService;
    private final JobSeekerSaveService jobSeekerSaveService;
    private final RecruiterProfileService recruiterProfileService;
    private final JobSeekerProfileService jobSeekerProfileService;

    @Autowired
    public JobSeekerApplyController(
            JobPostActivityService jobPostActivityService,
            UsersService usersService,
            JobSeekerApplyService jobSeekerApplyService,
            JobSeekerSaveService jobSeekerSaveService,
            RecruiterProfileService recruiterProfileService,
            JobSeekerProfileService jobSeekerProfileService) {

        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.jobSeekerApplyService = jobSeekerApplyService;
        this.jobSeekerSaveService = jobSeekerSaveService;
        this.recruiterProfileService = recruiterProfileService;
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    // ===========================
    // ✅ Display Job Details Page
    // ===========================
    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
                if (user != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
                if (user != null) {
                    boolean exists = jobSeekerApplyList.stream()
                            .anyMatch(apply -> apply.getUserId().getUserAccountId() == user.getUserAccountId());
                    boolean saved = jobSeekerSaveList.stream()
                            .anyMatch(save -> save.getUserId().getUserAccountId() == user.getUserAccountId());

                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        model.addAttribute("applyJob", new JobSeekerApply());
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "job-details";
    }

    // ===========================================
    // ✅ Apply to a Job (with duplicate prevention)
    // ===========================================
    @PostMapping("/job-details-apply/{id}")
    public String applyToJob(@PathVariable("id") int id, JobSeekerApply formApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login?error=unauthorized";
        }

        String currentUsername = authentication.getName();
        Users user = usersService.findByEmail(currentUsername);
        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

        if (seekerProfile.isEmpty() || jobPostActivity == null) {
            throw new RuntimeException("User or job not found");
        }

        JobSeekerProfile profile = seekerProfile.get();

        // ✅ Prevent duplicate application
        boolean alreadyExists = jobSeekerApplyService.existsByUserIdAndJob(profile, jobPostActivity);
        if (alreadyExists) {
            return "redirect:/job-details-apply/" + id + "?alreadyApplied=true";
        }

        // ✅ Always treat as new entity
        JobSeekerApply jobSeekerApply = new JobSeekerApply();
        jobSeekerApply.setId(null);
        jobSeekerApply.setUserId(profile);
        jobSeekerApply.setJob(jobPostActivity);
        jobSeekerApply.setApplyDate(new Date());
        jobSeekerApply.setCoverletter(formApply.getCoverletter());

        jobSeekerApplyService.addNew(jobSeekerApply);

        return "redirect:/job-details-apply/" + id + "?success=true";
    }

    // ===========================
    // ✅ Edit job for recruiter
    // ===========================
    @PostMapping("dashboard/edit/{id}")
    public String editJob(@PathVariable("id") int id, Model model) {
        JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
        model.addAttribute("jobPostActivity", jobPostActivity);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }
}
