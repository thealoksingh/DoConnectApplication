
# DoConnect Application

## Project Overview

DoConnect is a Question and Answer (Q&A) application where technical questions are asked and answered. The application is built on a microservices architecture using Spring Boot, and integrates key technologies to ensure functionality and security.

## Features

### User Features:

1. **Authentication**: Users can register, log in, and log out using JWT authentication.
2. **CRUD Questions**: Users can ask questions on any topic, search for existing questions, and answer them.
3. **Engagement**: Users can like and comment on answers, as well as upload images for reference.

### Admin Features:

1. **CRUD Operations**: Admins can manage users and questions, including approving or deleting content.
2. **Monitoring**: Admins can mark discussion threads as resolved and monitor mail for new questions or answers.

### Backend Features:

1. **Microservices Architecture**: The application uses separate microservices for user and owner functionalities, ensuring separation of concerns.
2. **Inter-service Communication**: Microservices communicate via Eureka Server and use Spring Cloud for configuration sharing.
3. **Database**: Each microservice uses its own H2 database, ensuring independence.
4. **API Testing**: Swagger and Mockito are used for comprehensive testing and documentation.

## Technology Stack

1. **Spring Boot**: Main framework for microservices.
2. **JWT**: For secure authentication.
3. **Swagger**: For API documentation and testing.
4. **Mockito**: For unit testing microservices.
5. **H2**: Database used for storage within each microservice.

## Installation & Setup

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd DoConnect
   ```

2. **Build the application:**

   ```bash
   mvn clean package
   ```

3. **Run the application:**

   ```bash
   java -jar target/DoConnect-0.1.0.jar
   ```

4. **Access the application:**

   Visit [http://localhost:8080](http://localhost:8080) to interact with the application.

5. **Swagger Documentation:**

   Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view the API documentation.

## Future Improvements

1. **Database Integration**: Transition to a more robust database for production use.
2. **API Rate Limiting**: Prevent spamming by introducing rate-limiting mechanisms.
3. **Logging**: Implement logging strategies to monitor application activity.
