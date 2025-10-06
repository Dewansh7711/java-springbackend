mvn spring-boot:run

mvn spring-boot:run -X
# QuizApp

QuizApp is a Spring Boot-based RESTful API for managing quizzes and questions. It provides endpoints to create, retrieve, and manage quizzes and their questions, making it suitable for online quiz platforms, educational tools, or practice test systems.

## Features
- Create and manage quizzes
- Add, update, and retrieve questions
- RESTful API endpoints for quiz and question operations
- Layered architecture with Controller, Service, and DAO layers
- Uses Spring Data JPA for data persistence
- Configurable via `application.properties`

## Project Structure
```
quizapp/
├── src/
│   ├── main/
│   │   ├── java/com/dewansh/quizapp/
│   │   │   ├── controller/         # REST controllers for API endpoints
│   │   │   ├── dao/                # Data access objects (repositories)
│   │   │   ├── model/              # Entity and DTO classes
│   │   │   ├── service/            # Business logic layer
│   │   │   └── QuizappApplication.java # Main Spring Boot application
│   │   └── resources/
│   │       ├── application.properties # App configuration
│   │       ├── static/                # Static resources
│   │       └── templates/             # Template files (if any)
│   └── test/
│       └── java/com/dewansh/quizapp/  # Unit and integration tests
├── pom.xml                            # Maven build file
└── ...
```

## Getting Started
1. **Clone the repository**
2. **Configure the database** in `src/main/resources/application.properties`
3. **Build the project**:
	```
	./mvnw clean install
	```
4. **Run the application**:
	```
	./mvnw spring-boot:run
	```
5. **Access the API** at `http://localhost:8080/`

## API Endpoints
- `/question` - Manage questions
- `/quiz` - Manage quizzes

Refer to the controller classes for detailed endpoint information.

## Technologies Used
- Java 17+
- Spring Boot
- Spring Data JPA
- Maven

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.

---

## Future Improvements
- Add user authentication and authorization (JWT-based)
- Implement quiz result tracking and analytics
- Add support for multiple question types (MCQ, True/False, etc.)
- Create a frontend UI for quiz participation
- Add timer and scoring features
- Integrate with external databases or cloud storage
- Add more comprehensive test coverage
- Enable quiz sharing and public/private quiz modes