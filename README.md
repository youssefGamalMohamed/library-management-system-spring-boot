
# Library Management System API

This project aims to develop a robust Library Management System API using Spring Boot. The system facilitates librarians in efficiently managing books, patrons, and borrowing records.

## Features

- Manage books, patrons, and borrowing records
- RESTful API endpoints
- CRUD operations
- Validation and error handling
- Unit and integration tests
- Caching with Spring Boot (type: simple)

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Log4j2
- Logbook for Http Request & Response Logging
- Spring Validation
- OpenAPI Documentation

## Getting Started

### Prerequisites

- Java 21
- MySQL
- Gradle

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/youssefGamalMohamed/library-management-system-spring-boot.git
   ```
2. Navigate to the project directory:
   ```bash
   cd library-management-system-spring-boot
   ```
3. Configure the database connection and other settings in `src/main/resources/application.yml`:
   ```yaml
   server:
     port: 8081
     servlet:
       context-path: /api

   spring:
     application:
       name: library-management-system-spring-boot
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/library_management_system_db
       username: root
       password: 1234
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
     cache:
       type: simple

   logging:
     level:
       org:
         springframework:
           cache: trace
         zalando:
           logbook: trace

   springdoc:
     swagger-ui:
       path: /swagger-ui.html
   ```

### Running the Application

1. Build the project:
   ```bash
   ./gradlew build
   ```
2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

### Testing

Run the tests using:
```bash
./gradlew test
```

## API Documentation

API endpoints and documentation can be accessed at:
```
http://localhost:8081/api/swagger-ui.html
```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any changes or suggestions.
