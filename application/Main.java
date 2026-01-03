package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Aplikasi Kalkulator Body Fat - UAS");
            primaryStage.setScene(scene);
            
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat LoginView.fxml. Pastikan file ada di folder src/view/");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}