# 🚖 SwiftRide - Taxi Booking Backend Application

Taxibooking is a robust backend application designed for a seamless taxi booking experience. Built using **Java** and **Spring Boot** and **Mysql**, it efficiently manages customers, drivers, vehicles, and bookings. This system ensures smooth ride-hailing operations with real-time booking management, driver availability tracking, and customer-friendly ride experiences

## 📦 Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [SnapShots](#snapshots)
- [API Endpoints](#api-endpoints)
    - [Customer Controller](#customer-controller)
    - [Driver Controller](#driver-controller)
    - [Vehicle Controller](#vehicle-controller)
    - [Booking Controller](#booking-controller)
- [Sample Data](#sample-data)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## 🚀 Features
- **User  Registration**: Customers and drivers can register and manage their profiles.
- **Booking Management**: Customers can book rides, and drivers can manage their bookings.
- **Vehicle Management**: Drivers can register and manage their vehicles.
- **Real-time Notifications**: Users receive updates about ride status and other important notifications.
- **Rating System**: Customers can rate their rides and provide feedback.
- **Gender and Age Filtering**: Customers can search for other users based on gender and age.
- **Statistics and Analytics**: Provides insights into bookings, revenue, and user engagement.

## 🛠️ Technologies Used

### 💻 Backend
- ****Java 21****: The primary programming language used for developing the application.
- **Spring Boot (3.3.3)**: A framework that simplifies the development of Java applications by providing built-in features and configurations. It allows for rapid application development with minimal setup.

### 📦 Database
- **MySQL**: A relational database management system used to store user data, blog posts, comments, and other application-related information.

### 🛠️ Libraries & Tools
- **Builder**: A library used for object mapping between Data Transfer Objects (DTOs) and entity classes, simplifying the conversion of data between layers.
- **Maven**: A build automation tool used for managing project dependencies and building the application.
- **Lombok**: Used for Readymade getters, setters and constructor.
- **Spring Data JPA**: Used for Databbbase Connectivity.
- **Swagger**: A tool that helps document and test RESTful APIs, providing an interactive interface for developers to explore the API endpoints.


## 🏁 Getting Started
To get started with the SwiftRide application, follow these steps:

1**Set up the database:**

- Create a MySQL database named `swift_ride`.
- Update the application.properties file with your database credentials.

2**Build the project:**

```bash
    gradle build
```

3## 🏃‍♂️ Running the Application

To run the application, use the following command:

```bash
./gradlew bootRun
```
The application will start on [http://localhost:8080](http://localhost:8080).

4**Access the application:**

- Open your browser and go to http://localhost:8081.

5**Swagger Documentation:**

- Access the Swagger UI at http://localhost:8081/swagger-ui/index.html to explore and test the API endpoints interactively.

## 💻 Snapshots

```bash
    Snapshots to make the documentation more informative
```

<img src="https://assets.thehansindia.com/h-upload/2022/08/01/1600x960_1305733-tiktok.jpg" alt="api-snapshot" width="100%"/>


## 📡 API Endpoints

### 👥 Customer Controller
- **POST** `/customer/` - Add a new customer
- **GET** `/customer/{id}` - Get customer by ID
- **GET** `/customer/gender/{gender}` - Get all customers by gender
- **PUT** `/customer/{customerId}` - Update customer details
- **DELETE** `/customer/{customerId}` - Delete a customer

### 🚗 Driver Controller
- **POST** `/driver/` - Add a new driver
- **GET** `/driver/{driverId}` - Get driver by ID
- **GET** `/driver/available` - Get all available drivers
- **PUT** `/driver/{driverId}` - Update driver details
- **DELETE** `/driver/{driverId}` - Delete a driver

### 🚙 Vehicle Controller
- **POST** `/vehicle/register` - Register a new vehicle
- **GET** `/vehicle/{vehicleId}` - Get vehicle by ID
- **GET** `/vehicle/` - Get all vehicles
- **PUT** `/vehicle/{vehicleId}` - Update vehicle details
- **DELETE** `/vehicle/{vehicleId}` - Delete a vehicle

### 📅 Booking Controller
- **POST** `/booking/customer/{customerId}` - Book a ride for a customer
- **PATCH** `/booking/{bookingId}/rate` - Rate a booking
- **GET** `/booking/{bookingId}/customer/{customerId}` - Get booking details
- **DELETE** `/booking/{bookingId}/customer/{customerId}` - Delete a booking
- **GET** `/booking/status` - Get bookings by status

## 📊 Sample Data

### Customer
```json
{
  "customerName": "John Doe",
  "CustomerAge": 30,
  "emailId": "john.doe@example.com",
  "customerPhone": "1234567890",
  "gender": "MALE"
}
```

### Driver
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "age": 28,
  "phone": "0987654321"
}
```

### Vehicle
```json
{
  "model": "Toyota Camry",
  "licensePlate": "XYZ 1234",
  "color": "Black",
  "perKmRate": 10.0,
  "vehicleType": "CAR"
}
```

### Booking
```json
{
  "pickUpLocation": "123 Main St",
  "dropOffLocation": "456 Elm St",
  "tripDistanceInKm": 15.0
}
```

## 🧪 Testing

To run the tests, use the following command:

```bash
mvn test
```

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue.
```bash
 Big Thanks To "AccioJob" for Helping me out 
```

entity(model) -> repository -> dto(req/res) -> transformer(used for converting entity <-> dto) -> service (api-logic) -> exception (custom exception that service might throw and controller must return clean responses) -> controller (here creating rest apis)