
This project was developed as part of a university course on System Programming Language (SPL).

In this project, we implemented a Course Registration System with a server-client architecture.
The communication between the server and the clients is performed using a custom binary protocol over TCP/IP.

Communication Protocol

The protocol defines binary messages, each beginning with an opcode (a 2-byte short number) indicating the command type.
Following the opcode, each message includes additional data fields, depending on the specific command.
Each client request expects either an ACK (acknowledgment) or an ERROR response from the server.

Supported client commands include:
- Registration and login (Admin/Student)
- Course registration and unregistration
- Viewing course prerequisites (Kdam courses)
- Querying course or student status
- Checking registered courses
- Logging out

Client-Server Connection

Client Side

The client side was implemented in C++ using Boost::asio and follows these steps:
- Establish a TCP connection with the server.
- Register a new user (admin or student) or log in as an existing user.
- Send encoded binary commands to the server.
- Receive and decode server responses (ACK or ERROR).

The client uses two threads:
- One thread reads user input (keyboard commands).
- Another thread listens for server responses asynchronously.

Messages are translated between text commands and binary network messages via a dedicated encoder/decoder.

Server Side

The server side was implemented in Java and supports two concurrency models:
- Thread-Per-Client (TPC): A new thread is spawned for each incoming client connection.
- Reactor: A selector-based model handling multiple clients concurrently with a fixed-size thread pool.

On startup, the server:
- Loads course information from a Courses.txt file.
- Initializes a singleton database.
- Creates the necessary protocol and encoder/decoder components.
- Starts handling incoming client requests according to the selected concurrency model.

Server Implementation Details

TPC Mode:
- Creates a server socket.
- For each new connection, opens a new thread (ConnectionHandler) to handle communication.
- Each handler decodes incoming messages, processes protocol commands, and sends back responses.

Reactor Mode:
- Opens a selector-based server socket.
- Manages client sockets using a fixed thread pool.
- Tasks are queued and processed asynchronously as threads become available.

Both models interact with the same thread-safe database.

Database

The system uses a thread-safe singleton database to maintain:
- Registered students and administrators.
- Courses with course numbers, names, prerequisites (kdam courses), and seat availability.
- Student course registration records.

The database ensures safe concurrent access across multiple threads and clients.

User Classes

- User (abstract class): Holds the common fields (username, password, login status).
- Student (inherits User): Tracks registered courses.
- Admin (inherits User): Manages course and student queries but cannot register for courses.

Purpose

The purpose of this project was to practice implementing network communication and concurrency using Java and C++.
The project required a strong understanding of:
- Threading and synchronization
- TCP networking and socket communication
- Protocol design (binary protocol)
- Server architecture patterns (TPC and Reactor)
- Client-side and server-side concurrency handling
