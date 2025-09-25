# JWT Authentication & Middleware Setup for Spring Boot

This guide explains how to add JWT-based authentication and middleware to your Spring Boot application, including folder structure, dependencies, and all key steps.

---

## 📁 Recommended Folder Structure

```
src/main/java/com/yourapp/
│
├── config/                # Security configuration
│     └── SecurityConfig.java
├── controller/            # REST controllers
│     └── AuthController.java
├── filter/                # JWT filter (middleware)
│     └── JwtAuthFilter.java
├── model/                 # User and role models
│     └── User.java
│     └── Role.java
├── repository/            # User repository
│     └── UserRepository.java
├── service/               # User and JWT services
│     └── UserService.java
│     └── JwtUtil.java
└── ...
```

---

## 1. Add Dependencies
Add these to your `pom.xml`:

- `spring-boot-starter-security`
- `spring-boot-starter-web`
- `jjwt` (or `java-jwt`)

---

## 2. User Model & Roles
- Create a `User` entity with fields: `id`, `username`, `password`, `roles` (as a collection).
- Create a `Role` enum or entity (e.g., `USER`, `ADMIN`).

---

## 3. Authentication Controller
- Create an `AuthController` with a `/login` endpoint.
- Accept username & password, validate credentials.
- If valid, generate and return a JWT token.

---

## 4. JWT Utility Class
- Create a `JwtUtil` class with methods to generate, validate, and parse JWT tokens.
- Store your secret key securely (e.g., in `application.properties`).

---

## 5. JWT Filter (Middleware)
- Create a `JwtAuthFilter` that:
	- Intercepts requests.
	- Extracts and validates JWT from the `Authorization: Bearer <token>` header.
	- If valid, sets authentication in the security context.

---

## 6. Spring Security Configuration
- Create a `SecurityConfig` class.
- Register your JWT filter.
- Configure which endpoints require authentication and which are public (e.g., `/login` is public).
- Use annotations like `@PreAuthorize("hasRole('ADMIN')")` for role-based access.

---

## 7. Protect Endpoints
- Annotate controller methods to restrict access by role, e.g.:
	- `@PreAuthorize("hasRole('ADMIN')")`
	- `@PreAuthorize("hasAnyRole('USER', 'ADMIN')")`

---

## 8. Frontend Usage
- On login, store the JWT token (localStorage/sessionStorage).
- Send the token in the `Authorization` header for protected API requests.

---

## 9. Example Libraries
- [jjwt](https://github.com/jwtk/jjwt)
- [java-jwt](https://github.com/auth0/java-jwt)

---

## 10. Example Flow
1. User logs in → receives JWT → stores it.
2. For protected endpoints, frontend sends JWT in `Authorization: Bearer <token>` header.
3. Backend validates JWT and authorizes access based on roles.

---

## 11. Tips
- Always keep your JWT secret key safe.
- Use HTTPS in production.
- Set token expiration for security.
- Handle token errors gracefully (expired, invalid, etc).

---

**This structure and checklist will help you implement robust JWT authentication and middleware in your Spring Boot app.**
