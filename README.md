# Gym Management System API

A backend API that simulates a real-world gym management system, built to demonstrate Clean Architecture, DDD principles, and strong domain modeling.

## 1. Highlights

This project demonstrates:
* Clean Architecture (Ports & Adapters / Hexagonal)
* Domain-Driven Design principles
* Rich domain models and business rule encapsulation
* Aggregate boundaries and cross-aggregate validation
* Global exception handling with ProblemDetail
* Persistence abstraction
* Flyway database migrations
* Dockerized environment
* Unit and integration testing
* JaCoCo code coverage

The focus is on behavior and business rules rather than database-driven development.

## 2. Project Status

**This project is actively evolving** and currently includes:
* User management
* Gym management
* Gym class management
* Student enrollment flows
* Cross-aggregate validation through gateways
* Global exception handling
* Automated database migrations with Flyway
* Unit and integration tests
* Dockerized environment

## 3. Domain Overview

The system currently models three aggregates:

### User

Supports **three roles**:

* Student
* Instructor
* Manager

**Business rules include:**

* Email and login uniqueness
* Email format validation
* Students must be at least 16 years old
* Birth date cannot be in the future
* Instructor must provide CREF and specialty
* Students with active membership cannot be deleted

### Gym

Represents a physical gym and manages:

* Members
* Manager assignment
* Address information

**Business rules include:**

* Duplicate members are not allowed
* Members must exist before being added
* Gyms with active members cannot be deleted

### GymClass

Represents classes offered by a gym.

**Business rules include:**

* Classes belong to a gym
* Instructors must belong to the gym
* Only active students can enroll
* Duplicate enrollments are not allowed
* Capacity constraints are enforced
* Capacity cannot be lower than enrolled students
* Classes with enrolled students cannot be deleted

---

### The domain layer contains:

* Entities
* Value Objects
* Domain exceptions
* Behavioral methods (no public setters)


## 4. Architecture

This project follows:

    Clean Architecture (Ports & Adapters / Hexagonal)

### Layered Structure
```
application
 ├── domain
 ├── use cases (services)
 └── ports (inbound / outbound)

infrastructure
 ├── REST controllers (inbound)
 └── JPA persistence adapters (outbound)
 ```

**Architectural Highlights**
* Domain has zero framework dependencies
* Controllers depend on use case interfaces
* Infrastructure implements outbound ports
* Persistence can be replaced without affecting business logic
* Clear separation between domain and JPA entities

## 5. Implemented Features

### User Management

* Create user
* List users
* Get user by ID
* Get user by login
* Partial update
* Delete user with business validations

### Gym Management

* Create gym
* List gyms
* Get gym by ID
* Update gym
* Delete gym
* Add member
* Remove member

### GymClass Management

* Create gym class
* List gym classes
* Get gym class by ID
* Update gym class
* Delete gym class
* Enroll student
* Unenroll student

### Validation and Error Handling

* Global exception handling
* Module-specific exceptions
* ProblemDetail responses
* Validation error responses
* Invalid UUID and malformed request handling

## 6. Persistence

* PostgreSQL
* Spring Data JPA
* Flyway for database versioning
* Single table strategy with user type discriminator
* Explicit mapping between the domain model and JPA entities

## 7. Database & Migrations
This project uses Flyway for database version control.

### Migration Files
**Located at:**
```
src/main/resources/db/migration
```

### How it works
* Migrations run automatically on application startup
* Database schema is versioned and tracked
* Prevents inconsistencies between environments

> The project does not rely on `ddl-auto=create`. All schema changes are managed explicitly via migrations.

## 8. API Routes

### Users

**Base path:** `/api/users`

| Method | Endpoint  | Description                           |
|--------|-----------|---------------------------------------|
| POST   | `/`       | Create a user                         |
| GET    | `/`       | List users (optional `?name=` filter) |
| GET    | `/{id}`   | Get user by ID                        |
| PATCH  | `/{id}`   | Partially update a user               |
| DELETE | `/{id}`   | Delete a user                         |

### Gyms

**Base path:** `/api/gyms`

| Method | Endpoint                      | Description                |
|--------|-------------------------------|----------------------------|
| POST   | `/`                           | Create a gym               |
| GET    | `/`                           | List gyms                  |
| GET    | `/{gymId}`                    | Get gym by ID              |
| PATCH  | `/{gymId}`                    | Partially update a gym     |
| DELETE | `/{gymId}`                    | Delete a gym               |
| POST   | `/{gymId}/members`            | Add a member to a gym      |
| DELETE | `/{gymId}/members/{memberId}` | Remove a member from a gym |

### Gym Classes

**Base path:** `/api/gym-classes`

| Method | Endpoint                              | Description                  |
|--------|---------------------------------------|------------------------------|
| POST   | `/`                                   | Create a gym class           |
| GET    | `/`                                   | List gym classes             |
| GET    | `/{gymClassId}`                       | Get gym class by ID          |
| PATCH  | `/{gymClassId}`                       | Partially update a gym class |
| DELETE | `/{gymClassId}`                       | Delete a gym class           |
| POST   | `/{gymClassId}/students`              | Enroll a student             |
| DELETE | `/{gymClassId}/students/{studentId}`  | Unenroll a student           |

## 9. Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* Docker and Docker Compose
* H2 (tests)
* JUnit and MockMvc
* Lombok
* Jakarta Validation

## 10. Testing

The project includes automated tests to ensure business logic correctness and API behavior.

### Test Types

* Unit tests for use cases
* Integration tests for REST flows

### Covered Modules

* User
* Gym
* GymClass

### Technologies

* JUnit
* MockMvc
* H2 in-memory database
* JaCoCo

### Running tests

```bash
mvn test
```
## 11. Docker

The application is fully containerized using Docker.

### Setup includes:

* Dockerfile for the Spring Boot application
* Docker Compose for orchestrating:
  * Application container
  * PostgreSQL database

### Benefits

* Consistent environment across machines
* Easy setup for new developers
* Closer to production environment

## 12. Running the Application

**Prerequisites**
* Docker
* Docker Compose

### Start the application

```bash
docker-compose up --build
```

**What happens**
* PostgreSQL container starts
* Application container starts
* Flyway runs migrations automatically
* Application connects to the database

**API available at:**
```
http://localhost:8080
```

### Stop containers

```bash
docker-compose down
```

## 13. Next Steps

### Security

* JWT authentication
* Role-based authorization

### Improvements

* Password hashing
* API documentation (OpenAPI / Swagger)
* Testcontainers
* Soft delete
* Audit fields

## 14. Key Takeaway

This project demonstrates the ability to:
* Design maintainable backend systems
* Model business rules correctly
* Apply Clean Architecture in practice
* Separate business logic from frameworks

---
🧑🏻‍💻 Developed by [Lucas Oliveira](https://www.linkedin.com/in/lucas-oliveira10/)

