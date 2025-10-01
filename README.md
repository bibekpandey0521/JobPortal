# JobPortal – Part 8  

👤 **Day 54 of #100DaysOfCode (Skill Shikshya Journey)**  

In Part 8 of the Job Portal project, I implemented **Job Seeker Profile** functionality with file upload support for images and resumes.  

---

## ✅ Features Implemented
1. Created **Job Candidate Profile** entity and form.  
2. Updated database entities to store Job Seeker information.  
3. Developed **Job Seeker Profile** creation flow.  
4. Added **file upload support** for profile images and resumes.  

---

## 📚 What I Learned
1. Handling **multipart file uploads** in Spring Boot.  
2. Associating uploaded **images and resumes** with Job Seeker profiles.  
3. Updating JPA entities for additional profile fields.  
4. Displaying uploaded files dynamically in the UI.  

---

## 🛠 Tech Stack
1. Spring Boot 3.5.5  
2. Spring Data JPA  
3. Thymeleaf  
4. Java 21  
5. MySQL  

---

## 📂 Project Structure (Highlights)
src/main/java/com/javacode/jobportal/
│
├── controller/
│ └── JobSeekerProfileController.java
├── entity/
│ └── JobSeekerProfile.java
├── repository/
│ └── JobSeekerProfileRepository.java
├── service/
│ └── FileUploadService.java
├── config/
│ └── WebSecurityConfig.java
└── ...
