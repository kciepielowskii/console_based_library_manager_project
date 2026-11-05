package library;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;

import java.util.ArrayList;
import java.util.List;

public class Reader {
    private static final int MAX_BORROWED_BOOKS = 3;

    private final long id;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public Reader(long id, String name) {
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public List<Book> getBorrowedBooks() {
        return List.copyOf(borrowedBooks);
    }

    // Package-private on purpose: only library.Library may change the loans,
    // so that its rules cannot be bypassed
    void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("library.Book must not be null");
        }
        if (borrowedBooks.contains(book)) {
            throw new BookAlreadyBorrowedException("library.Book is already borrowed by this reader");
        }
        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS) {
            throw new BorrowLimitExceededException(
                    "Usage limit: " + MAX_BORROWED_BOOKS + "/" +
                            MAX_BORROWED_BOOKS + " books borrowed");
        }
        borrowedBooks.add(book);
    }

    // Package-private on purpose: only library.Library may change the loans,
    // so that its rules cannot be bypassed
    void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("library.Book must not be null");
        }
        if (!borrowedBooks.contains(book)) {
            throw new BookNotFoundException("library.Book not found");
        }
        borrowedBooks.remove(book);
    }
}
