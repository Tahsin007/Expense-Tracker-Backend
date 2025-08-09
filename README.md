# Expense Tracker Backend

A comprehensive and robust RESTful API for managing personal finances. This backend service allows users to track their income and expenses, set budgets, manage categories, and handle recurring transactions. It is built with Spring Boot and secured with JWT authentication.

## ✨ Features

*   **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens).
*   **Transaction Management:** CRUD operations for income and expenses.
*   **Budgeting:** Set and manage monthly budgets.
*   **Categorization:** Create and manage categories for transactions.
*   **Recurring Transactions:** Schedule and manage recurring income or expenses.
*   **Reporting:** Generate monthly and yearly financial reports.
*   **API Documentation:** Interactive API documentation with Swagger UI.
*   **Containerized:** Ready to run with Docker and Docker Compose.

## 🛠️ Technologies Used

*   **Java 17**
*   **Spring Boot 3.3.2**
*   **Spring Web**
*   **Spring Security**
*   **Spring Data JPA**
*   **MySQL** (with H2 for testing)
*   **Maven**
*   **Lombok**
*   **JWT (JSON Web Token)**
*   **SpringDoc OpenAPI (Swagger UI)**
*   **Docker & Docker Compose**

## 📂 Folder Structure

```
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com/project/Expense/Tracker
│   │   │       ├───Controller      # API endpoints
│   │   │       ├───Entity          # JPA entities
│   │   │       ├───Exception       # Custom exception handling
│   │   │       ├───Repository      # Data access layer
│   │   │       ├───Security        # Spring Security and OpenAPI config
│   │   │       ├───Service         # Business logic
│   │   │       └───Utils           # JWT utilities
│   │   └───resources
│   │       ├───application.properties      # Main application configuration
│   │       └───application-local.properties # Local development configuration
│   └───test                        # Test classes
├───.gitignore
├───docker-compose.yml              # Docker services definition
├───Dockerfile                      # Docker image build instructions
├───pom.xml                         # Maven project configuration
└───README.md
```
## 🗃️ Database Schema

<img width="1232" height="806" alt="Image" src="https://github.com/user-attachments/assets/99044c9b-dee3-497a-b8b0-91c084752155" />


## 🚀 Getting Started

### Prerequisites

*   Java 17 or later
*   Maven 3.2+
*   Docker and Docker Compose

### Running the Application

#### 1. Using Docker (Recommended)

This is the easiest way to get the application and the database running.

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd Expense-Tracker-Backend
    ```

2.  **Build and run with Docker Compose:**
    ```bash
    docker-compose up -d
    ```

The application will be accessible at `http://localhost:8080`.

#### 2. Running Locally with Maven

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd Expense-Tracker-Backend
    ```

2.  **Setup MySQL:**
    Make sure you have a MySQL instance running. Create a database named `expenseTrackerDB`.

3.  **Configure the application:**
    Open `src/main/resources/application-local.properties` and update the `spring.datasource` properties with your MySQL credentials.

4.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run -Dspring.profiles.active=local
    ```

The application will be accessible at `http://localhost:8081`.

## 📖 API Documentation

Once the application is running, you can access the interactive Swagger UI documentation to explore and test the API endpoints.

*   **URL:** `http://localhost:8080/swagger-ui.html` (for Docker) or `http://localhost:8081/swagger-ui.html` (for local)

## 🔐 API Endpoints

Here is a summary of the available API endpoints. All endpoints except for `/public/**` require a valid JWT token in the `Authorization` header.

### Public Controller (`/public`)

| Method   | Endpoint         | Description                               |
| :------- | :--------------- | :---------------------------------------- |
| `POST`   | `/sign-up`       | Register a new user.                      |
| `POST`   | `/sign-in`       | Authenticate a user and get a JWT token.  |
| `POST`   | `/logout`        | Log out the user and invalidate the token.|

### Transaction Controller (`/transaction`)

| Method   | Endpoint                   | Description                               |
| :------- | :------------------------- | :---------------------------------------- |
| `GET`    | `/`                        | Get all transactions for the user.        |
| `POST`   | `/`                        | Add a new transaction.                    |
| `PUT`    | `?id={id}`                 | Update a transaction by its ID.           |
| `DELETE` | `/{id}`                    | Delete a transaction by its ID.           |
| `GET`    | `/monthly-report`          | Get a monthly transaction report.         |
| `GET`    | `/yearly-report`           | Get a yearly transaction report.          |
| `GET`    | `/category-report/{category}` | Get transactions for a specific category. |

### Budget Controller (`/budget`)

| Method   | Endpoint         | Description                               |
| :------- | :--------------- | :---------------------------------------- |
| `GET`    | `/`              | Get all budgets for the user.             |
| `POST`   | `/`              | Set a new monthly budget.                 |
| `GET`    | `/month/{month}` | Get the budget for a specific month.      |
| `GET`    | `/year/{year}`   | Get all budgets for a specific year.      |
| `PUT`    | `/{budgetId}`    | Update a budget by its ID.                |

### Category Controller (`/categories`)

| Method   | Endpoint | Description                  |
| :------- | :------- | :--------------------------- |
| `GET`    | `/`      | Get all available categories.|
| `POST`   | `/`      | Create a new category.       |
| `PUT`    | `/{id}`  | Update a category by its ID. |
| `DELETE` | `/{id}`  | Delete a category by its ID. |

### Recurring Transaction Controller (`/recurring`)

| Method   | Endpoint | Description                            |
| :------- | :------- | :------------------------------------- |
| `GET`    | `/`      | Get all recurring transactions.        |
| `POST`   | `/`      | Create a new recurring transaction.    |
| `GET`    | `/{id}`  | Get a recurring transaction by ID.     |
| `PUT`    | `/{id}`  | Update a recurring transaction by ID.  |
| `DELETE` | `/{id}`  | Delete a recurring transaction by ID.  |
