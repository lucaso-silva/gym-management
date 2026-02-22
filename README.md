# Gym Management System API

Backend API designed to simulate a real-world gym management system, focusing on domain modeling, business rules, and layered architecture rather than a simple CRUD application.

> ⚠️ This project is currently under active development. Some features and endpoints are still being implemented and may change.

---

## Purpose

The goal of this project is to practice backend engineering concepts commonly found in production systems, including domain validation, application services, and separation of responsibilities.

Instead of centering the application around controllers and database entities, the system is built around **domain behavior** and **use cases**.

The project explores how business rules should live inside the domain layer while infrastructure frameworks (Spring/JPA) remain replaceable.

## Domain Overview

The system models a gym environment with different types of users:

- **Student** – gym member
- **Instructor** – professional responsible for training activities
- **Manager** – manages the gym

All user types inherit from a base `User` entity and contain their own specific attributes and rules.

The domain layer contains:
- Entities
- Value Objects
- Domain validations
- Behavioral methods (not anemic models)

Example responsibilities handled by the domain:
- Data validation inside entities
- Controlled updates through commands
- Encapsulation of business invariants
- Polymorphic user behavior

## Architecture

This project follows **Clean Architecture with Ports and Adapters (Hexagonal Architecture)**.

The main idea is to isolate business rules from frameworks and persistence technologies.
