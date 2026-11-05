package library;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    public void shouldCreateBookSuccessfully() {
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2008
        );

        assertEquals("978-0141439518", book.isbn());
        assertEquals("Pride and Prejudice", book.title());
        assertEquals("Jane Austen", book.author());
        assertEquals(2008, book.year());
    }

    @Test
    public void shouldThrowExceptionWhenIsbnIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book(null,
                        "Pride and Prejudice",
                        "Jane Austen",
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        null,
                        "Jane Austen",
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenAuthorIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        "Pride and Prejudice",
                        null,
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenIsbnIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("     ",
                        "Pride and Prejudice",
                        "Jane Austen",
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        "     ",
                        "Jane Austen",
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenAuthorIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        "Pride and Prejudice",
                        "     ",
                        2026
                ));
    }

    @Test
    public void shouldThrowExceptionWhenYearIsBelowOne() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        "Pride and Prejudice",
                        "Jane Austen",
                        0
                ));
    }

    @Test
    public void shouldThrowExceptionWhenYearIsFromTheFuture() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("978-0141439518",
                        "Pride and Prejudice",
                        "Jane Austen",
                        2100
                ));
    }

    @Test
    public void shouldAcceptYearEqualToOne() {
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                1
        );
        assertEquals(1, book.year());
    }

    @Test
    public void shouldAcceptCurrentYear() {
        int currentYear = Year.now().getValue();
        Book book = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                currentYear
        );
        assertEquals(currentYear, book.year());
    }

    @Test
    public void shouldConsiderTwoBooksEqualWhenAllFieldsMatch() {
        Book book1 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        Book book2 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    public void shouldConsiderTwoBooksNotEqualWhenOneFieldDiffers() {
        Book book1 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2002);
        Book book2 = new Book(
                "978-0141439518",
                "Pride and Prejudice",
                "Jane Austen",
                2003);
        assertNotEquals(book1, book2);
    }
}