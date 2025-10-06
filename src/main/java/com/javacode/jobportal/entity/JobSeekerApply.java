package com.javacode.jobportal.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "job"})
})
public class JobSeekerApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ✅ Removed cascade = ALL (important!)
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id", nullable = false)
    private JobSeekerProfile userId;

    @ManyToOne
    @JoinColumn(name = "job", referencedColumnName = "jobPostId", nullable = false)
    private JobPostActivity job;

    @DateTimeFormat(pattern = "dd-MM-yyyy") // ✅ fixed typo from yyy → yyyy
    private Date applyDate;

    private String coverletter;

    public JobSeekerApply() {}

    public JobSeekerApply(Integer id, JobSeekerProfile userId, JobPostActivity job, Date applyDate, String coverletter) {
        this.id = id;
        this.userId = userId;
        this.job = job;
        this.applyDate = applyDate;
        this.coverletter = coverletter;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public JobSeekerProfile getUserId() { return userId; }
    public void setUserId(JobSeekerProfile userId) { this.userId = userId; }

    public JobPostActivity getJob() { return job; }
    public void setJob(JobPostActivity job) { this.job = job; }

    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }

    public String getCoverletter() { return coverletter; }
    public void setCoverletter(String coverletter) { this.coverletter = coverletter; }

    @Override
    public String toString() {
        return "JobSeekerApply [id=" + id + ", userId=" + userId + ", job=" + job +
                ", applyDate=" + applyDate + ", coverletter=" + coverletter + "]";
    }
}
