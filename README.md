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
    "name": "Pasta Carbonara",        // string, required
    "ingredients": ["Pasta", "Eggs", "Bacon", "Parmesan"], // array of strings, required
    "instructions": "Boil pasta. Cook bacon. Mix with eggs and cheese." // string, required
  }
  ```
- **Response:** Returns the created recipe with its ID.

---

### Search Recipes
- **GET** `/recipes/search`
- **Query Parameters (all optional):**
  - `vegetarian` (boolean) — Filter by vegetarian recipes
  - `servings` (integer) — Filter by number of servings
  - `includeIngredients` (list of strings, e.g., `includeIngredients=Eggs&includeIngredients=Cheese`) — Recipes must include these ingredients
  - `excludeIngredients` (list of strings) — Recipes must NOT include these ingredients
  - `instruction` (string) — Filter by instruction text
- **Response:** Set of recipes matching the filters.

---

### Get Recipe by ID
- **GET** `/recipes/{id}`
- **Path Parameter:**
  - `id` (UUID) — The recipe's unique identifier
- **Response:** Returns the recipe with the specified ID.

---

### Update a Recipe
- **PUT** `/recipes/{id}`
- **Path Parameter:**
  - `id` (UUID) — The recipe's unique identifier
- **Request Body:**
  ```json
  {
    "name": "Pasta Carbonara",        // string, required
    "ingredients": ["Pasta", "Eggs", "Bacon", "Parmesan"], // array of strings, required
    "instructions": "Boil pasta. Cook bacon. Mix with eggs and cheese." // string, required
  }
  ```
- **Response:** Returns the updated recipe.

---

### Delete a Recipe
- **DELETE** `/recipes/{id}`
- **Path Parameter:**
  - `id` (UUID) — The recipe's unique identifier
- **Response:** No content (204) on success.

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
