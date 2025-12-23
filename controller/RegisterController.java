package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.UserService;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private UserService userService = new UserService();

    @FXML
    public void handleRegister() {
        String user = txtUsername.getText();
        String email = txtEmail.getText();
        String pass = txtPassword.getText();

        String errorMessage = userService.validateAndRegister(user, email, pass);

        if (errorMessage == null) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Akun berhasil dibuat! Silakan login.");
            handleBackToLogin();
        } else {
            showAlert(Alert.AlertType.WARNING, "Validasi Gagal", errorMessage);
        }
    }

    @FXML
    public void handleBackToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}