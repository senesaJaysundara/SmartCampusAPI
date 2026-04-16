# Smart Campus RESTful API

## Overview

This project is a RESTful API developed using Java, JAX-RS (Jersey), and Grizzly HTTP Server. It simulates a Smart Campus system that manages rooms, sensors, and sensor readings.

The API supports CRUD operations, filtering, validation, and nested resources following REST principles.

## Technologies Used

* Java 17
* JAX-RS (Jersey)
* Grizzly HTTP Server
* Maven


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

### 1. Get API Info

```
curl http://localhost:8080/api/v1
```

### 2. Create Room

```
curl -X POST http://localhost:8080/api/v1/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"name\":\"Room A\",\"capacity\":30}"
```

### 3. Create Sensor

```
curl -X POST http://localhost:8080/api/v1/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"type\":\"Temperature\",\"value\":25,\"roomId\":1}"
```

### 4. Get Sensors (Filtered)

```
curl "http://localhost:8080/api/v1/sensors?type=Temperature"
```

### 5. Add Sensor Reading

```
curl -X POST http://localhost:8080/api/v1/sensors/1/readings \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"value\":30.5,\"timestamp\":\"2026-04-14T10:00:00\"}"
```

---

## Notes

* The API uses in-memory storage (no database)
* Data will reset when the server restarts
* Designed following RESTful best practices
