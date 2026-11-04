import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean running = true;

        while (running) {

            System.out.println("==============================");
            System.out.println("     LIBRARY MANAGEMENT");
            System.out.println("==============================");

            System.out.println("1. Sign in");
            System.out.println("2. Exit program");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println("Enter ID: ");
                        long id = Long.parseLong(scanner.nextLine());
                        
                        Reader reader;
                        Optional<Reader> foundReader = library.findReaderById(id);
                        if (foundReader.isPresent()) {
                            reader = foundReader.get();
                        } else {
                            System.out.println("New reader. Enter Name: ");
                            String name = scanner.nextLine();
                            reader = new Reader(id, name);
                            library.registerReader(reader);
                        }

                        boolean loggedIn = true;

                        while (loggedIn) {

                            System.out.println("\n==============================");
                            System.out.println("     READER MENU");
                            System.out.println("==============================");
                            System.out.println("1. Add book");
                            System.out.println("2. Borrow book");
                            System.out.println("3. Return book");
                            System.out.println("4. Search book by ISBN");
                            System.out.println("5. Search books by author");
                            System.out.println("6. List available books");
                            System.out.println("7. My borrowed books");
                            System.out.println("8. Library statistics");
                            System.out.println("9. Library report");
                            System.out.println("10. Log out");

                            try {
                                int option = Integer.parseInt(scanner.nextLine());

                                switch (option) {

                                    case 1:
                                        System.out.println("Enter book ISBN: ");
                                        String isbn = scanner.nextLine();

                                        System.out.println("Enter book title: ");
                                        String title = scanner.nextLine();

                                        System.out.println("Enter book author: ");
                                        String author = scanner.nextLine();

                                        System.out.println("Enter book year: ");
                                        int year = Integer.parseInt(scanner.nextLine());

                                        library.addBook(new Book(isbn, title, author, year));
                                        System.out.println("Book added successfully.");
                                        break;

                                    case 2:
                                        System.out.println("Enter book ISBN: ");
                                        String borrowIsbn = scanner.nextLine();

                                        library.borrowBook(reader.getId(), borrowIsbn);
                                        System.out.println("Book borrowed successfully.");
                                        break;

                                    case 3:
                                        System.out.println("Enter book ISBN: ");
                                        String returnIsbn = scanner.nextLine();

                                        library.returnBook(returnIsbn);
                                        System.out.println("Book returned successfully.");
                                        break;

                                    case 4:
                                        System.out.println("Enter book ISBN: ");
                                        String searchIsbn = scanner.nextLine();

                                        Optional<Book> foundBook = library.findBookByIsbn(searchIsbn);
                                        if (foundBook.isPresent()) {
                                            Book book = foundBook.get();
                                            System.out.println("Found: " + book.title() + " by " + book.author() + " (" + book.year() + ")");
                                        } else {
                                            System.out.println("No book found with ISBN: " + searchIsbn);
                                        }
                                        break;

                                    case 5:
                                        System.out.println("Enter author name: ");
                                        String searchAuthor = scanner.nextLine();

                                        List<Book> booksByAuthor = library.findBooksByAuthor(searchAuthor);
                                        if (booksByAuthor.isEmpty()) {
                                            System.out.println("No books found by author: " + searchAuthor);
                                        } else {
                                            System.out.println("Books by " + searchAuthor + ":");
                                            for (Book book : booksByAuthor) {
                                                System.out.println("  - " + book.title() + " (" + book.year() + ")");
                                            }
                                        }
                                        break;

                                    case 6:
                                        List<Book> availableBooks = library.findAvailableBooks();
                                        if (availableBooks.isEmpty()) {
                                            System.out.println("No available books.");
                                        } else {
                                            System.out.println("Available books:");
                                            for (Book book : availableBooks) {
                                                System.out.println("  - " + book.title() + " by " + book.author() + " [" + book.isbn() + "]");
                                            }
                                        }
                                        break;

                                    case 7:
                                        List<Book> myBooks = library.findBooksBorrowedByReader(reader.getId());
                                        if (myBooks.isEmpty()) {
                                            System.out.println("You have no borrowed books.");
                                        } else {
                                            System.out.println("Your borrowed books:");
                                            for (Book book : myBooks) {
                                                System.out.println("  - " + book.title() + " by " + book.author() + " [" + book.isbn() + "]");
                                            }
                                        }
                                        break;

                                    case 8:
                                        System.out.println("=== Library Statistics ===");
                                        System.out.println("Total books: " + library.getTotalBooksCount());
                                        System.out.println("Borrowed books: " + library.getBorrowedBooksCount());
                                        System.out.println("Available books: " + (library.getTotalBooksCount() - library.getBorrowedBooksCount()));

                                        Map<String, Long> booksPerAuthor = library.countBooksPerAuthor();
                                        if (!booksPerAuthor.isEmpty()) {
                                            System.out.println("\nBooks per author:");
                                            for (Map.Entry<String, Long> entry : booksPerAuthor.entrySet()) {
                                                System.out.println("  - " + entry.getKey() + ": " + entry.getValue());
                                            }
                                        }
                                        break;

                                    case 9:
                                        System.out.print(library.buildReport());
                                        break;

                                    case 10:
                                        loggedIn = false;
                                        System.out.println("Logging out...");
                                        break;

                                    default:
                                        System.out.println("Invalid option!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: a number is required");
                            } catch (RuntimeException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                        break;

                    case 2:
                        running = false;
                        System.out.println("Exiting program...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: a number is required");
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}