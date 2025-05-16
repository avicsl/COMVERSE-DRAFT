package src; // Or remove this if you're not using packages at the root level

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Collections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseHandler {
 
    private static DatabaseHandler handler = null;
 
    private DatabaseHandler() {
    }
 
    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }
 
    public static Connection getDBConnection() {
        Connection connection = null;
        String dburl = "jdbc:mysql://127.0.0.1:3306/comverse?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Manila";
        String userName = "root";
        String password = "Vhina05solo02_";
        try {
            connection = DriverManager.getConnection(dburl, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return connection;
    }


//STUDENT TABLE----------------------------------------------------------------------------------------------------------------
 
 private static String loggedInStudentNumber;

    public static String getLoggedInStudentNumber() {
        return loggedInStudentNumber;
    }

    public ResultSet execQuery(String query) {
        ResultSet result = null;
        try {
            Connection conn = getDBConnection();
            Statement stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery: " + ex.getLocalizedMessage());
        }
        return result;
    }

    public boolean addUser(User user) {
        if (isEmailExists(user.getEmail())) {
            showAlert("Error", "Duplicate Entry", "Email already exists!", AlertType.ERROR);
            return false;
        }

        String query = "INSERT INTO users (email, password, first_name, last_name, department, course) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setString(5, user.getDepartment());
            pstmt.setString(6, user.getCourse());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(String oldStudentNumber, User updatedUser) {
        if (!oldStudentNumber.equals(updatedUser.getEmail()) && isEmailExists(updatedUser.getEmail())) {
            showAlert("Error", "Duplicate Entry", "Email already exists!", AlertType.ERROR);
            return false;
        }

        String query = "UPDATE users SET email = ?, password = ?, first_name = ?, last_name = ?, department = ?, course = ? WHERE student_number = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, updatedUser.getEmail());
            pstmt.setString(2, updatedUser.getPassword());
            pstmt.setString(3, updatedUser.getFirstName());
            pstmt.setString(4, updatedUser.getLastName());
            pstmt.setString(5, updatedUser.getDepartment());
            pstmt.setString(6, updatedUser.getCourse());
            pstmt.setString(7, oldStudentNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String studentNumber) {
        String query = "DELETE FROM users WHERE student_number = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validateLogin(String email, String password) {
        String query = "SELECT student_number FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                loggedInStudentNumber = result.getString("student_number");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getUserByStudentNumber(String studentNumber) {
        String query = "SELECT * FROM users WHERE student_number = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentNumber);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getUsers() {
        String query = "SELECT * FROM users";
        return execQuery(query);
    }

    private static void showAlert(String title, String header, String content, AlertType alertType) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    // Optional: remove these methods if not needed anymore
    public boolean isDepartmentExists(String dept) {
        return false; // placeholder to indicate not used anymore
    }

    public boolean isCourseExists(String course) {
        return false; // placeholder to indicate not used anymore
    }
}