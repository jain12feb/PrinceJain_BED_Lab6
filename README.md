# College Debate Web App

Welcome to the College Debate Web App! This application, built using Java Spring Boot, facilitates college debates by allowing students to register, manage records, and engage in debates effectively.

## Features

- **User Authentication**: Secure user registration and login functionality using Spring Security.
- **User Authorization**: Role-based access control, allowing administrators to perform CRUD operations on student records while regular users can only view registered students.
- **Search Functionality**: Search students by first name, last name, or email address.
- **Sorting**: Sort student records by various columns.
- **Pagination**: Navigate through large sets of student records easily.
- **CRUD Operations**: Add, update, and delete student records.
- **Print Functionality**: Print all student records for offline use.
  
## Usage

Upon accessing the application, you will be presented with the login page. Depending on your role:

- **Admin**: You will have access to all functionalities, including adding, updating, and deleting student records, as well as searching, sorting, pagination, and printing.
- **User**: You will only have access to view student records.

## Technologies Used

- **Java Spring Boot**: Backend framework
- **MySQL**: Relational database management system
- **Spring Security**: Authentication and authorization framework
- **Thymeleaf**: Template engine for server-side rendering
