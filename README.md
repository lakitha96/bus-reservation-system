# Bus Reservation System

This project is a simplified Bus Reservation System implemented using pure Java. It is divided into two modules:

- **server**: A REST API built using Java Servlets and deployed as a `.war` file on Apache Tomcat 9.
- **client**: A standalone Java application packaged as a `.jar` that calls the server API

---

## Project Structure

```bus-reservation-system/
├── server/ # REST API (WAR)
└── client/ # API testing client (JAR)
```

## How to Run the Project

### Pre requirements

- Java 8 or higher
- Maven
- Apache Tomcat 9 (installed at: `~/tomcat`)

### 1. Run Full setup through the Deliverables

- WAR File: server/target/bus-reservation-server.war 
- JAR File: client/target/bus-reservation-assignment/bus-reservation-client.jar

### Run Full Setup via Script

Note: Before running the script, make sure to update all file paths correctly (Tomcat location, project directory, etc...)

```bash
cd bus-reservation-system
chmod +x startup.sh
./startup.sh
```


### This script will:

- Clean and build both server and client modules 
- Deploy the .war file to Tomcat 
- Restart the Tomcat server 
- Run the client .jar to test Availability and Booking APIs

## API Endpoints

### 1. Check Availability
```
curl --location 'http://localhost:8080/bus-reservation-server/api/v1/availability?origin=C&destination=D&passengers=6'
```

### 2. Book Seats
```
curl --location 'http://localhost:8080/bus-reservation-server/api/v1/bookings' \
--header 'Content-Type: application/json' \
--data '{
    "origin": "C",
    "destination": "D",
    "passengers": 6,
    "amount": 300
}'
```

## Assumptions
- Only one bus operates the route A -> B -> C -> D and returns D -> C -> B -> A per day. 
- All reservations share the same 40 seats on the bus, no matter the origin or destination. 
- If a seat is booked only up to stop C, it becomes available again from C onward (e.g.,
for C to D). 
- The bus always has 40 seats labeled from 1A to 10D (4 seats per row
and 10 rows). 
- Fixed journey stops: A, B, C, and D. Any other stop (e.g., E) is considered invalid. 
- Prices between each pair of stops are hardcoded and same both directions.

## Test Coverage
- Business logic in BookingServiceImpl and AvailabilityServiceImpl is covered with 100% unit test coverage. 
- Unit tests were executed and analyzed using IntelliJ IDEA's test coverage tools.