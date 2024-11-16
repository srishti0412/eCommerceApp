
---

# E-Commerce Project with JWT Authentication and Role-Based Access Control

This project is a simple E-commerce application that implements authentication and role-based access control using **JWT (JSON Web Tokens)** and **HTTP-only cookies**. The system supports different roles like **Admin**, **Seller**, and **User**. Users can sign up, log in, and access secured resources based on their roles.

## Features

- **JWT Authentication**: Users authenticate using their credentials, and a JWT token is generated and stored in an HTTP-only cookie.
- **Role-based Authorization**: Different roles (Admin, Seller, User) are assigned, and each role has different permissions to access various endpoints.
- **Spring Security Integration**: Utilizes Spring Security to handle authentication, authorization, and securing endpoints.
- **Cookie-based Authentication**: JWT is stored in a secure, HTTP-only cookie for subsequent requests.

## Technology Stack

- **Spring Boot**: The main framework for building the backend.
- **Spring Security**: For authentication and authorization.
- **JWT (JSON Web Token)**: Used to authenticate and authorize users.
- **H2 Database**: In-memory database for quick development and testing.
- **Maven**: Dependency management and build tool.
- **Postman**: Used for testing the APIs.

## Prerequisites

Before running the project, ensure you have the following installed on your machine:

- **JDK 17** or later
- **Maven** for building the project
- **Postman** or any other API testing tool

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/ecommerce-jwt-auth.git
   cd ecommerce-jwt-auth
   ```

2. **Build the Project:**
   Using Maven, you can build the project:
   ```bash
   mvn clean install
   ```

3. **Run the Application:**
   Run the application using:
   ```bash
   mvn spring-boot:run
   ```
   This will start the application on `http://localhost:8080`.

4. **H2 Console:**
   Access the H2 database console at `http://localhost:8080/h2-console` using the default JDBC URL:
   ```
   jdbc:h2:mem:testdb
   ```

## API Endpoints

### 1. **User Registration (Sign Up)**

**POST** `/api/auth/signup`

Request Body:
```json
{
  "username": "user1",
  "email": "user1@example.com",
  "password": "password123",
  "roles": ["USER"]
}
```

Response:
```json
{
  "message": "User registered successfully!"
}
```

### 2. **User Login (Sign In)**

**POST** `/api/auth/signin`

Request Body:
```json
{
  "username": "user1",
  "password": "password123"
}
```

Response:
```json
{
  "id": 1,
  "username": "user1",
  "roles": ["ROLE_USER"],
  "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTYwNjg2MTI3MiwiZXhwIjoxNzE4NjQyOTkyfQ..."
}
```

The `jwtToken` will be stored in an HTTP-only cookie.

### 3. **Get User Info (Authenticated)**

**GET** `/api/auth/user`

Headers:
- **Cookie**: `jwtToken=eyJhbGciOiJIUzI1Ni...`

Response:
```json
{
  "id": 1,
  "username": "user1",
  "roles": ["ROLE_USER"]
}
```

### 4. **Sign Out (Logout)**

**POST** `/api/auth/signout`

Response:
```json
{
  "message": "You've been signed out!"
}
```

### 5. **Admin-Only Endpoint**

**GET** `/api/admin/**`  
**Roles Required**: `ROLE_ADMIN`

This endpoint requires the user to have an admin role to access it.

### 6. **Swagger UI (Optional)**

You can access the Swagger UI at `http://localhost:8080/swagger-ui` to visualize and interact with the API endpoints.

## Cookie Handling

- JWT is stored in a **secure HTTP-only cookie** to prevent client-side access.
- The cookie is automatically sent with each request once the user logs in.
- The cookie is set to expire after **24 hours** (`maxAge(24 * 60 * 60)`).

## Security Considerations

- **HTTP-Only Cookies**: Prevents JavaScript from accessing the cookie, reducing the risk of XSS attacks.
- **Secure Cookies**: Ensures the cookie is only sent over HTTPS (disabling in development mode).
- **Role-based Access Control (RBAC)**: Only users with appropriate roles can access certain endpoints.

## Running in Production

For production environments, make sure to:

1. Use **HTTPS** to ensure that cookies are sent securely.
2. Set `secure=true` for cookies.
3. Configure a real database (e.g., MySQL or PostgreSQL) instead of using H2.

## Conclusion

This project demonstrates how to implement authentication and authorization using JWT and cookies in a Spring Boot application. It also showcases how to use Spring Security to enforce role-based access control, providing a secure way to protect sensitive resources.

---
