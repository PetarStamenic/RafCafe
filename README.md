# RafCafe

Distributed backend system for a digital cafe shop built with Spring Boot microservices.

## Architecture

The system consists of three backend microservices:

- **User Service** (port 8081) - Authentication, JWT tokens, user profiles, loyalty points
- **Menu & Order Service** (port 8082) - Menu CRUD, ordering, order management
- **Notification Service** (port 8083) - Email notifications, notification archive

## Tech Stack

- Java 25, Spring Boot 3.4.4
- Spring Data JPA, H2 (dev), PostgreSQL (prod)
- Spring Security + JWT (HMAC-SHA384)
- SpringDoc OpenAPI / Swagger UI
- JUnit 5, Mockito, JaCoCo
- Maven (multi-module)

## Getting Started

### Prerequisites

- JDK 25+
- Maven 3.8+

### Build & Run

```bash
# Build all modules
mvn clean package

# Run services individually
java -jar user-service/target/user-service-0.0.1-SNAPSHOT.jar
java -jar menu-order-service/target/menu-order-service-0.0.1-SNAPSHOT.jar
java -jar notification-service/target/notification-service-0.0.1-SNAPSHOT.jar
```

### Run Tests

```bash
mvn clean verify
```

## API Documentation

Once services are running, Swagger UI is available at:

- User Service: `http://localhost:8081/swagger-ui.html`
- Menu & Order Service: `http://localhost:8082/swagger-ui.html`
- Notification Service: `http://localhost:8083/swagger-ui.html`

## API Testing

Postman collection is located in `postman/RafCafe.postman_collection.json`.

Run with Newman:

```bash
npm install -g newman
newman run postman/RafCafe.postman_collection.json
```

## Project Structure

```
RafCafe/
├── user-service/           # Auth & Profile microservice
├── menu-order-service/     # Menu & Orders microservice
├── notification-service/   # Notification microservice
├── postman/                # Postman/Newman test collection
├── .github/workflows/      # CI pipeline
└── pom.xml                 # Parent POM
```
