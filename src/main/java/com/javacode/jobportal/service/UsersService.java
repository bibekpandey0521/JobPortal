package com.javacode.jobportal.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javacode.jobportal.entity.JobSeekerProfile;
import com.javacode.jobportal.entity.RecruiterProfile;
import com.javacode.jobportal.entity.Users;
import com.javacode.jobportal.repository.JobSeekerProfileRepository;
import com.javacode.jobportal.repository.RecruiterProfileRepository;
import com.javacode.jobportal.repository.UsersRepository;

@Service
public class UsersService {
	
	private final UsersRepository usersRepository;
	private final JobSeekerProfileRepository jobSeekerProfileRepository;
	private final RecruiterProfileRepository recruiterProfileRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public UsersService(UsersRepository usersRepository,
			JobSeekerProfileRepository jobSeekerProfileRepository,
			RecruiterProfileRepository recruiterProfileRepository,
			PasswordEncoder passwordEncoder
			) {
		this.usersRepository = usersRepository;
		this.recruiterProfileRepository = recruiterProfileRepository;
		this.jobSeekerProfileRepository = jobSeekerProfileRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Users addNew(Users users) {
		users.setActive(true);
		users.setRegistrationDate(new Date(System.currentTimeMillis()));
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		
		Users savedUser = usersRepository.save(users);
		int userTypeId = users.getUserTypeId().getUserTypeId();
		if (userTypeId == 1) {
			recruiterProfileRepository.save(new RecruiterProfile(savedUser));
		}else {
			jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
		}
		
		return savedUser;	
	}
	
	public Optional<Users> getUserByEmail(String email){
		return usersRepository.findByEmail(email);
	}

	public Object getCurrentUserProfile() {
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(!(authentication instanceof AnonymousAuthenticationToken)) 
		{
			String username = authentication.getName();
			Users users = usersRepository.findByEmail(username).orElseThrow(()->new 
					UsernameNotFoundException("Could not Found"));
			int userId = users.getUserId();
			
			if(authentication.getAuthorities().contains(new SimpleGrantedAuthority
				("Recruiter"))) {
				RecruiterProfile recruiterProfile =	recruiterProfileRepository.findById
					(userId).orElse(new RecruiterProfile());
				return recruiterProfile;
			}else {
			JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById
					(userId).orElse(new JobSeekerProfile());
				return jobSeekerProfile;
			}
		}
		
		return null;
	}

	public Users getCurrentUser() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String username = authentication.getName();
			Users user = usersRepository.findByEmail(username).orElseThrow(
					()-> new UsernameNotFoundException("" + "user"));
			return user;
		}
		return null;
	}

	public Users findByEmail(String currentUsername) {
		return usersRepository.findByEmail(currentUsername).orElseThrow(()->new UsernameNotFoundException("User not found"));
	}
	
}
