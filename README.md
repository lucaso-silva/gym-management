# Gym Management System API

A backend API that simulates a real-world gym management system, built to demonstrate Clean Architecture, DDD principles, and strong domain modeling.

## Highlights
This project demonstrates:
- Clean Architecture (Ports & Adapters / Hexagonal)
- Domain-driven design concepts
- Business rule encapsulation inside the domain
- Proper separation of concerns
- Persistence abstraction
- Non-anemic domain models

The focus is on behavior and business rules, not database-driven development.

## Domain Overview
The system currently models three user roles:
- Student – gym member
- Instructor – training professional
- Manager – responsible for gym operations

All roles extend a base User aggregate root and enforce their own invariants.

### **Business Rules**
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

## Architecture
This project follows:

    Clean Architecture (Ports & Adapters / Hexagonal)

### **Layered Structure**
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

## Implemented Features
**User Management**
- Create user (Student, Instructor, Manager)
- List users (optional name filter)
- Get user by ID
- Get user by login
- Partial update (PATCH)
- Delete user (with business validation)

## Persistence

- PostgreSQL
- Spring Data JPA
- Single table strategy with user type discriminator
- Explicit mapping between domain model and JPA entities

## API Routes

`/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users` | Create user |
| GET | `/users` | List users (optional ?name= filter) |
| GET | `/users/{id}` | Get user by ID |
| PATCH | `/users/{id}` | Partial update |
| DELETE | `/users/{id}` | Delete user |

## Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Lombok
- Jakarta Validation

## Next Steps
**Domain Expansion**
- Add Gym aggregate
- Add GymClass aggregate
- Implement student enrollment
- Instructor assignment rules
- Membership lifecycle management

**Improvements**
- Unit tests for domain layer
- Integration tests
- Password hashing
- Soft delete
- Audit fields
- OpenAPI documentation
- Docker support

## Key Takeaway
This project demonstrates the ability to:
- Design maintainable backend systems
- Model business rules correctly
- Apply Clean Architecture in practice
- Separate business logic from frameworks

---
🧑🏻‍💻 Developed by [Lucas Oliveira](https://www.linkedin.com/in/lucas-oliveira10/)

