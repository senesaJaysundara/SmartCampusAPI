# Smart Campus RESTful API

## Part 1: Service Architecture and Setup

### Question 1:  Explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton?

**Answer:** 
By default, JAX-RS resource classes are instantiated per request, meaning a new instance is created for each incoming HTTP request. This ensures thread safety because each request operates on its own object instance. However, if a resource is configured as a singleton, multiple threads may access the same instance simultaneously. In such cases, shared data structures such as HashMaps must be synchronized to prevent race conditions and data inconsistency.

### Question 2: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?

**Answer:**
Hypermedia (HATEOAS) allows API responses to include links to related resources, enabling clientsto dynamically navigate the API without relying on external documentation. This improves flexibility and reduces coupling between client and server and makes the API self-descriptive. Clients can follow links instead of hardcoding URLs, improving flexibility and maintainability.

---

## Part 2: Room Management

### Question 1: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

**Answer:**
Returning only IDs reduces network bandwidth and improves performance, especially when dealing with large datasets. However, it increases client-side complexity, as additional requests may be required to retrieve full details. Returning full objects simplifies client processing by providing all necessary information in one response, but it increases payload size and network usage. Therefore, a balance must be achieved based on use case requirements.  

### Question 2: Is the DELETE operation idempotent in your implementation?  

**Answer:**
Yes, the DELETE operation is idempotent. This means that making the same DELETE request multiple times produces the same result. 
In this implementation: 
● The first DELETE removes the room successfully. 
● Subsequent DELETE requests for the same room will either return a “not found” response or have no effect. 
This behavior ensures consistency and aligns with REST principles.  

---

## Part 3: Sensor Operations and Linking

### Question 1: Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch? 

**Answer:**
The @Consumes(MediaType.APPLICATION_JSON) annotation restricts the API to accept only JSON input. If a client sends data in another format such as text, plain or application, xml, JAX-RS cannot find a suitable message body reader to process the request. As a result, the framework automatically returns an HTTP 415 Unsupported Media Type error, ensuring strict adherence to the expected input format.

### Question 2: Why is the query parameter approach generally considered superior for filtering and searching collections?

**Answer:**
Query parameters are designed for filtering and searching, while path parameters are meant for resource identification. Using query parameters allows multiple filters to be combined easily and keeps the URL structure clean and flexible.  Path-based filtering can lead to rigid and less scalable designs. Therefore, query parameters provide better usability and align more closely with RESTful best practices.

---

## Part 4: Sub-Resources and Deep Nesting

### Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class? 

**Answer:**
The Sub-Resource Locator pattern improves modularity by separating logic into dedicated classes. This prevents large, complex controller classes and enhances readability, maintainability, and scalability. Each resource handles its own responsibility, making the API easier to extend. 

---

## Part 5: Exception Handling and Logging

### Question 1: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload? 

**Answer:**
HTTP 422 indicates that the request is syntactically correct but semantically invalid. In this case, the JSON structure is valid, but the referenced room doesn't exist. A 404 error implies that the endpoint itself is not found, which is misleading. Therefore, 422 provides a more accurate representation of the error. 

### Question - 2: explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace? 

**Answer:**
Exposing stack traces can reveal sensitive internal details such as class names, file paths and system structures. Attackers can use this information to identify vulnerabilities and exploit the system. Therefore, APIs should return generic error messages to enhance security. 

### Question - 3: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method? 

**Answer:**
Filters provide a centralized mechanism for handling cross-cutting concerns like logging. 
This avoids code duplication and ensures consistency across all endpoints. Manual 
logging inside each method increases maintenance effort and makes the code harder to 
manage.

---

##  How to Run the Project

1. Clone the repository:

```
git clone https://github.com/YOUR_USERNAME/SmartCampusAPI.git
```

2. Open the project in NetBeans (or any IDE)

3. Build the project:

```
Right click → Clean and Build
```

4. Run the project:

```
Run Main.java
```

5. Server will start at:

```
http://localhost:8080/api/v1
```

---

## API Endpoints

### Discovery

* GET /api/v1

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
* POST /api/v1/sensors
* GET /api/v1/sensors/{id}

---

### Sensor Readings

* GET /api/v1/sensors/{id}/readings
* POST /api/v1/sensors/{id}/readings

---

## Sample curl Commands

### 1. Get API Discovery

```
curl http://localhost:8080/api/v1
```

### 2. Get All Rooms

```
curl http://localhost:8080/api/v1/rooms
```

### 3. Create a New Room

```
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"name\":\"Room A\"}"
```

### 4. Get Room by ID

```
curl http://localhost:8080/api/v1/rooms/1
```

### 5. Create a New Sensor

```
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"type\":\"Temperature\",\"value\":25,\"roomId\":1}"
```

### 6. Get Sensors (Filtered by Type)

```
curl "http://localhost:8080/api/v1/sensors?type=CO2"
```

### 7. Add Sensor Reading

```
curl -X POST http://localhost:8080/api/v1/sensors/1/readings \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"value\":30.5,\"timestamp\":\"2026-04-14T10:00:00\"}"
```

### 1. Get Sensor REading

```
curl http://localhost:8080/api/v1/sensors/readings
```

---

## Notes

* The API uses in-memory storage (no database)
* Data will reset when the server restarts
* Designed following RESTful best practices
