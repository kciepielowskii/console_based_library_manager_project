import java.time.Year;

public record Book(String ISBN, String title, String author, int year) {
    public Book {
        if (ISBN == null || ISBN.isBlank()) {
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
