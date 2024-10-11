# Church Management System

## Overview

The Church Management System is a robust application built with Spring Boot, PostgreSQL, and MinIO, designed to streamline the management of church activities and operations. This project includes features for managing member information, event scheduling, donations, and more, while ensuring data integrity and security.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **PostgreSQL**: Relational database for storing data.
- **MinIO**: Object storage for managing files and media uploads.
- **Java**: Programming language used for backend development.
- **Maven**: Build automation tool for Java projects.

## Features

- **Multi-Environment Configuration**: Support for multiple environments (QA and PROD) with customizable configurations.
- **Custom Exception Handling**: Implements a custom exception handling mechanism through `CustomRequestException` to manage application errors gracefully.
- **User Management**: Manage church members and their information.
- **Event Management**: Schedule and manage church events and activities.
- **Donation Management**: Record and track donations made by members.

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17 or higher
- Maven
- PostgreSQL
- MinIO

### Configuration

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/church-management-system.git
   cd church-management-system

2. Go to into file application.yml and then :
- change url, port, and database name :
>spring.datasource.url=jdbc:postgresql://localhost:5432/db_hkbpCMS

- change username :
>spring.datasource.username=postgres

- change password :
>spring.datasource.password=HKBP_CMS

3. Go to terminal and run :
```bash
# Install dependency
$ mvn install

# run spring boot
$ mvn spring-boot:run
```

4. Run minio server:
```bash
# Install dependency
$ --console-address :9001
```

## Reference Documentation
### The following links provide useful reference material for the technologies used in this project:
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/maven-plugin/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [PostgreSQL documentation](https://www.postgresql.org/docs/14/index.html)
* [MinIO Documentation for Java Spring boot](https://min.io/docs/minio/linux/developers/java/API.html)

## Rest API Documentation 
// soon