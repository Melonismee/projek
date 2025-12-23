package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        // Panggil Service
        boolean isSuccess = userService.register(user, email, pass);

        if (isSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Sukses", "Akun berhasil dibuat! Silakan login.");
            handleBackToLogin();
        } else {
            // Pesan error detailnya sudah di-handle/print di Service (atau bisa disesuaikan)
            showAlert(Alert.AlertType.ERROR, "Gagal", "Gagal mendaftar. Cek input atau username mungkin sudah ada.");
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