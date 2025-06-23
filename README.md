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

## Getting Started

### Clone the repository
```shell
git clone https://github.com/yourusername/recipe-api.git
cd recipe-api
```

### Running the application locally

#### Using Maven
```shell
mvn spring-boot:run
```

#### Using Docker Compose
```shell
docker-compose up --build
```

The API will be available at `http://localhost:8080` by default.

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