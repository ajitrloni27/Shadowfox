import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static final String DB_URL = "jdbc:sqlite:library.db";

    // ================= DATABASE CONNECTION =================
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() {
        String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL)";

        String bookTable = "CREATE TABLE IF NOT EXISTS books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "author TEXT NOT NULL)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(userTable);
            stmt.execute(bookTable);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void registerUser(Scanner sc) {
        try (Connection conn = connect()) {

            System.out.print("Enter username: ");
            String username = sc.nextLine();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty");
                return;
            }

            System.out.print("Enter password: ");
            String password = sc.nextLine();
            if (password.length() < 4) {
                System.out.println("Password must be at least 4 characters");
                return;
            }

            String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

            System.out.println("User registered successfully");

        } catch (SQLException e) {
            System.out.println("Username already exists");
        }
    }

    // ================= USER LOGIN =================
    public static boolean loginUser(Scanner sc) {
        try (Connection conn = connect()) {

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful");
                return true;
            } else {
                System.out.println("Invalid credentials");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Login error");
            return false;
        }
    }

    // ================= ADD BOOK =================
    public static void addBook(Scanner sc) {
        try (Connection conn = connect()) {

            System.out.print("Enter book title: ");
            String title = sc.nextLine();
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty");
                return;
            }

            System.out.print("Enter author name: ");
            String author = sc.nextLine();
            if (author.isEmpty()) {
                System.out.println("Author cannot be empty");
                return;
            }

            String sql = "INSERT INTO books(title, author) VALUES(?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.executeUpdate();

            System.out.println("Book added successfully");

        } catch (SQLException e) {
            System.out.println("Error adding book");
        }
    }

    // ================= VIEW BOOKS =================
    public static void viewBooks() {
        try (Connection conn = connect()) {

            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Book List:");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching books");
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        createTables();

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                registerUser(sc);
            } else if (choice == 2) {
                if (loginUser(sc)) {
                    while (true) {
                        System.out.println("\n1. Add Book");
                        System.out.println("2. View Books");
                        System.out.println("3. Logout");
                        System.out.print("Choice: ");

                        int opt = sc.nextInt();
                        sc.nextLine();

                        if (opt == 1) addBook(sc);
                        else if (opt == 2) viewBooks();
                        else break;
                    }
                }
            } else {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
    }
}
