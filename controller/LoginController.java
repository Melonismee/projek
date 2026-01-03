package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import service.UserService;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private UserService userService = new UserService();
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleLogin() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        User loggedInUser = userService.login(user, pass);

        if (loggedInUser != null) {
            goToMainScreen(loggedInUser);
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau Password salah.");
        }
    }

    @FXML
    public void handleGoToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterView.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goToMainScreen(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setSession(user);

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Body Fat Calculator - Dashboard");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}