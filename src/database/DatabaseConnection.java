package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//public class DatabaseConnection {
//    private static final String DB_URL = "jdbc:sqlserver://192.168.1.16:1433;databaseName=JAVATANK";
//    private static final String USER = "sa";
//    private static final String PASS = "1";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, USER, PASS);
//    }
//}
public class DatabaseConnection {
    String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=JAVATANK;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "1";
    private Connection connection;

    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Đảm bảo rằng driver được tải
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.INFO, "Connection established successfully.");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "Error: Driver class not found", e);
        } catch (SQLException e) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, "Error: SQL exception", e);
        }
        return connection;
    }
}
