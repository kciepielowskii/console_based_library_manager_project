import org.junit.jupiter.api.Test;

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
}