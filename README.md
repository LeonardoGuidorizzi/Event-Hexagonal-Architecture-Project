# Event API

A REST API for creating, publishing, and canceling events. The project uses a hexagonal architecture to keep business rules separate from web, persistence, and framework concerns.

## Technologies

- Java 21
- Spring Boot 4.0.3 (Web MVC, Data JPA, Validation, Actuator)
- PostgreSQL
- Flyway for database migrations
- MapStruct for mapping between domain and persistence models
- Springdoc OpenAPI 3.2.0 and Swagger UI
- Micrometer with the Datadog metrics registry
- Maven

## Getting started

1. Create a PostgreSQL database and configure its connection in `src/main/resources/application.yaml` (use `application.yaml.example` as a reference).
2. Start the application:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows, use `mvnw.cmd spring-boot:run`.

Flyway applies the SQL migrations from `src/main/resources/db/migration` when the application starts. The default server port is `8080` and can be changed with `SERVER_PORT`.

## API documentation

When the application is running locally:

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [OpenAPI JSON](http://localhost:8080/v3/api-docs)

## Routes

| Method | Route | Description | Success response |
| --- | --- | --- | --- |
| `POST` | `/api/v1/create` | Create an event | `201 Created` with the event |
| `POST` | `/api/v1/publish/{id}` | Publish an event by ID | `200 OK` with the updated event |
| `POST` | `/api/v1/cancel/{id}` | Cancel an event by ID | `200 OK` with the updated event |
| `PUT` | `/api/v1/{id}` | Update a draft event by ID | `200 OK` with the updated event |
| `DELETE` | `/api/v1/{id}` | Delete an event by ID | `204 No Content` |

Create request example:

```json
{
  "name": "Community meetup",
  "description": "A meetup for local developers",
  "local": "Sao Paulo",
  "host": "Event team",
  "capacity": 80,
  "eventType": "SEMINAR",
  "start": "2030-06-15T18:00:00",
  "end": "2030-06-15T21:00:00"
}
```

The `name`, `local`, `host`, `capacity`, `eventType`, `start`, and `end` fields are required. Capacity must be positive and the start time must be in the future. `eventType` must match a value in the `EventType` enum.

## Technical decisions

- **Hexagonal architecture:** application use cases depend on application ports. Infrastructure adapters implement the persistence port and expose the HTTP API; infrastructure configuration wires use case implementations to their dependencies.
- **Framework independent domain:** event rules and state transitions live in the domain model, without Spring, HTTP, or JPA annotations. Infrastructure persistence entities are mapped to and from the domain model.
- **HTTP boundary models:** the presentation adapter accepts and returns DTOs and maps them to application/domain types, keeping transport details out of the core.
- **Database evolution:** Flyway versioned SQL migrations define the schema, while Spring Data JPA provides persistence access.
- **API contract:** Springdoc generates the OpenAPI description and Swagger UI from the presentation endpoints and their documentation annotations.
- **Observability:** Actuator and Micrometer expose application metrics, with a Datadog registry available for export.

## Author

[Leonardo Guidorizzi](https://github.com/LeonardoGuidorizzi)
