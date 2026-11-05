package library;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {
    @Test
    public void shouldCreateReaderSuccessfully() {
        Reader reader = new Reader(
                1,
                "Alice"
        );

        assertEquals(1, reader.getId());
        assertEquals("Alice", reader.getName());
    }

    @Test
    public void shouldThrowExceptionWhenIdIsZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reader(0,"Alice"));
    }

    @Test
    public void shouldThrowExceptionWhenIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reader(-1,"Alice"));
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reader(1,null));
    }

    @Test
    public void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Reader(1,"     "));
    }

    @Test
    public void shouldBorrowBookSuccessfully() {
        Reader reader = new Reader(1, "Alice");
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        reader.borrowBook(book);
        assertTrue(reader.getBorrowedBooks().contains(book));
    }

    @Test
    public void shouldThrowExceptionWhenBorrowingNullBook() {
        Reader reader = new Reader(1, "Alice");
        assertThrows(IllegalArgumentException.class, () ->
                reader.borrowBook(null));
    }

    @Test
    public void shouldThrowExceptionWhenBorrowingSameBookTwice() {
        Reader reader = new Reader(1, "Alice");
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        reader.borrowBook(book);
        assertThrows(BookAlreadyBorrowedException.class, () ->
                reader.borrowBook(book));
    }

    @Test
    public void shouldThrowExceptionWhenBorrowingMoreThanThreeBooks() {
        Reader reader = new Reader(1, "Alice");
        Book book1 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        Book book2 = new Book(
                "978-0141439662",
                "Sense and Sensibility",
                "Jane Austen",
                2003
        );
        Book book3 = new Book(
                "978-0141439587",
                "Emma",
                "Jane Austen",
                2003
        );
        Book book4 = new Book(
                "978-0141439877",
                "Persuasion",
                "Jane Austen",
                2003
        );
        reader.borrowBook(book1);
        reader.borrowBook(book2);
        reader.borrowBook(book3);
        assertThrows(BorrowLimitExceededException.class, () ->
                reader.borrowBook(book4));
        assertEquals(3, reader.getBorrowedBooks().size());
    }

    @Test
    public void shouldReturnBookSuccessfully() {
        Reader reader = new Reader(1, "Alice");
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        reader.borrowBook(book);
        reader.returnBook(book);
        assertFalse(reader.getBorrowedBooks().contains(book));
    }

    @Test
    public void shouldThrowExceptionWhenReturningNullBook() {
        Reader reader = new Reader(1, "Alice");
        assertThrows(IllegalArgumentException.class, () ->
                reader.returnBook(null));
    }

    @Test
    public void shouldThrowExceptionWhenReturningBookNotBorrowedByReader() {
        Reader reader = new Reader(1, "Alice");
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        assertThrows(BookNotFoundException.class, () ->
                reader.returnBook(book));
    }

    @Test
    public void shouldReturnUnmodifiableCopyOfBorrowedBooks() {
        Reader reader = new Reader(1, "Alice");
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        reader.borrowBook(book);
        List<Book> borrowedBooks = reader.getBorrowedBooks();
        assertThrows(UnsupportedOperationException.class, () ->
                borrowedBooks.remove(book));
        assertTrue(reader.getBorrowedBooks().contains(book));
    }
}