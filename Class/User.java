package Class;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty studentNumber;
    private StringProperty email;
    private StringProperty password;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty department;
    private StringProperty course;

    public User(String studentNumber, String email, String password, String firstName, String lastName, String department, String course) {
        this.studentNumber = new SimpleStringProperty(studentNumber);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.department = new SimpleStringProperty(department);
        this.course = new SimpleStringProperty(course);
    }

    // Getters
    public String getStudentNumber() { return studentNumber.get(); }
    public String getEmail() { return email.get(); }
    public String getPassword() { return password.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getDepartment() { return department.get(); }
    public String getCourse() { return course.get(); }

    // Properties (used for JavaFX bindings)
    public StringProperty studentNumberProperty() { return studentNumber; }
    public StringProperty emailProperty() { return email; }
    public StringProperty passwordProperty() { return password; }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
    public StringProperty departmentProperty() { return department; }
    public StringProperty courseProperty() { return course; }

    // Setters
    public void setStudentNumber(String studentNumber) { this.studentNumber.set(studentNumber); }
    public void setEmail(String email) { this.email.set(email); }
    public void setPassword(String password) { this.password.set(password); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public void setDepartment(String department) { this.department.set(department); }
    public void setCourse(String course) { this.course.set(course); }
}
