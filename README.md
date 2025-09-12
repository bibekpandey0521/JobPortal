# JobPortal – Part 3  

🚀 **Day 47 of #100DaysOfCode (Skill Shikshya Journey)**  

This part of the project focuses on **user registration, duplicate email validation, and profile creation** for different user roles.  

---

## ✅ Features Implemented
1. **Registration Controller** – handles user signups via Thymeleaf form  
2. **Duplicate Email Fix** – prevents multiple accounts with the same email  
3. **Recruiter Profile** – separate profile section for recruiters  
4. **Job Seeker Profile** – profile setup for job seekers  
5. **Integration with Entities** – linked `Users` and `UsersType` entities during registration  

---

## 📚 What I Learned
- Creating **controllers** to handle registration  
- Applying **validation** for unique fields like email  
- Designing **role-based profiles** (Recruiters vs Job Seekers)  
- Structuring entities and repositories for clean persistence  

---

## 🛠 Tech Stack
- **Spring Boot 3.5.5**  
- **Spring Data JPA**  
- **Thymeleaf**  
- **Java 21**  
- **MySQL**  

---

## 📂 Project Structure (Highlights)
src/main/java/com/javacode/jobportal/
│
├── controller/
│ └── RegistrationController.java
├── entity/
│ ├── Users.java
│ └── UsersType.java
├── repository/
│ ├── UsersRepository.java
│ └── UsersTypeRepository.java
└── ...

---

## 🔜 Next Steps
- Add **login functionality** with authentication  
- Implement **role-based access control**  
- Enhance Recruiter & Job Seeker profiles with more details  

---

## 📌 Repository
👉 [View on GitHub – Part 3](https://github.com/bibekpandey0521/JobPortal/tree/Part3)  

---

## Hashtags
#SpringBoot #Java #100DaysOfCode #JPA #CRUD #Thymeleaf #SkillShikshya  
