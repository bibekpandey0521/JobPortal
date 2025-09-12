package com.javacode.jobportal.repository;

import com.javacode.jobportal.entity.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile,Integer>{
	
}
