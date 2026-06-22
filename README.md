# Student Management System

A Java Swing application for managing student records using MySQL and JDBC.

## Features
- Add Student
- View Students
- Update Student
- Delete Student
- Search Student

## Technologies Used
- Java
- Swing
- JDBC
- MySQL

## Database
Database Name: student_db

Table:
```sql
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    course VARCHAR(100)
);
```
