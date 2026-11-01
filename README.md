# Console-Based Library Management System

A console-based library management application written in Java.

The purpose of this project is to practice object-oriented programming, Java collections, exception handling, Stream API, Optional, and data validation by building a library management system from scratch.

The project is developed as part of my Java learning journey.

---

## 1. Project Overview

The application simulates a simple library management system.

It allows users to manage books and readers, borrow and return books, search for books, and generate reports about the current state of the library.

The application runs in the console and focuses on correct business logic, clean code, and proper object-oriented design.

## 2. Functional Requirements

### 2.1 Book Management

- Add a new book to the library.
- Store the following information:
    - ISBN
    - Title
    - Author
    - Publication year
- Prevent adding books with duplicate ISBN numbers.
- Validate all book attributes.

### 2.2 Reader Management

- Register new readers.
- Store the following information:
    - Reader ID
    - Reader name
- Prevent registering readers with duplicate IDs.
- Validate reader information.

### 2.3 Book Borrowing

Readers can borrow books if all conditions are met:

- The book exists in the library.
- The reader exists in the library.
- The book is currently available.
- The reader has fewer than 3 borrowed books.

The system must handle invalid borrowing attempts using appropriate exceptions.

### 2.4 Book Returning

Readers can return previously borrowed books.

The system must handle the following situations:

- The requested book does not exist.
- The book is not currently borrowed.

After a successful return, the book becomes available again.

### 2.5 Searching and Filtering

The application provides the following search operations:

- Find a book by ISBN using `Optional`.
- Find all books written by a specific author, sorted alphabetically by title.
- Display all currently available books.
- Display all books borrowed by a specific reader.
- Count the number of books written by each author using `Map<String, Long>`.

The implementation should use Java Stream API where appropriate.

### 2.6 Library Report

Generate a report containing:

- Total number of books.
- Number of currently borrowed books.
- Number of available books.
- List of readers and their currently borrowed books.

---

## 3. Technical Requirements

The project should demonstrate practical use of the following Java features:

| Feature | Purpose |
|---|---|
| Records | Represent immutable data objects such as `Book` |
| Classes | Represent objects with changing state, such as `Reader` |
| Encapsulation | Protect internal application data |
| Collections | Store books, readers, and borrowing information |
| Map | Enable efficient lookup by ISBN and reader ID |
| Set | Ensure uniqueness where appropriate |
| Custom Exceptions | Handle specific business rule violations |
| Stream API | Search, filter, sort, and aggregate data |
| Optional | Represent potentially missing search results |
| LocalDate | Optionally manage borrowing and return dates |

### Data Validation

All constructor arguments and method parameters must be validated.

Validation should cover:

- Null values.
- Empty or blank strings.
- Negative publication years.
- Invalid identifiers.
- Invalid borrowing and returning operations.

Exception messages should be written in English.

### Encapsulation

- Internal collections must be private.
- Methods should not expose mutable internal collections.
- Return defensive copies using `List.copyOf()` or appropriate unmodifiable views.

---

## 4. Initial Design Assumptions

The application will initially be designed around three main classes:

### Book
- Represented as a Java record.
- Contains ISBN, title, author, and publication year.
- Immutable after creation.
- Responsible for validating its own data.

### Reader
- Represented as a regular Java class.
- Contains reader ID and name.
- Manages information about the reader's borrowed books.
- Enforces the maximum borrowing limit of 3 books.

### Library
- Manages the book catalog and registered readers.
- Responsible for checking book availability.
- Coordinates borrowing and returning operations.
- Provides searching, filtering, and reporting functionality.

The final class structure may evolve during implementation as the project develops.

---

## 5. Testing

The application will include manual test scenarios in the `main` method.

Tests should verify actual results and print `OK` or `ERROR` depending on whether the expected result was achieved.

Important test scenarios:

- Adding a book successfully.
- Attempting to add a book with an existing ISBN.
- Registering a reader successfully.
- Attempting to register a reader with an existing ID.
- Borrowing an available book.
- Attempting to borrow a book that does not exist.
- Attempting to borrow an already borrowed book.
- Attempting to borrow a fourth book by the same reader.
- Returning a borrowed book.
- Borrowing the same book again after it has been returned.
- Searching for books by an existing author.
- Searching for books by a non-existing author.
- Searching in an empty library.

---

## 6. Optional Features

The project may be extended with borrowing dates and return deadlines using `java.time.LocalDate`.

Planned functionality:

- Store the borrowing date.
- Automatically calculate the return deadline (14 days).
- Identify overdue books.
- Display a list of overdue books.

---

## 7. Development Goals

The main learning objectives are:

- Improve object-oriented programming skills.
- Practice designing classes without a predefined structure.
- Understand encapsulation and separation of responsibilities.
- Gain practical experience with Java collections.
- Use Stream API and Optional in real-world scenarios.
- Implement custom exceptions.
- Practice Git and GitHub workflows.
- Write readable, maintainable, and testable code.

---

## 8. Project Status

<ins>In development</ins>

The project is being implemented incrementally. Each logical feature is developed, tested, and committed separately.

