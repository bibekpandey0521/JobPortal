package com.javacode.jobportal.controller;

import com.javacode.jobportal.entity.*;
import com.javacode.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {
        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile recruiter = recruiterProfileService.getCurrentRecruiterProfile();
                if (recruiter != null) {
                    model.addAttribute("applyList", jobSeekerApplyList);
                }
            } else {
                JobSeekerProfile seeker = jobSeekerProfileService.getCurrentSeekerProfile();
                if (seeker != null) {
                    boolean alreadyApplied = jobSeekerApplyList.stream()
                            .anyMatch(app -> app.getUserId().getUserAccountId() == seeker.getUserAccountId());

                    boolean alreadySaved = jobSeekerSaveList.stream()
                            .anyMatch(save -> save.getUserId().getUserAccountId() == seeker.getUserAccountId());

                    model.addAttribute("alreadyApplied", alreadyApplied);
                    model.addAttribute("alreadySaved", alreadySaved);
                }
            }
        }

        model.addAttribute("applyJob", new JobSeekerApply());
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "job-details";
    }

    @PostMapping("job-details/apply/{id}")
    public String apply(@PathVariable("id") int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);

            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);

            if (seekerProfile.isEmpty() || jobPostActivity == null) {
                throw new RuntimeException("Invalid user or job");
            }

            jobSeekerApplyService.addNew(seekerProfile.get(), jobPostActivity);
        }

        return "redirect:/dashboard/";
    }
}
