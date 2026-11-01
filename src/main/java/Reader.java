import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final Long id;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public Reader(Long id, String name) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0 and cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        this.id = id;
        this.name = name;
    }
}
