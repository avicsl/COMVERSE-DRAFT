package UserControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import model.DatabaseHandler;
import Class.User;             // ✅ this line is critical
import src.DatabaseHandler; 

public class UserSignupController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField departmentField;
    @FXML private TextField courseField;
    @FXML private Button signUpButton;

    @FXML
    private void handleSignUp() {
        if (!validateInputs()) return;

        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String department = departmentField.getText().trim();
        String course = courseField.getText().trim();

        DatabaseHandler dbHandler = DatabaseHandler.getDBConnection();

        if (dbHandler.isEmailExists(email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", null, "Email already exists. Please use a different email.");
            return;
        }

        User newUser = new User(email, password, firstName, lastName, department, course);

        boolean success = dbHandler.addUser(newUser);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", null, "User registered successfully!");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed", null, "Failed to register user.");
        }
    }

    private boolean validateInputs() {
        if (emailField.getText().isEmpty() ||
            passwordField.getText().isEmpty() ||
            firstNameField.getText().isEmpty() ||
            lastNameField.getText().isEmpty() ||
            departmentField.getText().isEmpty() ||
            courseField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", null, "Please fill in all fields.");
            return false;
        }

        if (!emailField.getText().endsWith("@gmail.com")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", null, "Email must end with @gmail.com!");
            return false;
        }

        return true;
    }

    private void clearFields() {
        emailField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        departmentField.clear();
        courseField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
