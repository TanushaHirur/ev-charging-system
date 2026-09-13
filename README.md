# EV Charging Station Booking and Management System

## Project Overview

The EV Charging Station Booking and Management System is a Java Spring Boot application designed to manage electric vehicle charging infrastructure and booking operations.

The system allows users to manage electric vehicles, charging stations, charging slots, bookings, and charging sessions through RESTful APIs.

The application uses MySQL for persistent data storage and implements backend business logic for booking validation, charging session management, and prevention of overlapping bookings.

## Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- RESTful APIs
- GitHub
- Dockerfile

## Main Features

### User Management
- Create users
- View users
- Update users
- Delete users

### Vehicle Management
- Register electric vehicles
- View vehicle details
- Update vehicle information
- Delete vehicles

### Charging Station Management
- Create charging stations
- View charging stations
- Update charging stations
- Delete charging stations

### Charging Slot Management
- Create charging slots
- View charging slots
- Update slot status
- Delete charging slots

### Booking Management
- Create charging bookings
- View bookings
- Update bookings
- Delete bookings
- Validate booking times
- Check charging slot availability
- Prevent overlapping bookings

### Charging Session Management
- Create charging sessions
- Start charging sessions
- Complete charging sessions
- Record energy consumption
- Track charging session status
- Calculate charging session cost

### Statistics

The system provides an API for viewing overall system statistics, including:

- Total users
- Total vehicles
- Total charging stations
- Total charging slots
- Total bookings
- Total charging sessions

## System Architecture

The application follows a layered architecture:

```text
Client
  |
  v
REST Controller
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
MySQL Database

## Installation and Setup

### Prerequisites

Make sure the following are installed:

- Java 21 or higher
- MySQL
- Git

### Database Configuration

The application uses MySQL for persistent data storage.

Configure the database connection in:

```text
src/main/resources/application.properties
```

The database password is provided through the `DB_PASSWORD` environment variable and is not stored in the GitHub repository.

For macOS/Linux:

```bash
export DB_PASSWORD="your_mysql_password"
```

### Run the Application

From the project root directory, run:

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

### REST API Endpoints

The application provides the following main REST endpoints:

| Resource | Endpoint |
|---|---|
| Users | `/api/users` |
| Vehicles | `/api/vehicles` |
| Charging Stations | `/api/stations` |
| Charging Slots | `/api/slots` |
| Bookings | `/api/bookings` |
| Charging Sessions | `/api/sessions` |
| Statistics | `/api/statistics` |

### Example Application Workflow

The main workflow of the system is:

1. Create a user.
2. Register an electric vehicle.
3. Create a charging slot.
4. Create a booking for the charging slot.
5. The system checks for booking conflicts.
6. Create a charging session for the booking.
7. Start the charging session.
8. Complete the charging session with energy consumption data.
9. View system statistics.

### Build and Test

To run the automated tests:

```bash
./mvnw clean test
```

To build the application:

```bash
./mvnw clean package
```