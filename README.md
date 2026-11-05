# Console-Based library.Library Management System

A console application for managing a small library, written in Java. It was built as a learning project to practice object-oriented design, collections, the Stream API, `Optional`, custom exceptions, data validation and automated testing.

## Features

- Add books (unique ISBN) and register readers (unique ID), with validation of all input.
- Borrow a book: the book and the reader must exist, the book must be available and the reader can have at most 3 books.
- Return a book.
- Search: find a book by ISBN (`Optional`), find books by author (sorted by title), list available books, list books borrowed by a reader, count books per author.
- library.Library statistics and a text report of all readers with their borrowed books.
- Interactive console menu with friendly error messages (invalid numbers and business rule violations never crash the program).

## Tech stack

| Area | Used |
|---|---|
| Language | Java 17+ (records, Stream API, `Optional`) |
| Build | Maven |
| Tests | JUnit 5 |

## How to run

Requirements: JDK 17 or newer and Maven (IntelliJ IDEA has Maven built in).

```bash
# run all tests
mvn test

# compile and start the console application
mvn compile
java -cp target/classes library.Main
```

In IntelliJ IDEA: open the project folder (the `pom.xml` is detected automatically), then run `Main` or use the Maven window: `Lifecycle` → `test`.

## Using the application

The main menu lets you sign in or exit. Signing in with an existing ID logs the reader in, a new ID registers a new reader (the name is asked for). After signing in, the reader menu offers: add a book, borrow, return, search by ISBN, search by author, list available books, my borrowed books, library statistics, library report and log out.

Example report:

```
library.Library report
Books: 2 (borrowed: 1, available: 1)
Readers: 2
  1 Alice: Emma
  2 Bob: (no borrowed books)
```

## Project structure

```
pom.xml
src/
├── main/java/
│   ├── library.Book.java        — immutable record (ISBN, title, author, year) with validation
│   ├── library.Reader.java      — reader with borrowed books (max 3)
│   ├── library.Library.java     — catalog, readers, borrowing, searching, statistics, report
│   ├── library.Main.java        — interactive console menu
│   └── exception/
│       ├── BookAlreadyBorrowedException.java
│       ├── BookNotFoundException.java
│       ├── BorrowLimitExceededException.java
│       └── ReaderNotFoundException.java
└── test/java/
    ├── library.BookTest.java
    ├── library.ReaderTest.java
    └── library.LibraryTest.java
```

## Design decisions

- **`Book` is a record.** It is immutable data, and its compact constructor validates every field (non-blank texts, year between 1 and the current year).
- **`Reader` is a class** because its state changes. `borrowBook` and `returnBook` are package-private on purpose: only `Library` can change loans, so its rules (the book exists, nobody else has it) cannot be bypassed.
- **`Library` is the single coordinator.** Books and readers are stored in maps (lookup by ISBN and by ID), and a `Set<Book>` of borrowed books gives a constant-time availability check.
- **Encapsulation.** Internal collections are private, and getters return defensive copies (`List.copyOf`).
- **Exceptions.** Business rule violations use dedicated unchecked exceptions with clear English messages. Invalid arguments throw `IllegalArgumentException`.

## Testing

The project has 72 automated JUnit 5 tests:

| Test class | Tests | What is covered |
|---|---|---|
| `BookTest` | 13 | field validation, year boundaries, equals/hashCode |
| `ReaderTest` | 13 | constructor validation, borrowing, returning, the 3-book limit, immutability of the returned list |
| `LibraryTest` | 46 | adding and registering, searching, borrow/return flow, available books, statistics, report |

Run them with `mvn test`.

## Known limitations

- Data is kept in memory only and disappears when the program ends.
- Returning a book is not tied to the reader: any logged-in reader can return any borrowed book.
- All classes are in the default package.

## Possible next steps

- Borrowing dates, a 14-day return deadline and a list of overdue books (`java.time.LocalDate`).
- Persistence in a database and a REST API (Spring Boot).