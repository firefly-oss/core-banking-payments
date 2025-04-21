# Core Banking Payments Service

## Overview

The Core Banking Payments Service is a microservice component of the Firefly platform that handles various types of banking payment operations. It provides a comprehensive API for managing payment orders, processing different payment types, and handling payment-related operations in a banking environment.

## Features

- **Payment Order Management**: Create, retrieve, update, and delete payment orders
- **Multiple Payment Types Support**:
  - SEPA (Single Euro Payments Area) payments
    - SCT (SEPA Credit Transfer)
    - Direct Debit
    - ICT (Instant Credit Transfer)
  - SWIFT international payments
  - Payroll payments
  - Internal transfers
- **Payment Scheduling**: Schedule one-time or recurring payments
- **Payment Status Tracking**: Monitor the status of payments throughout their lifecycle
- **Fee Management**: Configure and apply fees to different payment types
- **Payment Method Management**: Configure and manage different payment methods
- **Provider Integration**: Connect with various payment providers and networks

## Architecture

The service follows a modular architecture with the following components:

1. **core-banking-payments-interfaces**: Contains DTOs (Data Transfer Objects), interfaces, and enums that define the API contract
2. **core-banking-payments-models**: Contains database entities and repositories
3. **core-banking-payments-core**: Contains the business logic and service implementations
4. **core-banking-payments-web**: Contains REST controllers and web-related configurations

## Technical Stack

- **Java 21**: Core programming language
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data access layer
- **Maven**: Dependency management and build tool
- **Docker**: Containerization
- **Swagger/OpenAPI**: API documentation
- **Lombok**: Reduces boilerplate code
- **Jackson**: JSON serialization/deserialization

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21
- Maven 3.8+
- Docker (for containerized deployment)

### Building the Service

```bash
mvn clean install
```

### Running Locally

```bash
mvn spring-boot:run -pl core-banking-payments-web
```

### Running with Docker

```bash
# Build the Docker image
docker build -t core-banking-payments .

# Run the container
docker run -p 8080:8080 core-banking-payments
```

## API Documentation

When the service is running, the API documentation is available at:

```
http://localhost:8080/swagger-ui.html
```

### Key API Endpoints

The service exposes several REST endpoints organized by payment type and management function:

#### Payment Order Management

- `GET /api/v1/manager/payment-orders`: List payment orders
- `GET /api/v1/manager/payment-orders/{id}`: Get a specific payment order
- `POST /api/v1/manager/payment-orders`: Create a new payment order
- `PUT /api/v1/manager/payment-orders/{id}`: Update a payment order
- `DELETE /api/v1/manager/payment-orders/{id}`: Delete a payment order

#### SEPA Payments

- `POST /api/v1/payments/sepa/sct`: Process a SEPA Credit Transfer
- `POST /api/v1/payments/sepa/dd`: Process a SEPA Direct Debit
- `POST /api/v1/payments/sepa/ict`: Process a SEPA Instant Credit Transfer

#### SWIFT Payments

- `POST /api/v1/payments/swift`: Process a SWIFT international payment

#### Payroll Payments

- `POST /api/v1/payments/payroll`: Process a payroll payment

## Development Guidelines

### Code Structure

- Follow the package structure based on domain concepts
- Use DTOs for API request/response objects
- Implement business logic in service classes
- Use repositories for data access

### Coding Standards

- Follow Java coding conventions
- Use meaningful variable and method names
- Write comprehensive JavaDoc comments
- Include unit tests for all new functionality

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PaymentOrderServiceTest
```

## Deployment

The service can be deployed as a standalone Spring Boot application or as a Docker container. It exposes port 8080 for HTTP traffic.

### Environment Variables

The following environment variables can be configured:

- `SPRING_PROFILES_ACTIVE`: Active Spring profile (dev, test, prod)
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request