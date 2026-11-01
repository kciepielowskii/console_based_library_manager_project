import java.util.HashMap;
import java.util.Map;

public class Library {
    private final Map<String, Book> booksByIsbn = new HashMap<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (booksByIsbn.containsKey(book.ISBN())) {
            throw new IllegalStateException("Book with ISBN " + book.ISBN() + " already exists");
        }
        booksByIsbn.put(book.ISBN(), book);
    }
}
