# DoConnect Application

## Project Overview

DoConnect is a Question and Answer (Q&A) application where technical questions are asked and answered. The project has been assigned by the Great Learning team as a final capstone project, with Great Learning serving as the training partner assigned by HCL.

### Problem Statement
	
The DoConnect application has two primary users:

1. **User**:  
   - Can register, log in, and log out of the application.  
   - Can ask any kind of questions, search for existing ones, and answer them.  
   - Can answer multiple questions and the same question multiple times.  
   - Can chat with other users, like and comment on answers.  


2. **Admin**:  
   - Can register, log in, and log out.  
   - Can perform CRUD operations on users and questions.  
   - Receives notifications when new questions are asked or answered.  
   - Can approve or delete questions and answers, making content visible only when approved.
   - Can close discussion threads or posts and update them as resolved.

### Backend Features:

- **Microservices Architecture**: The application uses separate microservices for users and admins, ensuring separation of concerns.
- **Inter-service Communication**: Microservices are registered with Eureka Server to communicate seamlessly.
- **Database**: MySQL is used to manage storage requirements.
- **Logging**: Implemented logging strategies allow for monitoring application activity and ensuring smooth operation.
- **API Testing**: Swagger provides comprehensive documentation and testing.
- **Unit Testing**: JUnit is used to implement unit tests for comprehensive testing.
- **Code Quality**: SonarQube is used to analyze and improve code quality.

## Technology Stack

1. **Java**: Primary language for implementing the project. [Java](https://www.java.com)
2. **Spring Boot**: Main framework for microservices. [Spring Boot](https://spring.io/projects/spring-boot)
3. **Maven**: For dependency management and project building. [Maven](https://maven.apache.org)
4. **JWT**: For secure authentication. [JWT](https://jwt.io)
5. **Swagger**: For API documentation and testing. [Swagger](https://swagger.io)
6. **MySQL**: Database for storage. [MySQL](https://www.mysql.com)
7. **Hibernate**: For Object-Relational Mapping (ORM). [Hibernate](https://hibernate.org)
8. **JUnit**: For unit testing. [JUnit](https://junit.org/junit5/)
9. **SonarQube**: For code quality analysis. [SonarQube](https://www.sonarsource.com/)

## Ports and Access URLs

1. **Admin Service:**  
   Running on port `8085`.  
   Access [Swagger UI](http://localhost:8085/swagger-ui/index.html).

2. **User Service:**  
   Running on port `8090`.  
   Access [Swagger UI](http://localhost:8090/swagger-ui/index.html).

3. **Eureka Server:**  
   Running on port `8761`.  
   Access [http://localhost:8761](http://localhost:8761).

## Installation & Setup

1. **Download the repository:**

   - Download the repository as a zip file.

2. **Extract the contents:**

   - Unzip the file to a desired directory.

3. **Open in Eclipse:**

   - Launch Eclipse IDE.
   - Choose "File" > "Open Projects from File System."
   - Select the extracted directory.

4. **MySQL Setup:**

   - **Download and add the MySQL JDBC driver:** [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).
   - **Create a database:** Create a new database named `DoConnect`.
   - **Configure the database connection:** Update the application's configuration file with your MySQL username and password.

5. **Build and Run:**

   - Use Eclipse's build and run functionalities to start the application.

## Future Improvements

1. **Database Integration**: Ensure smooth migration to production and consistency.
2. **API Rate Limiting**: Introduce rate-limiting to prevent spamming.
3. **MVC Architecture**: Convert the project into an MVC architecture for streamlined design.
