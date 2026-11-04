import exception.BookAlreadyBorrowedException;
import exception.BookNotFoundException;
import exception.BorrowLimitExceededException;

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

    public void borrowBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        if (borrowedBooks.contains(book)) {
            throw new BookAlreadyBorrowedException("Book is already borrowed by this reader");
        }
        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS) {
            throw new BorrowLimitExceededException(
                    "Usage limit: " + MAX_BORROWED_BOOKS + "/" +
                            MAX_BORROWED_BOOKS + " books borrowed");
        }
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        if (!borrowedBooks.contains(book)) {
            throw new BookNotFoundException("Book not found");
        }
        borrowedBooks.remove(book);
    }
}
