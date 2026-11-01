import java.util.ArrayList;
import java.util.List;

public class Reader {
    private final long id;
    private final String name;
    private final List<Book> borrowedBooks = new ArrayList<>();

    public Reader(long id, String name) {
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
