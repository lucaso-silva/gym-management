# Gym Management System API

A backend API that simulates a real-world gym management system, built to demonstrate Clean Architecture, DDD principles, and strong domain modeling.

## 1. Highlights
This project demonstrates:
- Clean Architecture (Ports & Adapters / Hexagonal)
- Domain-driven design concepts
- Business rule encapsulation inside the domain
- Proper separation of concerns
- Persistence abstraction
- Non-anemic domain models
- Use of Flyway for database versioning and Docker for environment consistency

The focus is on behavior and business rules, not database-driven development.

## 2. Project Status
This project is actively evolving and currently includes:
- Core user management features
- Clean Architecture implementation
- Automated database migrations (Flyway)
- Unit and controller tests (MockMvc)
- Dockerized environment


## 3. Domain Overview
The system currently models three user roles:
- Student – gym member
- Instructor – training professional
- Manager – responsible for gym operations

All roles extend a base User aggregate root and enforce their own invariants.

### Business Rules
- Email and login must be unique
- Email format validation
- Student must be at least 16 years old
- Birth date cannot be in the future
- Instructor must provide CREF and specialty
- Manager must provide a gym name
- Users cannot be modified bypassing domain methods
- Students with active membership cannot be deleted

**The domain layer contains:**
- Entities
- Value Objects
- Domain exceptions
- Behavioral update methods (no public setters)

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
- Domain has zero framework dependencies
- Controllers depend on use case interfaces
- Infrastructure implements outbound ports
- Persistence can be replaced without affecting business logic
- Clear separation between domain and JPA entities

## 5. Implemented Features
**User Management**
- Create user (Student, Instructor, Manager)
- List users (optional name filter)
- Get user by ID
- Get user by login
- Partial update (PATCH)
- Delete user (with business validation)

## 6. Persistence

- PostgreSQL
- Spring Data JPA
- Flyway for database versioning
- Single table strategy with user type discriminator
- Explicit mapping between the domain model and JPA entities

## 7. Database & Migrations
This project uses Flyway for database version control.

### Migration Files
**Located at:**
```
src/main/resources/db/migration
```

### How it works
- Migrations run automatically on application startup
- Database schema is versioned and tracked
- Prevents inconsistencies between environments

> The project does not rely on `ddl-auto=create`. All schema changes are managed explicitly via migrations.

## 8. API Routes

`/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create user |
| GET | `/users` | List users (optional ?name= filter) |
| GET | `/users/{id}` | Get user by ID |
| PATCH | `/users/{id}` | Partial update |
| DELETE | `/users/{id}` | Delete user |

## 9. Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Flyway
- Docker and Docker Compose
- H2 (tests)
- JUnit and MockMvc
- Lombok
- Jakarta Validation

## 10. Testing
The project includes automated tests to ensure the correctness of business logic and API behavior.

### Test Types
- Unit tests for Use Cases (business logic)
- Controller tests using MockMvc

### Technologies
- JUnit
- MockMvc
- H2 in-memory database

### Running tests

```bash
mvn test
```
## 11. Docker
The application is fully containerized using Docker.

### Setup includes:
- Dockerfile for the Spring Boot application
- Docker Compose for orchestrating:
  - Application container
  - PostgreSQL database

### Benefits
- Consistent environment across machines
- Easy setup for new developers
- Closer to production environment

## 12. Running the Application
**Prerequisites**
- Docker
- Docker Compose

### Start the application
```bash
docker-compose up --build
```

**What happens**
- PostgreSQL container starts
- Application container starts
- Flyway runs migrations automatically
- Application connects to the database

**API available at:**
```
http://localhost:8080
```

### Stop containers
```bash
docker-compose down
```

## 13. Next Steps
**Domain Expansion**
- Add Gym aggregate
- Add GymClass aggregate
- Implement student enrollment
- Instructor assignment rules
- Membership lifecycle management

**Improvements**
- Password hashing
- Implement authentication and authorization
- Soft delete
- Audit fields
- API documentation (OpenAPI / Swagger)
- Add Testcontainers for real database integration tests

## 14. Key Takeaway
This project demonstrates the ability to:
- Design maintainable backend systems
- Model business rules correctly
- Apply Clean Architecture in practice
- Separate business logic from frameworks

---
🧑🏻‍💻 Developed by [Lucas Oliveira](https://www.linkedin.com/in/lucas-oliveira10/)

