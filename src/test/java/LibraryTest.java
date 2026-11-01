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
    public void shouldReturnAuthorBooksSuccessfully() {
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
}
