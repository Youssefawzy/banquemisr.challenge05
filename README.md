# **Task Mangement System**
## **Table of Contents**

- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)


---

## **About the Project**

A Task Management System built with Spring Boot, leveraging JPA for data persistence, Spring Security for role-based access control, and RESTful APIs for seamless integration

## **Features**

- **User authentication and role-based access control** using Spring Security.
- **JWT** for secure authorization and authentication.
- **CRUD operations** for task content management.
- **Data input validation** to ensure data integrity.
- **Search and filtering** capabilities for efficient task management.
- **Integration with a relational database** using JPA for data persistence.
- **Exception handling** with customizable error responses.
- **Command-line initialization** for roles and users setup.
- **RESTful APIs** for easy integration with third-party services.
- **Email notifications** for task updates and user actions.


## **Technologies Used**

- **Java 17+**
- **Spring Boot 3.x** (Spring Data JPA, Spring Security, etc.)
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Other Tools**: Lombok

## **Getting Started**

### Prerequisites

1. **Java Development Kit (JDK)**: Version 17 or higher.
2. **Maven/Gradle**: For building the project.
3. **Database**: MySQL or PostgreSQL.
4. **Git**: To clone the repository.

### Installation

1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
2. Configure the database connection:
    ```bash
    echo "spring.datasource.url=jdbc:mysql://localhost:3306/your_database" >> src/main/resources/application.properties
    echo "spring.datasource.username=your_username" >> src/main/resources/application.properties
    echo "spring.datasource.password=your_password" >> src/main/resources/application.properties
3. Build the project using Maven:
    ```bash
    mvn clean install
4. Run the application:
    ```bash
     mvn spring-boot:run

5. Once the application is running, it will be accessible at:
   mvn spring-boot:run

  Once the application is running, it will be accessible at http://localhost:8080.

## Configuration

To run the application, ensure the following configurations are set in your `application.properties` file:

### Application Properties
    spring.application.name=Task Management System
    
    spring.datasource.url=jdbc:postgresql://<HOST>:<PORT>/<DATABASE_NAME>
    spring.datasource.username=<DATABASE_USERNAME>
    spring.datasource.password=<DATABASE_PASSWORD>
    spring.datasource.driver-class-name=org.postgresql.Driver
    
    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    
    api.prefix=/api/v1
    
    auth.token.expirationInMils=<TOKEN_EXPIRATION_IN_MILLISECONDS>
    auth.token.jwtSecret=<JWT_SECRET_KEY>
    
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=<EMAIL_ADDRESS>
    spring.mail.password=<EMAIL_PASSWORD>
    spring.mail.protocol=smtp
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true


### Usage
  Access API:
    # API Endpoints

## Task Management

### Get Task By ID
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/{id}`

### Get Upcoming Tasks
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/Notify-Upcoming-Tasks?fromDate={fromDate}&toDate={toDate}`

### Search Tasks by Title
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/search/title?title={title}`

### Search Tasks by Status
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/search/status?status={status}`

### Search Tasks by Description
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/search/description?description={description}`

### Search Tasks by Due Date
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/search/due-date?dueDate={dueDate}`

### Update Task By ID
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/v1/tasks/{id}`

### Delete Task By ID
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/v1/tasks/{id}`

### Get All Tasks
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks`

### Create Task
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/v1/tasks`

### Mark Task as Complete
- **Method:** `PATCH`
- **URL:** `http://localhost:8080/api/v1/tasks/{id}/complete`

### Mark Task as In Progress
- **Method:** `PATCH`
- **URL:** `http://localhost:8080/api/v1/tasks/{id}/in_progress`

## History

### Create History
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/v1/history/{id}`

### Get History By ID
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/history/{id}`

### Get Task History
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/tasks/history/{taskId}`

### Update History By ID
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/v1/history/{id}`

### Delete History By ID
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/v1/history/{id}`

## Notifications

### Create Notification
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/notifications/task/{taskId}`

### Get Notification By ID
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/notifications/{id}`

### Get Task Notifications
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/notifications/task/{taskId}`

### Update Notification
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/notifications/{id}`

### Delete Notification
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/notifications/{id}`

## User Management

### Create User
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/v1/users/add`

### Sign Up
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/v1/users/sign-up`

### Update User
- **Method:** `PUT`
- **URL:** `http://localhost:8080/api/v1/users/{id}`

### Delete User
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/api/v1/users/{id}`

### Get User By ID
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/users/{id}`

### Login
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/v1/auth/login`





