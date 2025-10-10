mvn spring-boot:run

mvn spring-boot:run -X
# QuizApp

QuizApp is a Spring Boot-based RESTful API for managing quizzes and questions, now with JWT-based authentication for secure access. It provides endpoints to create, retrieve, and manage quizzes and their questions, making it suitable for online quiz platforms, educational tools, or practice test systems.

## Features
- JWT-based user authentication (login required for most endpoints)
- Secure API endpoints (protected with JWT token)
- Create and manage quizzes
- Add, update, and retrieve questions
- Layered architecture: Controller, Service, DAO
- Uses Spring Data JPA for data persistence
- Configurable via `application.properties`
- Logging with SLF4J

## Project Structure
```
quizapp/
├── src/
│   ├── main/
│   │   ├── java/com/dewansh/quizapp/
│   │   │   ├── controller/         # REST controllers for API endpoints
│   │   │   ├── dao/                # Data access objects (repositories)
│   │   │   ├── model/              # Entity and DTO classes
│   │   │   ├── service/            # Business logic layer (JwtUtil, JwtAuthFilter, UserService, etc.)
│   │   │   ├── config/             # Security configuration (SecurityConfig)
│   │   │   └── QuizappApplication.java # Main Spring Boot application
│   │   └── resources/
│   │       ├── application.properties # App configuration
│   │       ├── static/                # Static resources
│   │       └── templates/             # Template files (if any)
│   └── test/
│       └── java/com/dewansh/quizapp/  # Unit and integration tests
├── documentation/                     # Security and technical documentation
├── pom.xml                            # Maven build file
└── ...
```

## Getting Started
1. **Clone the repository**
2. **Configure the database** in `src/main/resources/application.properties`
3. **Set JWT secret and expiration** in `application.properties`
4. **Build the project**:
    ```
    ./mvnw clean install
    ```
5. **Run the application**:
    ```
    ./mvnw spring-boot:run
    ```
6. **Access the API** at `http://localhost:8080/`

## Authentication & Security
- **Login Endpoint:**
  - `POST /auth/login` (requires username and password)
  - Returns a JWT token on successful login
- **Protected Endpoints:**
  - All other endpoints require `Authorization: Bearer <token>` header
  - Requests without a valid token are denied
- **Security Implementation:**
  - JWT token generation and validation (`JwtUtil`)
  - Request filtering (`JwtAuthFilter`)
  - Security configuration (`SecurityConfig`)
  - See `documentation/security_explained.md` for details

## API Endpoints
- `/auth/login` - User login (returns JWT token)
- `/question` - Manage questions (protected)
- `/quiz` - Manage quizzes (protected)

Refer to the controller classes and documentation for detailed endpoint information.

## Technologies Used
- Java 17+
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA
- Maven
- Lombok (for logging)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

---

## Future Improvements
- Integrate user database (replace hardcoded credentials)
- Add role-based access control (RBAC)
- Implement quiz result tracking and analytics
- Add support for multiple question types (MCQ, True/False, etc.)
- Create a frontend UI for quiz participation
- Add timer and scoring features
- Integrate with external databases or cloud storage
- Add more comprehensive test coverage
- Enable quiz sharing and public/private quiz modes
- Improve error handling and token management

---

For a detailed explanation of security, see `documentation/security_explained.md`.