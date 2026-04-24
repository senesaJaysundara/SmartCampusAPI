# Smart Campus RESTful API

---

## API Overview

The Smart Campus API is a RESTful web service built with JAX-RS (Jersey) deployed on Apache Tomcat 9. It manages Rooms and Sensors across a university campus, providing full CRUD operations, sensor reading history and robust error handling using custom Exception Mappers.

### Key design decisions
* Resource hierarchy: /rooms -> /sensors -> /sensors/{id}/readings
* In-memory storage using HashMap 
* Custom exception mappers for 409, 422, 403, and 500 responses.
* JAX-RS filter for automatic request/response logging

---

## Part 1: Service Architecture and Setup

### Question 1:  Explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton?

**Answer:** 
By default, JAX-RS resource classes are instantiated per request, meaning a new instance is created for each incoming HTTP request. This ensures thread safety because each request operates on its own object instance. However, if a resource is configured as a singleton, multiple threads may access the same instance simultaneously. In such cases, shared data structures such as HashMaps must be synchronized to prevent race conditions and data inconsistency. Since each request gets a new resource instance, the static HashMaps in service classes (RoomService, SensorService) are shared across all instances. This means concurrent requests could read/write the same map simultaneously, causing race conditions. In this implementation, static maps are used as a deliberate design choice for simplicity, as the coursework prohibits databases.


### Question 2: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

**Answer:**
Hypermedia (HATEOAS) allows API responses to include links to related resources, enabling clients to dynamically navigate the API without relying on external documentation. This improves flexibility and reduces coupling between client and server and makes the API self-descriptive. Clients can follow links instead of hardcoding URLs, improving flexibility and maintainability.
For example, when a client creates a new sensor, the response can include a link to /api/v1/sensors/{id}/readings, allowing the client to immediately know where to post readings without consulting any external documentation.


---

## Part 2: Room Management

### Question 1: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

**Answer:**
Returning only IDs reduces network bandwidth and improves performance, especially when dealing with large datasets. However, it increases client-side complexity, as additional requests may be required to retrieve full details. Returning full objects simplifies client processing by providing all necessary information in one response, but it increases payload size and network usage. Therefore, a balance must be achieved based on use case requirements.  

### Question 2: Is the DELETE operation idempotent in your implementation?  

**Answer:**
Yes, the DELETE operation is idempotent in this implementation. The first DELETE request on a room with no sensors removes it and returns 200 OK with the deleted room. Any subsequent DELETE request for the same roomID returns 404 Not Found, since the room no longer exists - it does not throw an error or cause side effects. However, if the room has sensors assigned, every  DELETE attempt consistently returns 409 Conflict, which is also idempotent behaviour since the result is always the same. This aligns with the REST principle that idempotency means the server state after N identical requests equals the state after 1 request.
  

---

## Part 3: Sensor Operations and Linking

### Question 1: Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch? 

**Answer:**
The @Consumes(MediaType.APPLICATION_JSON) annotation restricts the API to accept only JSON input. If a client sends data in another format such as text, plain or application, xml, JAX-RS cannot find a suitable message body reader to process the request. As a result, the framework automatically returns an HTTP 415 Unsupported Media Type error, ensuring strict adherence to the expected input format.

### Question 2: Why is the query parameter approach generally considered superior for filtering and searching collections?

**Answer:**
Query parameters are designed for filtering and searching, while path parameters are meant for resource identification. Using query parameters allows multiple filters to be combined easily and keeps the URL structure clean and flexible.  Path-based filtering can lead to rigid and less scalable designs. Therefore, query parameters provide better usability and align more closely with RESTful best practices.
For example, /api/v1/sensors?type=CO2&status=ACTIVE is far cleaner than /api/v1/sensors/type/CO2/status/ACTIVE, and query parameters are naturally optional, meaning /api/v1/sensors still works without any filter applied.

---

## Part 4: Sub-Resources and Deep Nesting

### Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class? 

**Answer:**
The Sub-Resource Locator pattern improves modularity by separating logic into dedicated classes. This prevents large, complex controller classes and enhances readability, maintainability, and scalability. Each resource handles its own responsibility, making the API easier to extend. Without the Sub-Resource Locator pattern, all paths such as GET /sensors/{id}/readings and POST /sensors/{id}/readings would need to be defined inside SensorResource, making it a large, hard-to-maintain class. By delegating to a dedicated SensoreReadingResource class, each class has a single responsibility. The locator method @Path(“/{id}/readings”) in SensorResource simply instantiates and returns the sub-resource object, passing the sensor ID as context. JAX-RS then dispatches the actual HTTP method handling to SensorReadingResource. This pattern also makes it easy to add new nested resources in future, such as /sensors/{id}/alerts, without modifying existing classes - following the Open/Closed Principle.


---

## Part 5: Exception Handling and Logging

