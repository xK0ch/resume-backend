# Resume backend

## Introduction

Spring boot project for creating a resume

## Environment variables

In order to have your own basic authentication username & password some environment variables need to be set.

1. Run the command:
    ```bash
   cp .env.example .env
    ```
2. Adjust the variables within the `.env` to your personal preference

**Hint:** Even though it is not recommended this step can be skipped.
There are already default values set within the application.

## Starting the application locally

### Manuel start

1. Make sure [***docker***](https://www.docker.com/) is installed & running
2. To create the database container run:
    ```bash
    docker run --name resume-database-container -p 5433:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=letmein -e POSTGRES_DB=resume-database -d postgres:17.2
    ```
3. To start the application run:
    ```bash
    ./gradlew bootRun
    ```

### Compose start

1. Make sure [***docker***](https://www.docker.com/) is installed & running
2. Run:
    ```bash
    docker compose -f docker-compose-resume-backend.yml up --build
    ```

## Testing

### Unit tests

1. Run:
   ```bash
   ./gradlew test
   ```

### Integration tests

1. Make sure [***docker***](https://www.docker.com/) is installed & running
2. Run:
   ```bash
   ./gradlew integrationTest
   ```

## Documentation

For the documentation of the REST API [***springdoc***](https://springdoc.org/)
is used.

### Swagger

To see the Swagger documentation go [***here***](https://fynn-koch.de:8443/docs/ui)

### OpenAPI

To see the OpenAPI specs go [***here***](https://fynn-koch.de:8443/docs)