# QuizApp Security Explained (Beginner Friendly)

This guide explains every security concept, annotation, and class used in QuizApp so that even someone new to Java Sprin### 2.10. Dependency Injection
- **What is Dependency Injection?**
  - It's a fancy term for "Spring gives you the objects you need."
  - Instead of creating objects with `new`, you let Spring do it for you.

- **Why is it useful?**
  - Less boilerplate code.
  - Easy to test (you can replace real objects with mock objects).
  - Spring manages object lifecycle.

- **How does it work?**
  1. Mark a class with `@Service`, `@Component`, etc. (makes it a bean).
  2. In another class, use `@Autowired` to get that bean.
  3. Spring automatically creates the object and injects it.

- **Example:**
  ```java
  @Service
  public class UserService {
      @Autowired
      private JwtUtil jwtUtil; // Spring injects JwtUtil here
      
      public String login(String username, String password) {
          return jwtUtil.generateToken(username);
      }
  }
  ```

- **Without Dependency Injection:**
  ```java
  public class UserService {
      private JwtUtil jwtUtil = new JwtUtil(); // Manual creation
  }
  ```

- **With Spring (Dependency Injection):**
  - Spring creates `JwtUtil` once.
  - Spring gives it to `UserService` automatically.
  - You don't worry about object creation.oot can understand how and why things work.

---

## 1. Why Security?
- Security protects your app from unauthorized access and data leaks.
- We use JWT (JSON Web Token) to make sure only logged-in users can access protected APIs.

---

## 2. Key Concepts & Classes

### 2.1. Controller (`@RestController`)
- Handles HTTP requests (like `/auth/login`).
- `@RestController` tells Spring this class will handle web requests and return data (usually JSON).

### 2.2. Service (`@Service`)
- Contains business logic (like checking login credentials).
- `@Service` marks the class as a Spring bean (object managed by Spring).

### 2.3. Bean (`@Component`, `@Service`, `@Autowired`)
- **What is a Bean?**
  - A bean is just an object (instance of a class) that Spring creates and manages for you.
  - Think of Spring as a factory that creates and stores objects so you don't have to use `new MyClass()` everywhere.
  - Spring keeps these objects in a "container" and gives them to you when needed.

- **Why use Beans?**
  - You don't have to manually create objects.
  - Spring handles object lifecycles (creation, destruction).
  - Easy to swap implementations and write tests.

- **How to make a Bean?**
  - **Option 1: Use `@Component` / `@Service` / `@Repository` / `@Controller`**
    - Add the annotation on top of your class.
    - Spring automatically creates one instance when the app starts.
    - Use this when YOU wrote the class (your own code).
    - Example:
      ```java
      @Service
      public class UserService { } // Spring creates UserService bean
      ```
  
  - **Option 2: Use `@Bean` method inside `@Configuration` class**
    - Create a method that returns an object.
    - Mark the method with `@Bean`.
    - Spring calls this method and stores the returned object as a bean.
    - Use this when:
      - You need custom setup/configuration before creating the object.
      - You're using a third-party class (can't add `@Component` to it).
      - You want to create multiple beans of the same type with different configs.
    - Example:
      ```java
      @Configuration
      public class SecurityConfig {
          @Bean
          public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
              // Custom setup here
              http.csrf().disable();
              return http.build(); // Spring stores this as a bean
          }
      }
      ```

- **When to use which?**
  - `@Component` / `@Service`: For YOUR classes (classes you write).
  - `@Bean`: For complex setup or third-party classes (classes from libraries).

