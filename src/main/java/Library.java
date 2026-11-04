import exception.BookAlreadyBorrowedException;
import exception.BookNotFoundException;
import exception.ReaderNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final Map<String, Book> booksByIsbn = new HashMap<>();
    private final Map<Long, Reader> readersById = new HashMap<>();
    private final Set<Book> borrowedBooks = new HashSet<>();

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
        if (isbn == null || isbn.isBlank()) {
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

    public void borrowBook(long readerId, String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if (!booksByIsbn.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " does not exist");
        }
        if (!readersById.containsKey(readerId)) {
            throw new ReaderNotFoundException("Reader with ID " + readerId + " does not exist");
        }
        Book book = booksByIsbn.get(isbn);
        Reader reader = readersById.get(readerId);

        if (isBorrowed(book)) {
            throw new BookAlreadyBorrowedException("Book with ISBN " + isbn + " is already borrowed");
        }
        reader.borrowBook(book);
        borrowedBooks.add(book);
    }

    private boolean isBorrowed(Book book) {
        return borrowedBooks.contains(book);
    }

    public void returnBook(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or blank");
        }
        if (!booksByIsbn.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " does not exist");
        }
        Book book = booksByIsbn.get(isbn);
        findReaderByBorrowedBook(book)
                .orElseThrow(() -> new IllegalStateException("Book with ISBN " + isbn + " is not borrowed"))
                .returnBook(book);
        borrowedBooks.remove(book);
    }

    private Optional<Reader> findReaderByBorrowedBook(Book book) {
        return readersById.values().stream()
                .filter(reader -> reader.getBorrowedBooks().contains(book))
                .findFirst();
    }

    public List<Book> findAvailableBooks() {
        return booksByIsbn.values().stream()
                .filter(book -> !isBorrowed(book))
                .toList();
    }

    public List<Book> findBooksBorrowedByReader(long readerId) {
        Reader reader = readersById.get(readerId);
        if (reader == null) {
            throw new ReaderNotFoundException("Reader with ID " + readerId + " does not exist");
        }
        return reader.getBorrowedBooks();
    }

    public Map<String, Long> countBooksPerAuthor() {
        return booksByIsbn.values().stream()
                .collect(Collectors.groupingBy(Book::author, Collectors.counting()));
    }

    public int getTotalBooksCount() {
        return booksByIsbn.size();
    }

    public int getBorrowedBooksCount() {
        return readersById.values().stream()
                .mapToInt(reader -> reader.getBorrowedBooks().size())
                .sum();
    }

    public String buildReport() {
        int total = getTotalBooksCount();
        int borrowed = getBorrowedBooksCount();

        StringBuilder report = new StringBuilder();
        report.append("Library report\n");
        report.append("Books: ").append(total)
                .append(" (borrowed: ").append(borrowed)
                .append(", available: ").append(total - borrowed)
                .append(")\n");
        report.append("Readers: ").append(readersById.size()).append("\n");

        List<Reader> sortedReaders = readersById.values().stream()
                .sorted(Comparator.comparingLong(Reader::getId))
                .toList();

        for (Reader reader : sortedReaders) {
            String titles = reader.getBorrowedBooks().stream()
                    .map(Book::title)
                    .sorted()
                    .collect(Collectors.joining(", "));

            if (titles.isEmpty()) {
                titles = "(no borrowed books)";
            }

            report.append("  ").append(reader.getId()).append(" ")
                    .append(reader.getName()).append(": ").append(titles).append("\n");
        }

        return report.toString();
    }

    public Optional<Reader> findReaderById(long readerId) {
        return Optional.ofNullable(readersById.get(readerId));
    }
}
