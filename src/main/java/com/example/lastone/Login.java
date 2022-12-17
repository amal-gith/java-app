package com.example.lastone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class Login {

    @FXML
    private Label showusernamelabel;
    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    @FXML
    private Button Login;

public class JdbcDao {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/biblio";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "Amal123*";
    private static final String SELECT_QUERY = "SELECT * FROM login WHERE username = ? and password = ?";

    public boolean validate(String Username, String Password) throws SQLException {

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setString(1, Username);
            preparedStatement.setString(2, Password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }


        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }}}}}



@FXML
public void validateLogin(ActionEvent event) throws SQLException, IOException {

    Window owner = Login.getScene().getWindow();

    System.out.println(Username.getText());
    System.out.println(Password.getText());

    if (Username.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter your email id");
        return;
    }
    if (Password.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please enter a password");
        return;
    }

    String emailId = Username.getText();
    String password = Password.getText();

    JdbcDao jdbcDao = new JdbcDao();
    boolean flag = jdbcDao.validate(emailId, password);

    if (!flag) {
        infoBox("Please enter correct Username and Password", null, "Failed");
    } else {
        infoBox("Login Successful!", null, "Welcome!");
        Stage stage = (Stage) Login.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("choisi.fxml"));
        Scene scene = new Scene(root);
        Stage primarStage = new Stage();
        primarStage.setScene(scene);
        primarStage.show();
        stage.close();
    }
}

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}