- **What if you DON'T use Beans (manual way)?**
  - **Without Spring Beans:**
    ```java
    public class AuthController {
        private UserService userService = new UserService(); // You create it
        
        public ResponseEntity<?> login(String username, String password) {
            return userService.handleLogin(username, password);
        }
    }
    
    public class UserService {
        private JwtUtil jwtUtil = new JwtUtil(); // You create it
        
        public ResponseEntity<?> handleLogin(String username, String password) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }
    }
    ```
    **Problems:**
    - You have to manually create every object with `new`.
    - If `JwtUtil` needs other objects, you have to create those too.
    - Hard to test (can't easily replace real objects with mock objects).
    - If you want to change how `JwtUtil` is created, you have to update every place you used `new JwtUtil()`.

  - **With Spring Beans (automatic way):**
    ```java
    @RestController
    public class AuthController {
        @Autowired
        private UserService userService; // Spring gives it to you
        
        public ResponseEntity<?> login(String username, String password) {
            return userService.handleLogin(username, password);
        }
    }
    
    @Service
    public class UserService {
        @Autowired
        private JwtUtil jwtUtil; // Spring gives it to you
        
        public ResponseEntity<?> handleLogin(String username, String password) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(token);
        }
    }
    ```
    **Benefits:**
    - Spring creates and manages all objects for you.
    - Easy to test (Spring can inject mock objects).
    - One place to configure how objects are created.
    - Spring handles dependencies automatically (if `JwtUtil` needs other objects, Spring provides them).

- **How to use a Bean?**
  - Use `@Autowired` to inject (get) the bean into another class.
  - Example:
    ```java
    @Service
    public class UserService {
        @Autowired
        private JwtUtil jwtUtil; // Spring gives us JwtUtil automatically
    }
    ```

- **Types of Bean Annotations:**
  - `@Component`: General purpose bean (any class).
  - `@Service`: Bean for business logic (like UserService).
  - `@Repository`: Bean for database access (like DAO classes).
  - `@Controller` / `@RestController`: Bean for handling web requests.

### 2.4. JWT (JSON Web Token)
- **What is JWT?**
  - JWT is like a digital passport that proves who you are.
  - It's a string (looks random) that contains your info (like username) and is digitally signed.

- **Why use JWT?**
  - No need to store sessions on the server (stateless).
  - The server can verify the token without checking a database every time.
  - Scalable: works great with multiple servers.

- **How does it work?**
  1. User logs in with username/password.
  2. Server creates a JWT token with username inside and signs it with a secret key.
  3. Server sends the token back to the user.
  4. User sends the token with every request (in the `Authorization` header).
  5. Server checks the signature—if valid, user is authenticated.

- **JWT Structure (3 parts separated by dots):**
  - **Header:** Info about the token (algorithm used).
  - **Payload:** Data (like username, expiration time).
  - **Signature:** Proof that the token wasn't tampered with.
  - Example: `xxxxx.yyyyy.zzzzz`

- **Can someone fake a JWT?**
  - No! The signature is created using a secret key only the server knows.
  - If someone changes the payload, the signature won't match.

### 2.5. JWT Utility (`JwtUtil`)
- Makes and checks JWT tokens.
- Reads secret key and expiration from `application.properties`.
- Methods:
  - `generateToken(username)`: Makes a token for a user.
  - `validateToken(token)`: Checks if a token is real and not expired.
  - `extractUsername(token)`: Gets the username from the token.

### 2.6. JWT Filter (`JwtAuthFilter`)
- **What is a Filter?**
  - A filter is code that runs BEFORE your API endpoint is called.
  - Think of it like a security guard checking IDs before letting people into a building.

- **What does JwtAuthFilter do?**
  - Checks every incoming request for a JWT token.
  - If the token is valid, marks the user as logged in for that request.
  - If no token or invalid token, the request continues but user is not logged in.

- **How does it work?**
  1. Extends `OncePerRequestFilter` (Spring runs this filter once per request).
  2. Overrides `doFilterInternal` method (your custom logic goes here).
  3. Gets the `Authorization` header from the request.
  4. Checks if it starts with "Bearer " (standard JWT format).
  5. Extracts the token (removes "Bearer " prefix).
  6. Validates the token using `JwtUtil`.
  7. If valid, extracts username and creates authentication object.
  8. Puts authentication in `SecurityContextHolder` (tells Spring who is logged in).
  9. Calls `filterChain.doFilter()` to continue to the next step.

- **Key Methods:**
  - `doFilterInternal`: Your filter logic.
  - `filterChain.doFilter()`: Pass request to next filter or controller.
  - `SecurityContextHolder.getContext().setAuthentication()`: Tell Spring who's logged in.

- **Exception Handling:**
  - `throws ServletException, IOException`: Required by Spring to handle web errors.

### 2.7. Security Configuration (`SecurityConfig`)
- **What is SecurityConfig?**
  - A class where you set all your security rules in one place.
  - Tells Spring: "These endpoints are public, these need login."

- **Key Annotations:**
  - `@Configuration`: Tells Spring this class contains configuration (settings).
  - `@EnableWebSecurity`: Turns on Spring Security features.

- **What does it do?**
  1. Registers your JWT filter.
  2. Defines which endpoints are public and which need authentication.
  3. Disables CSRF (not needed for APIs using JWT).
  4. Adds your filter to the security chain.

- **SecurityFilterChain Bean:**
  - A method that returns security rules.
  - Marked with `@Bean` so Spring can use it.
  - Example:
    ```java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll() // Public
                .anyRequest().authenticated() // Everything else needs login
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    ```

- **Why disable CSRF?**
  - CSRF protection is for cookie-based authentication.
  - JWT uses tokens in headers, not cookies, so CSRF is not needed.

- **Filter Order:**
  - `addFilterBefore`: Adds JWT filter before Spring's default authentication filter.
  - This means JWT filter runs first to check tokens.

### 2.8. Logging (`@Slf4j`, `LoggerFactory`)
- Helps you see what’s happening (for debugging).
- `@Slf4j` (from Lombok) or `LoggerFactory` creates a logger you can use like:
  - `log.info("message")`
  - `log.warn("warning")`
  - `log.debug("debug info")`

### 2.9. Properties (`application.properties`)
- Stores settings like database info and JWT secret.
- Example:
  - `jwt.secret=your_secret_key_here`
  - `jwt.expiration-ms=3600000`

### 2.10. Dependency Injection
- Spring automatically creates and connects beans for you.
- You don’t need to manually create objects—just use `@Autowired`.

---

## 3. Common Annotations Explained

### `@RestController`
- Marks a class as a REST API controller.
- Combines `@Controller` (handles web requests) and `@ResponseBody` (returns data as JSON).
- Example: `AuthController` handles `/auth/login`.

### `@Service`
- Marks a class as a service (business logic layer).
- Spring creates one instance of this class (makes it a bean).
- Example: `UserService` handles login logic.

### `@Component`
- General purpose annotation to make any class a bean.
- Use this when a class doesn't fit `@Service`, `@Repository`, or `@Controller`.
- Example: `JwtUtil` is marked as `@Component`.

### `@Autowired`
- Tells Spring to inject (give you) a bean automatically.
- No need to use `new` to create objects.
- Example:
  ```java
  @Autowired
  private JwtUtil jwtUtil; // Spring gives us JwtUtil
  ```

### `@Configuration`
- Marks a class as a configuration class (contains settings and beans).
- Used with `@Bean` methods to create beans manually.
- Example: `SecurityConfig` is marked as `@Configuration`.

### `@EnableWebSecurity`
- Turns on Spring Security features.
- Must be used with `@Configuration`.
- Example: Used in `SecurityConfig` to enable JWT security.

### `@Bean`
- Marks a method that creates and returns a bean.
- Used inside `@Configuration` classes.
- Spring calls this method and stores the returned object as a bean.
- Example:
  ```java
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) {
      return http.build();
  }
  ```

### `@Slf4j`
- From Lombok library.
- Automatically creates a logger variable called `log`.
- Use it like: `log.info("message")`, `log.error("error")`.
- Saves you from writing `Logger log = LoggerFactory.getLogger(MyClass.class);`.

### `@PostMapping` / `@GetMapping`
- Maps HTTP requests to methods.
- `@PostMapping("/login")`: This method handles POST requests to `/login`.
- `@GetMapping("/users")`: This method handles GET requests to `/users`.

### `@RequestParam`
- Gets data from URL query parameters.
- Example: `/login?username=john&password=1234`
  ```java
  public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password)
  ```

### `@RequestBody`
- Gets data from the request body (usually JSON).
- Example: POST request with JSON body `{"username": "john", "password": "1234"}`
  ```java
  public ResponseEntity<?> login(@RequestBody LoginRequest request)
  ```

### `@Value`
- Injects values from `application.properties` into variables.
- Example:
  ```java
  @Value("${jwt.secret}")
  private String secretKey; // Gets value from application.properties
  ```

---

## 4. How Everything Works Together
1. User sends login request to `/auth/login`.
2. If credentials are correct, server sends back a JWT token.
3. For every other API request, the client sends the token in the `Authorization` header.
4. `JwtAuthFilter` checks the token. If valid, user is marked as logged in for that request.
5. SecurityConfig makes sure only logged-in users can access protected APIs.

---

## 5. Why These Steps?
- **JWT:** No need to store sessions on the server. Scalable and stateless.
- **Filter:** Checks every request for security.
- **SecurityConfig:** Central place to set security rules.
- **Annotations:** Make code cleaner and easier to manage.

---

## 6. What’s Next?
- Add a user database (not just hardcoded users).
- Add roles (admin, user, etc.) and restrict APIs by role.
- Handle token expiration and errors better.

---

**Last updated:** October 8, 2025
