import org.junit.jupiter.api.Test;

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
}
