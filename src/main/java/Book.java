import java.time.Year;

public record Book(String isbn, String title, String author, int year) {
    public Book {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN must not be null or blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be null or blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author must not be null or blank");
        }
        int currentYear = Year.now().getValue();
        if (year < 1 || year > currentYear) {
            throw new IllegalArgumentException("Year must be between 1 and " + currentYear);
        }
    }
}
