# Event API

A REST API for creating, publishing, and canceling events. The project uses a hexagonal architecture to keep business rules separate from web, persistence, and framework concerns.

## Technologies

- Java 21
- Spring Boot 4.0.3 (Web MVC, Data JPA, Validation, Actuator, Security, OAuth 2.0 Resource Server)
- PostgreSQL
- Flyway for database migrations
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
| `POST` | `/api/v1/{id}/sign-up` | Sign up for a published event | `200 OK` with the updated event |
| `PUT` | `/api/v1/{id}` | Update a draft event by ID | `200 OK` with the updated event |
| `DELETE` | `/api/v1/{id}` | Delete an event by ID | `204 No Content` |
| `POST` | `/api/v1/auth/signup` | Create a user and account | `201 Created` with an access token |
| `POST` | `/api/v1/auth/login` | Log in | `200 OK` with an access token |
| `POST` | `/api/v1/auth/logout` | Revoke the current access token | `200 OK` |

Auth signup accepts `name`, `lastname`, `birthdate`, `email`, and `password`. `Account` stores the profile fields and `User` stores the login credentials. Auth signup and login routes are public; other routes require `Authorization: Bearer <accessToken>`. Event signup is limited to published events that have not ended, rejects duplicate participation, and tracks participation per user. Passwords use BCrypt and access tokens expire after 15 minutes. Configure `JWT_SECRET_BASE64` with a Base64 encoded random key of at least 32 bytes (for example, `openssl rand -base64 32`).

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
- **Cancelled event cleanup:** Cancellation records a timestamp, and a scheduled cleanup job removes cancelled events after a two-minute retention period. The cleanup uses a database-side delete filtered by status and cutoff, so it removes only eligible rows and does not load them into application memory.
- **Authentication and authorization:** Spring Security's OAuth 2.0 Resource Server validates signed JWT bearer tokens and protects event routes. Signup and login issue short-lived tokens; BCrypt hashes passwords, while logout revokes the token ID until expiry so a logged-out token cannot be reused.
- **API contract:** Springdoc generates the OpenAPI description and Swagger UI from the presentation endpoints and their documentation annotations.
- **Observability:** Actuator and Micrometer expose application metrics, with a Datadog registry available for export.

## Author

[Leonardo Guidorizzi](https://github.com/LeonardoGuidorizzi)
