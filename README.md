# JobPortal

A Spring Boot Java project for job portal management. This project uses **Thymeleaf** for templates and integrates front-end assets like **Bootstrap** and **Font Awesome** using WebJars.

---

## Project Setup

### 1. Prerequisites
- Java 21
- Maven 3.8+
- Spring Boot 3.5.5
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- MySQL or any relational database (for future database integration)

---

### 2. Clone the Repository
```bash
git clone https://github.com/bibekpandey0521/JpbPortal.git
cd JpbPortal/jobportal

jobportal/
│
├── src/
│   ├── main/
│   │   ├── java/com/javacode/jobportal/       # Java classes
│   │   └── resources/
│   │       ├── application.properties        # Spring Boot config
│   │       └── templates/                    # Thymeleaf templates
│   │           ├── index.html
│   │           └── other-pages.html
│   └── test/                                 # Unit tests
└── pom.xml   
