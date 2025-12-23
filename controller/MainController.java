package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Measurement;
import model.User;
import service.BodyFatService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainController {
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtHeight;
    @FXML private TextField txtWeight;
    @FXML private TextField txtWaist;
    @FXML private TextField txtNeck;
    @FXML private TextField txtHip;

    @FXML private Label lblResult;      
    @FXML private Label lblCategory;    
    @FXML private Label lblAvg;         

    @FXML private TableView<Measurement> tableHistory;
    @FXML private TableColumn<Measurement, String> colDate;
    @FXML private TableColumn<Measurement, String> colGender;
    @FXML private TableColumn<Measurement, Double> colResult;
    @FXML private TableColumn<Measurement, String> colCategory;
    @FXML private TableColumn<Measurement, String> colDiet;

    private BodyFatService service = new BodyFatService();
    private User currentUser;
    private ObservableList<Measurement> listData;

    @FXML
    public void initialize() {
        comboGender.setItems(FXCollections.observableArrayList("Male", "Female"));
        
        colDate.setCellValueFactory(new PropertyValueFactory<>("date")); 
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        colResult.setCellValueFactory(new PropertyValueFactory<>("bodyFatDisplay"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryDisplay"));
        colDiet.setCellValueFactory(new PropertyValueFactory<>("dietDisplay"));
    }

    public void setSession(User user) {
        this.currentUser = user;
        loadHistory(); 
    }

    @FXML
    public void handleCalculate() {
        try {
            String gender = comboGender.getValue();
            double height = Double.parseDouble(txtHeight.getText());
            double weight = Double.parseDouble(txtWeight.getText());
            double waist = Double.parseDouble(txtWaist.getText());
            double neck = Double.parseDouble(txtNeck.getText());
            double hip = txtHip.getText().isEmpty() ? 0 : Double.parseDouble(txtHip.getText());

            Measurement m = new Measurement();
            m.setUserId(currentUser.getUserId());
            m.setDate(LocalDate.now());
            m.setGender(gender);
            m.setHeight(height);
            m.setWeight(weight);
            m.setWaist(waist);
            m.setNeck(neck);
            m.setHip(hip);

            boolean success = service.calculateAndSave(m);

            if (success) {
                lblResult.setText(String.format("%.1f %%", m.getResult().getBodyFatPercentage()));
                lblCategory.setText(m.getResult().getCategory());
                
                loadHistory();
                
                showAlert(Alert.AlertType.INFORMATION, "Sukses", "Perhitungan berhasil disimpan!");
            } else {
                showAlert(Alert.AlertType.WARNING, "Gagal", "Validasi gagal. Cek input Anda (harus positif & logis).");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Mohon masukkan angka yang valid.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        Measurement selected = tableHistory.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
            service.delete(selected.getMeasurementId());
            loadHistory(); 
        } else {
            showAlert(Alert.AlertType.WARNING, "Pilih Data", "Silakan pilih data di tabel untuk dihapus.");
        }
    }

    @FXML
    public void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
            Stage stage = (Stage) lblResult.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        if (currentUser == null) return;

        List<Measurement> history = service.getHistory(currentUser.getUserId());
        
        listData = FXCollections.observableArrayList(history);
        tableHistory.setItems(listData);

        double avg = service.getAverageBodyFat(currentUser.getUserId());
        lblAvg.setText(String.format("Rata-rata: %.1f %%", avg));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}