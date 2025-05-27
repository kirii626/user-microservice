# 🧑‍💼 User Microservice

A secure, modular microservice built with Spring Boot for managing users and handling authentication via JWT. Designed to integrate seamlessly into a distributed microservice architecture.

---

## 📚 Table of Contents

- [Overview](##📝 Overview)
- [Features](#Features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
  - [Configure the Database](#configure-the-database)
  - [Running the Application](#running-the-application)
- [Configuration](#configuration)
- [Database](#database)
- [Security](#security)
- [Project Structure](#project-structure)

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

Controller Layer → Service Layer → Repository Layer → Database
↓ ↓ ↓
DTOs Business Logic Spring Data JPA
↓
Security Layer (JWT + BCrypt)


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

1. Clone the repository:

        git clone https://github.com/your-org/user-microservice.git
        cd user-microservice
   
## ⚙️ Configure the Database

### 🔧 Step 2: Update Database Credentials

Edit the following file to configure your database connection while developing using H2 DB:

📄 `src/main/resources/application-dev.properties`

Example:

    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    spring.datasource.url=jdbc:h2:mem:testdb

# JWT configuration
    jwt.secret=your_jwt_secret_key
    jwt.expiration=3600000  # in milliseconds

▶️ Running the Application
Run using Maven:

    ./mvnw spring-boot:run

or

    mvn spring-boot:run
    
The service will be available by default at:

    http://localhost:8080
    
but you can configure it at application.properties file using this line at application.properties: 

    server.port=8085
##📁 Project Structure

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

