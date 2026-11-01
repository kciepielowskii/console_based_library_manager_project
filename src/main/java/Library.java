import java.util.*;
import java.util.Comparator;

public class Library {
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<Long, Reader> readersById = new HashMap<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (booksByIsbn.containsKey(book.isbn())) {
            throw new IllegalStateException("Book with ISBN " + book.isbn() + " already exists");
        }
        booksByIsbn.put(book.isbn(), book);
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

    public Optional<Book> findBookByIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        return Optional.ofNullable(booksByIsbn.get(isbn));
    }

    public List<Book> findBooksByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        return booksByIsbn.values().stream()
                .filter(book -> book.author().equals(author))
                .sorted(Comparator.comparing(Book::title))
                .toList();
    }
}
