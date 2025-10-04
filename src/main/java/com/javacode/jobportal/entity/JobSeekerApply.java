package com.javacode.jobportal.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"userId","job"})
})

public class JobSeekerApply implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId",referencedColumnName = "user_account_id")
	private JobSeekerProfile userId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "job",referencedColumnName = "jobPostId")
	private JobPostActivity job;
	
	@DateTimeFormat(pattern = "dd-MM-yyy")
	private Date applyDate;
	
	
	private String coverletter;

	public JobSeekerApply() {
		
	}
	public JobSeekerApply(Integer id, JobSeekerProfile userId, JobPostActivity job, Date applyDate,
			String coverletter) {
		this.id = id;
		this.userId = userId;
		this.job = job;
		this.applyDate = applyDate;
		this.coverletter = coverletter;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public JobSeekerProfile getUserId() {
		return userId;
	}
	public void setUserId(JobSeekerProfile userId) {
		this.userId = userId;
	}
	public JobPostActivity getJob() {
		return job;
	}
	public void setJob(JobPostActivity job) {
		this.job = job;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getCoverletter() {
		return coverletter;
	}
	public void setCoverletter(String coverletter) {
		this.coverletter = coverletter;
	}
	@Override
	public String toString() {
		return "JobSeekerApply [id=" + id + ", userId=" + userId + ", job=" + job + ", applyDate=" + applyDate
				+ ", coverletter=" + coverletter + "]";
	}
	
	
}