### Question 1: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload? 

**Answer:**
HTTP 422 indicates that the request is syntactically correct but semantically invalid. In this case, the JSON structure is valid, but the referenced room doesn't exist. A 404 error implies that the endpoint itself is not found, which is misleading. Therefore, 422 provides a more accurate representation of the error. 

### Question - 2: explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace? 

**Answer:**
Exposing stack traces reveals:
 1. Class and package name, showing the internal architecture.
 2. File paths on the server, revealing directory structure.
 3. Library versions, allowing attackers to look up known CVEs for those exact versions.
 4. Method names and line numbers, making it easier to reverse-engineer logic and find exploitable      code paths.

For example, seeing org.glassfish.jersey in a trace tells an attacker exactly which framework and version is running. The GlobalExceptionMapperin this implementation prevents this by catching all Throwable exceptions and returning only a generic Internal Server Error message.


### Question - 3: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method? 

**Answer:**
Filters provide a centralized mechanism for handling cross-cutting concerns like logging. 
This avoids code duplication and ensures consistency across all endpoints. Manual 
logging inside each method increases maintenance effort and makes the code harder to 
manage. Additionally, filters in JAX-RS are applied automatically to every request and response through the @Provider annotation, meaning new endpoints added in future are automatically logged without any developer remembering to add logging code. This is a key advantage of aspect-oriented design.


---

## Deployment Notes

This application is deployed as a WAR file on Apache Tomcat 9

## Application Base URL:
```
http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/
```
## API Base Path:
```
http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1 
```
---

##  How to Run the Project

1. Clone the repository:

```
git clone https://github.com/senesaJaysundara/SmartCampusAPI.git
```

2. Open the project in NetBeans (or any IDE)

3. Build the project

```
Right click → Clean and Build
```
4. Deploy the tomcat
* Copy the generated .war file from
  ```
  target/SmartCampuAPI-1.0-SNAPSHOT.war
  ```
* Paste into:
  ```
  apache-tomcat/webapps/
  ```
4. Run the project:

```
Right click project → Run
Tomcat server will start and deploy the application
```

5. Server will start at:

```
http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1
```

---

## API Endpoints

### Discovery

* GET http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1

---

### Rooms

* GET /api/v1/rooms
* GET /api/v1/rooms/{id}
* POST /api/v1/rooms
* PUT /api/v1/rooms/{id}
* DELETE /api/v1/rooms/{id}

---

### Sensors

* GET /api/v1/sensors
* GET /api/v1/sensors?type=CO2
* GET /api/v1/sensors/{id}
* POST /api/v1/sensors
* PUT /api/v1/sensors/{id}
* DELETE /api/v1/sensors/{id}

---

### Sensor Readings

* GET /api/v1/sensors/{id}/readings
* POST /api/v1/sensors/{id}/readings

---

## Sample curl Commands

### 1. Get API Discovery

```
curl http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1
```

### 2. Get All Rooms

```
curl http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/rooms
```

### 3. Create a New Room

```
curl -X POST http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/rooms ^
-H "Content-Type: application/json" ^
-d "{\"id\":\"CS-205\",\"name\":\"Computer Science Lab\",\"capacity\":40}"
```

### 4. Get Room by ID

```
curl http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/rooms/CS-205
```

### 5. Create a New Sensor

```
curl -X POST http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":\"HUM-001\",\"type\":\"Humidity\",\"value\":55.0,\"roomId\":\"CS-205\",\"status\":\"ACTIVE\"}"
```

### 6. Filter Sensors by Type

```
curl http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors?type=CO2
```

### 7. Add Sensor Reading

```
curl -X POST http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors/HUM-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":24.7}"
```

### 8. Get Sensor Reading

```
curl http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors/HUM-001/readings
```

### 9. Delete a Room (409 Conflict)

```
curl -X DELETE http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/rooms/LIB-301
```

### 10. Create sensor with invalid roomId

```
curl -X POST http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors -H "Content-Type: application/json" -d "{\"id\":\"HUM-002\",\"type\":\"Humidity\",\"value\":55.0,\"roomId\":\"FAKE-999\",\"status\":\"ACTIVE\"}"
```

### 11. Post reading to MAINTENANCE sensor

```
curl -X POST http://localhost:8080/SmartCampusAPI-1.0-SNAPSHOT/api/v1/sensors/MAINT-01/readings -H "Content-Type: application/json" -d "{\"value\":500}"
```

---

## Error Handling 

The API implements custom exception handling using Exception Mappers.

### 422 Unprocessable Entity
* Returned when a sensor reference a non-existent room

### 403 Forbidden
* Returned when adding readings to a sensor in MAINTENANCE state.

### 409 Conflict
* Returned when attempting to delete a room that still contains sensors

### 500 Internal Server Error
* Returned for unexpected system errors 
---
