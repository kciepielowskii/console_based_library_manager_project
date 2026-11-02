import exception.BookAlreadyBorrowedException;
import exception.BookNotFoundException;
import exception.BorrowLimitExceededException;
import exception.ReaderNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    @Test
    public void shouldAddBookSuccessfully() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        assertDoesNotThrow(() -> library.addBook(book));
    }
    @Test
    public void shouldThrowExceptionWhenAddingDuplicateBook() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        library.addBook(book);
        Book theSameBook = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        assertThrows(IllegalStateException.class, () -> library.addBook(theSameBook));
    }
    @Test
    public void shouldThrowExceptionWhenAddingNullBook() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () -> library.addBook(null));
    }
    @Test
    public void shouldRegisterReaderSuccessfully() {
        Library library = new Library();
        Reader reader = new Reader(
                1,
                "Alice"
        );
        assertDoesNotThrow(() -> library.registerReader(reader));
    }
    @Test
    public void shouldThrowExceptionWhenRegisteringDuplicateReader() {
        Library library = new Library();
        Reader reader = new Reader(
                1,
                "Alice"
        );
        library.registerReader(reader);
        Reader theSameReader = new Reader(
                1,
                "Alice"
        );
        assertThrows(IllegalStateException.class, () -> library.registerReader(theSameReader));
    }
    @Test
    public void shouldThrowExceptionWhenRegisteringNullReader() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () -> library.registerReader(null));
    }
    @Test
    public void shouldFindBookByIsbnSuccessfully() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        library.addBook(book);
        assertEquals(
                book,
                library.findBookByIsbn("978-0141439518").orElseThrow()
        );
    }
    @Test
    public void shouldThrowExceptionWhenIsbnIsNull() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        library.addBook(book);
        assertThrows(
                IllegalArgumentException.class, () -> library.findBookByIsbn(null)
        );
    }
    @Test
    public void shouldThrowExceptionWhenIsbnIsBlank() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518" ,
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        library.addBook(book);
        assertThrows(
                IllegalArgumentException.class, () -> library.findBookByIsbn("     ")
        );
    }
    @Test
    public void shouldReturnEmptyWhenBookDoesNotExist() {
        Library library = new Library();
        assertTrue(library.findBookByIsbn("978-0141439518").isEmpty());
    }
    @Test
    public void shouldFindBooksByAuthorSuccessfully() {
        Library library = new Library();
        Book book1 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        Book book2 = new Book(
                "978-0141439662",
                "Sense and Sensibility",
                "Jane Austen",
                2003);
        Book book3 = new Book(
                "978-0141439587",
                "Emma",
                "Jane Austen",
                2003);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        assertEquals(3, library.findBooksByAuthor("Jane Austen").size());
    }
    @Test
    public void shouldReturnBooksSortedByTitle() {
        Library library = new Library();
        Book book1 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        Book book2 = new Book(
                "978-0141439662",
                "Sense and Sensibility",
                "Jane Austen",
                2003);
        Book book3 = new Book(
                "978-0141439587",
                "Emma",
                "Jane Austen",
                2003);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book1);
        assertEquals(List.of(book3, book1, book2),
                library.findBooksByAuthor("Jane Austen"));
    }
    @Test
    public void shouldReturnEmptyListWhenAuthorHasNoBooks() {
        Library library = new Library();
        assertTrue(library.findBooksByAuthor("Jane Austen").isEmpty());
    }

    @Test
    public void shouldThrowExceptionWhenAuthorIsNull() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.findBooksByAuthor(null));
    }
    @Test
    public void shouldThrowExceptionWhenAuthorIsBlank() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.findBooksByAuthor("   "));
    }
    @Test
    public void shouldThrowExceptionWhenBorrowingBookWithNullIsbn() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.borrowBook(1, null));
    }
    @Test
    public void shouldThrowExceptionWhenBorrowingBookWithBlankIsbn() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.borrowBook(1, "     "));
    }
    @Test
    public void shouldThrowExceptionWhenBorrowingNonExistentBook() {
        Library library = new Library();
        assertThrows(BookNotFoundException.class, () ->
                library.borrowBook(1, "978-0141439587"));
    }
    @Test
    public void shouldThrowExceptionWhenBorrowingNonExistentReader() {
        Library library = new Library();
        library.addBook(new Book(
                "978-0141439587",
                "Pride and Prejudice",
                "Jane Austen",
                2002));
        assertThrows(ReaderNotFoundException.class, () ->
                library.borrowBook(2, "978-0141439587"));
    }
    @Test
    public void shouldThrowExceptionWhenBorrowingAnAlreadyBorrowedBook() {
        Library library = new Library();
        library.addBook(new Book(
                "978-0141439587",
                "Pride and Prejudice",
                "Jane Austen",
                2002));
        library.registerReader(new Reader(1, "Alice"));
        library.registerReader(new Reader(2, "Bob"));
        library.borrowBook(1, "978-0141439587");
        assertThrows(BookAlreadyBorrowedException.class, () ->
                library.borrowBook(2, "978-0141439587"));
    }
    @Test
    public void shouldThrowExceptionWhenReaderExceedsBorrowingLimit() {
        Library library = new Library();
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
        library.registerReader(new Reader(1, "Alice"));
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.borrowBook(1, "978-0141439518");
        library.borrowBook(1, "978-0141439662");
        library.borrowBook(1, "978-0141439587");
        assertThrows(BorrowLimitExceededException.class, () ->
                library.borrowBook(1, "978-0141439877"));
    }
    @Test
    public void shouldBorrowBookSuccessfully() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        Reader reader = new Reader(1, "Alice");
        library.registerReader(reader);
        library.addBook(book);
        assertDoesNotThrow(() -> library.borrowBook(1, "978-0141439518"));
        assertTrue(reader.getBorrowedBooks().contains(book));
    }
    @Test
    public void shouldReturnBookSuccessfully() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        Reader reader = new Reader(1, "Alice");
        library.addBook(book);
        library.registerReader(reader);
        library.borrowBook(1, "978-0141439518");
        assertDoesNotThrow(() -> library.returnBook("978-0141439518"));
        assertFalse(reader.getBorrowedBooks().contains(book));
    }
    @Test
    public void shouldThrowExceptionWhenReturningBookWithNullIsbn() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.returnBook(null));
    }
    @Test
    public void shouldThrowExceptionWhenReturningBookWithBlankIsbn() {
        Library library = new Library();
        assertThrows(IllegalArgumentException.class, () ->
                library.returnBook("     "));
    }
    @Test
    public void shouldThrowExceptionWhenReturningNonExistentBook() {
        Library library = new Library();
        assertThrows(BookNotFoundException.class, () ->
                library.returnBook("978-0141439518"));
    }
    @Test
    public void shouldThrowExceptionWhenReturningBookThatIsNotBorrowed() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        library.addBook(book);
        assertThrows(IllegalStateException.class, () ->
                library.returnBook("978-0141439518"));
    }
    @Test
    public void shouldBorrowBookAgainAfterReturningIt() {
        Library library = new Library();
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002
        );
        Reader reader = new Reader(1, "Alice");
        library.addBook(book);
        library.registerReader(reader);
        library.borrowBook(1, "978-0141439518");
        library.returnBook("978-0141439518");
        assertDoesNotThrow(() -> library.borrowBook(1, "978-0141439518"));
        assertTrue(reader.getBorrowedBooks().contains(book));
    }
    @Test
    public void shouldFindAllAvailableBooks() {
        Library library = new Library();
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
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.findAvailableBooks().size());
        assertTrue(library.findAvailableBooks().contains(book1));
        assertTrue(library.findAvailableBooks().contains(book2));
    }
    @Test
    public void shouldExcludeBorrowedBooks() {
        Library library = new Library();
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
        Reader reader = new Reader(1, "Alice");
        library.addBook(book1);
        library.addBook(book2);
        library.registerReader(reader);
        library.borrowBook(1, "978-0141439518");
        assertFalse(library.findAvailableBooks().contains(book1));
        assertTrue(library.findAvailableBooks().contains(book2));
    }
    @Test
    public void shouldReturnEmptyListWhenNoBooksAreAvailable() {
        Library library = new Library();
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
        Reader reader = new Reader(1, "Alice");
        library.addBook(book1);
        library.addBook(book2);
        library.registerReader(reader);
        library.borrowBook(1, "978-0141439518");
        library.borrowBook(1, "978-0141439662");
        assertTrue(library.findAvailableBooks().isEmpty());
    }
    @Test
    public void shouldReturnEmptyListWhenLibraryIsEmpty() {
        Library library = new Library();
        assertTrue(library.findAvailableBooks().isEmpty());
    }
}
