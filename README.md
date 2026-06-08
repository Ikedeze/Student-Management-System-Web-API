# Student Management System Web API

The Student Management System Web API is a backend application designed to manage student-related data through RESTful API endpoints. It provides a structured way to perform operations such as creating, retrieving, updating, and deleting student records while ensuring efficient data management and communication between client applications and the database.

Built using modern web development principles, the project demonstrates the implementation of backend architecture, API design, database integration, and data validation. It serves as a practical example of how educational management systems can be developed to handle student information in a scalable and maintainable manner.

This project showcases skills in backend development, API development, database management, and software engineering best practices, making it a valuable learning and portfolio project for aspiring developers.


# Dual-Mode Student Management System (Console & Web API)

A robust, high-performance Java application designed to manage student records through a traditional command-line CLI or via a custom, lightweight local Web API server. This project demonstrates backend architecture fundamentals, file I/O synchronization, data parsing, and raw HTTP networking without relying on heavy external frameworks.

---

## 🚀 Key Features

* **Dual-Environment Boot Allocation:** A system boot manager allows users to switch between a native CLI interface or spin up a local network server on port `8000`.
* **Data Persistence Layer:** All student records are automatically synchronized and serialized into a structured local text file (`students.txt`).
* **Custom Web API Routing Engine:** Low-level integration of Java's native `com.sun.net.httpserver` to build clean API endpoints.
* **Advanced Data Processing:** Custom-built robust string parsing algorithms to chop and validate query parameters seamlessly.
* **Remote Server Kill-Switch:** Built-in network endpoint to gracefully execute thread termination and server shutdown remotely.

---

## 🛠️ Architecture & Endpoints

When initialized in **Web API Server Mode**, the application acts as a microservice offering the following endpoints accessible via any browser or API testing client:

| Action | HTTP Endpoint URL | Description |
| :--- | :--- | :--- |
| **View All** | `http://localhost:8000/students` | Streams the complete directory of registered students. |
| **Search** | `http://localhost:8000/search?id=NIIT1` | Filters data to isolate a single student by ID. |
| **Add** | `http://localhost:8000/add?name=Ike&age=25&course=Java` | Multi-parameter endpoint that parses chunks, validates types, saves, and updates storage. |
| **Delete** | `http://localhost:8000/delete?id=NIIT1` | Deletes a student from the active list and file layer. |
| **Shutdown** | `http://localhost:8000/exit` | Gracefully shuts down the running server application thread. |

---

## 💻 Tech Stack & Tools

* **Language:** Java 17 / 21
* **Core Concepts:** Object-Oriented Programming (OOP), ArrayList Manipulation, Multi-dimensional Array Parsing, Exception & Type Handling, File I/O Streams.
* **Networking:** `com.sun.net.httpserver.HttpServer`, `HttpHandler`, `HttpExchange`.

---

## 🏁 Getting Started

1. Clone this repository to your local workstation.
2. Open the project directory inside **IntelliJ IDEA** or your preferred Java IDE.
3. Run the `Main.java` file.
4. Follow the onscreen instructions to launch either the console interface or the live background web engine.
