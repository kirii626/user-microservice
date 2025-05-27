# 🧑‍💼 User Microservice

A secure, modular microservice built with Spring Boot for managing users and handling authentication via JWT. Designed to integrate seamlessly into a distributed microservice architecture.

---

## 📚 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
  - [Prerequisites](#-prerequisites)
  - [Setup](#%EF%B8%8F-setup)
  - [Configure the Database](#%EF%B8%8F-configure-the-database)
  - [Eureka server configuration](#%EF%B8%8F-eureka-server-configuration)
  - [Redis cache configuration](#%EF%B8%8F-redis-cache-configuration)
  - [Running the Application](#%EF%B8%8F-running-the-application)
- [Security](#-security)
  - [How works JWT in this project?](#-how-works-JWT-in-this-project)
  - [Authentication Flow](#-authentication-flow)
  - [Token Structure](#-token-tructure)
- [Project Structure](#-project-structure)

---

## 📝 Overview

This microservice provides RESTful endpoints to manage user registration, authentication, and data access. It uses JWT for stateless authentication and adheres to clean architectural practices.

---

## ✨ Features

- ✅ User registration and login
- 🔐 JWT-based authentication
- 🧂 BCrypt password hashing
- 🛡 Role-based access (admin endpoints)
- 📡 RESTful API design
- 🔄 DTOs for clean request/response formatting

---

## 🏗 Architecture

This is the complete architecture which user-microservice formes part

![Untitled Diagram drawio](https://github.com/user-attachments/assets/64d6f647-3342-4655-b840-3784560d0c3b)

These are the elements of user-microservice: 

- **Controller Layer:** Manages incoming HTTP requests and responses
- **Service Layer:** Core business logic (`AuthService`, etc.)
- **Repository Layer:** Data persistence and database operations
- **DTOs:** Decouple internal models from external requests
- **Security:** Stateless JWT-based security with encrypted passwords

---

## ⚙️ Tech Stack

| Component       | Technology       |
|----------------|------------------|
| Language        | Java 21+         |
| Framework       | Spring Boot      |
| Build Tool      | Maven            |
| ORM             | Spring Data JPA  |
| Database        | PostgreSQL / MySQL / H2 |
| Authentication  | JWT              |
| Testing         | JUnit, Mockito   |

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 21+
- Maven 3.8+
- (Optional) Docker (for local database setup)

### ⚙️ Setup 

🔧 Clone the repository:

        git clone https://github.com/your-org/user-microservice.git
        cd user-microservice
        
### ⚙️ General Configurations

🔧  You'll find the general configurations at applications.properties defined using environment variables like this: 

    jwt.expiration=${JWT_EXPIRATION}
    jwt.secret=${JWT_SECRET}
    
    spring.cache.type=redis
    spring.redis.host={SPRING_REDIS_HOST}
    spring.redis.port={REDIS_PORT}

so you need to define them on your IDE, using a .env file or just putting the configurations directly on the .properties file instead(this is the most unsafe option).
   
### ⚙️ Configure the Database

🔧 Update Database Credentials

#### For developing 

Edit the following file to configure your H2 database connection while developing:

📄 `src/main/resources/application-dev.properties`

Example:

    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    spring.datasource.url=jdbc:h2:mem:testdb

#### For postgres using Docker/Podman

📄 `src/main/resources/application-docker.properties`

Example: 

    spring.datasource.username=your_user
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

### ⚙️ Eureka server configuration

📄 `src/main/resources/application.properties`

Example: 

    eureka.client.service-url.defaultZone=http://localhost:your_port/eureka/
    eureka.client.register-with-eureka=true
    eureka.client.fetch-registry=true 
    eureka.instance.prefer-ip-address=true

### ⚙️ JWT configuration

Example: 

    jwt.secret=your_jwt_secret_key
    jwt.expiration=3600000  # in milliseconds

### ⚙️ Redis cache configuration

Example:

    spring.cache.type=redis
    spring.redis.host=localhost
    spring.redis.port=your_port

### ▶️ Running the Application 

Run using Maven:

    ./mvnw spring-boot:run

or

    mvn spring-boot:run
    
The service will be available by default at:

    http://localhost:8080
    
but you can configure it at application.properties file using this line at application.properties: 

    server.port=8085

## Security
### How works JWT in this project? 
This project uses JSON Web Tokens (JWT) for stateless, secure authentication and authorization across its REST API.

---

### 🔑 Authentication Flow

When a user logs in via:

    POST /api/auth/log-in

- Their credentials are validated by the backend.
- If valid, a JWT is generated and returned in the response.
- The token contains encoded user information and is cryptographically signed.

---

### 📦 Token Structure

- The JWT consists of three parts: `header.payload.signature`
- It includes user identification data, such as the user's ID or email, and the role of the user for allow access to resources.
- It is signed using a secret key configured at application.properties:

      jwt.secret=your_jwt_secret
      jwt.expiration=3600000

## 📁 Project Structure

    src/
     └── main/
         ├── java/com/accenture/user_microservice/
         │    ├── controllers/        # REST controllers
         │    ├── services/           # Business logic
         │    │    └── AuthService.java
         │    ├── dtos/               # Data Transfer Objects
         │    │    ├── input/
         │    │    │    └── UserDtoInput.java
         │    │    └── output/
         │    │         └── UserDtoOutput.java
         │    ├── entities/           # JPA Entities
         │    ├── repositories/       # Spring Data JPA Repositories
         │    └── security/           # JWT, filters, config
         └── resources/
              └── application.properties

