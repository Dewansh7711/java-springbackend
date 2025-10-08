
# QuizApp Security Implementation Documentation

This document details all security-related features and configurations implemented in the QuizApp project as of October 8, 2025.

---

## 1. Authentication Flow Overview

### 1.1. Login Endpoint (`AuthController`)
- **Endpoint:** `/auth/login`
- **Controller:** `AuthController`
- **Service:** `UserService`
- **Logic:**
  - Accepts username and password via POST request.
  - Validates credentials (currently hardcoded for demo: username `dewansh`, password `huihuihui`).
  - On success, generates a JWT token using `JwtUtil` and returns it in the response.
  - On failure, returns HTTP 401 Unauthorized.

### 1.2. JWT Token Generation (`JwtUtil`)
- **Class:** `JwtUtil`
- **Details:**
  - Uses `io.jsonwebtoken` (jjwt) library.
  - Secret key and expiration time are loaded from `application.properties`:
    - `jwt.secret=please_change_this_to_a_strong_secret_key_of_at_least_32_chars`
    - `jwt.expiration-ms=3600000` (1 hour)
  - Token includes subject (username), issued at, and expiration claims.
  - Methods:
    - `generateToken(String username)`: creates JWT token.
    - `validateToken(String token)`: validates token signature and expiration.
    - `extractUsername(String token)`: gets username from token.

### 1.3. JWT Filter (`JwtAuthFilter`)
- **Class:** `JwtAuthFilter` (extends `OncePerRequestFilter`)
- **Purpose:**
  - Intercepts every HTTP request.
  - Extracts JWT token from the `Authorization: Bearer <token>` header.
  - Validates the token using `JwtUtil`.
  - If valid, extracts username and sets authentication in the Spring Security context.
- **Key Steps:**
  1. Extended `OncePerRequestFilter`.
  2. Overrode `doFilterInternal` (handles `ServletException`, `IOException`).
  3. Extracted the Authorization header.
  4. Checked for "Bearer" prefix.
  5. Extracted the token.
  6. Validated the token.
  7. Extracted username from token.
  8. Created `UsernamePasswordAuthenticationToken`.
  9. Set authentication in `SecurityContextHolder`.
  10. Called `filterChain.doFilter(request, response)` to continue processing.
  11. Used SLF4J logging for debug/info/warn.

### 1.4. Security Configuration (`SecurityConfig`)
- **Class:** `SecurityConfig`
- **Purpose:**
  - Registers the JWT filter.
  - Configures which endpoints require authentication and which are public.
  - Disables CSRF for APIs.
- **Key Steps:**
  1. Annotated with `@Configuration` and `@EnableWebSecurity`.
  2. Defines a `SecurityFilterChain` bean.
  3. Permits `/auth/login` for all users.
  4. Requires authentication for all other endpoints.
  5. Adds `JwtAuthFilter` before `UsernamePasswordAuthenticationFilter`.

---

## 2. Logging
- **Logger:** SLF4J (`LoggerFactory` or Lombok `@Slf4j`)
- **Usage:**
  - Logs login attempts, errors, and debug info in `UserService`, `AuthController`, and `JwtAuthFilter`.

---

## 3. Security Properties
- **File:** `src/main/resources/application.properties`
- **Properties:**
  - `jwt.secret` (HMAC key for signing JWTs)
  - `jwt.expiration-ms` (token validity duration)

---

## 4. Dependencies
- **Maven (`pom.xml`):**
  - `spring-boot-starter-security` (for security framework)
  - `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson` (for JWT handling)

---

## 5. Areas Not Yet Implemented
- **Role-based access control (RBAC):** Not yet implemented.
- **User database:** Currently uses hardcoded credentials.
- **Endpoint protection:** Only login is public; other endpoints are protected by JWT filter.
- **Error handling:** Basic error handling for invalid/expired tokens.

---

## 6. Next Steps
- Replace hardcoded credentials with a user database.
- Add role-based access and permissions.
- Add more robust error handling and token refresh.

---

**Last updated:** October 8, 2025
