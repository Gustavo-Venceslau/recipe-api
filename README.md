# Recipe API

A RESTful API for managing recipes, built with Spring Boot. This project allows users to register, search, update, and delete recipes. It is designed with clean architecture principles and includes integration and unit tests.

## Features
- Register new recipes
- Search recipes with filters
- Get recipe details by ID
- Update existing recipes
- Delete recipes
- Exception handling with meaningful responses

## Requirements
- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)
- [Docker](https://www.docker.com/) (for running with Docker Compose)

## Setup Instructions

### 1. Clone the repository
```shell
git clone https://github.com/yourusername/recipe-api.git
cd recipe-api
```

### 2. Configure Environment
- Ensure Java 17 and Maven 3 are installed and available in your PATH.
- (Optional) Copy or edit `src/main/resources/application.yml` for custom configuration (e.g., database, port).

### 3. Running the Application

#### Using Maven (Local)
```shell
mvn spring-boot:run
```
- The API will be available at `http://localhost:8080` by default.

#### Using Docker Compose
```shell
docker-compose up --build
```
- This will build the image and start the service. The API will be available at `http://localhost:8080`.

#### Troubleshooting
- If you encounter port conflicts, change the server port in `application.yml`.
- For Java or Maven issues, verify your versions with `java -version` and `mvn -version`.

## API Endpoints

### Register a Recipe
- **POST** `/recipes`
- **Request Body:**
```json
{
  "name": "Pasta Carbonara",
  "ingredients": ["Pasta", "Eggs", "Bacon", "Parmesan"],
  "instructions": "Boil pasta. Cook bacon. Mix with eggs and cheese."
}
```

### Search Recipes
- **GET** `/recipes?name=pasta&ingredient=egg`
- **Response:**
```json
[
  {
    "id": 1,
    "name": "Pasta Carbonara",
    "ingredients": ["Pasta", "Eggs", "Bacon", "Parmesan"],
    "instructions": "Boil pasta. Cook bacon. Mix with eggs and cheese."
  }
]
```

### Get Recipe by ID
- **GET** `/recipes/{id}`

### Update a Recipe
- **PUT** `/recipes/{id}`
- **Request Body:** (same as register)

### Delete a Recipe
- **DELETE** `/recipes/{id}`

## Testing

To run all tests:
```shell
mvn test
```
- Integration and unit tests are included under `src/test/java/com/galmv/`.

## Project Structure
```
recipe-api/
├── src/
│   ├── main/
│   │   └── java/com/galmv/
│   │       ├── adapter/controllers/         # REST controllers
│   │       ├── application/interfaces/      # DTOs and filters
│   │       ├── application/useCases/        # Use case logic
│   │       ├── domain/                      # Entities, exceptions, repositories
│   │       └── RecipeApplication.java       # Main Spring Boot app
│   └── test/java/com/galmv/                 # Integration and unit tests
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Design Choices

### Clean Architecture
- The project follows Clean Architecture, separating concerns into layers:
  - **Controllers (adapter/controllers):** Handle HTTP requests and responses.
  - **Application (application/useCases, application/interfaces):** Contains business logic and use case interfaces/implementations.
  - **Domain (domain/entities, domain/repositories, domain/exceptions):** Core business models, repository abstractions, and domain exceptions.

### Exception Handling
- Centralized exception handling via `GlobalExceptionHandler` ensures consistent and meaningful error responses.

### Testing Strategy
- **Unit tests** cover use case logic in isolation.
- **Integration tests** validate controller endpoints and overall flow.

### Rationale
- **Separation of concerns** improves maintainability and testability.
- **DTOs and filters** decouple API contracts from domain models.
- **Repository abstraction** allows for flexible data source implementations.
