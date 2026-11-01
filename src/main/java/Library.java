import java.util.HashMap;
import java.util.Map;

public class Library {
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<Long, Reader> readersById = new HashMap<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (booksByIsbn.containsKey(book.ISBN())) {
            throw new IllegalStateException("Book with ISBN " + book.ISBN() + " already exists");
        }
        booksByIsbn.put(book.ISBN(), book);
    }

    public void registerReader(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }
        if (readersById.containsKey(reader.getId())) {
            throw new IllegalStateException("Reader with ID " + reader.getId() + " already exists");
        }
        readersById.put(reader.getId(), reader);
    }
}
