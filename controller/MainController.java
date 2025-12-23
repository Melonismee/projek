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

    // --- INPUT FIELDS ---
    @FXML private ComboBox<String> comboGender;
    @FXML private TextField txtHeight;
    @FXML private TextField txtWeight;
    @FXML private TextField txtWaist;
    @FXML private TextField txtNeck;
    @FXML private TextField txtHip;

    // --- OUTPUT / LABELS ---
    @FXML private Label lblResult;      // Menampilkan % Lemak
    @FXML private Label lblCategory;    // Menampilkan Kategori
    @FXML private Label lblAvg;         // Menampilkan Rata-rata

    // --- TABLE VIEW ---
    @FXML private TableView<Measurement> tableHistory;
    @FXML private TableColumn<Measurement, String> colDate;
    @FXML private TableColumn<Measurement, String> colGender;
    @FXML private TableColumn<Measurement, Double> colResult;
    @FXML private TableColumn<Measurement, String> colCategory;
    @FXML private TableColumn<Measurement, String> colDiet;

    // --- LOGIC ---
    private BodyFatService service = new BodyFatService();
    private User currentUser;
    private ObservableList<Measurement> listData;

    @FXML
    public void initialize() {
        // Setup Pilihan Gender
        comboGender.setItems(FXCollections.observableArrayList("Male", "Female"));
        
        // Setup Kolom Tabel
        // 'date' sesuai nama variabel di class Measurement
        colDate.setCellValueFactory(new PropertyValueFactory<>("date")); 
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        // PENTING: Menggunakan Helper Method di Measurement.java untuk mengambil data hasil
        // 'bodyFatDisplay' merujuk ke method getBodyFatDisplay()
        colResult.setCellValueFactory(new PropertyValueFactory<>("bodyFatDisplay"));
        // 'categoryDisplay' merujuk ke method getCategoryDisplay()
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryDisplay"));
        // 'dietDisplay' merujuk ke method getDietDisplay()
        colDiet.setCellValueFactory(new PropertyValueFactory<>("dietDisplay"));
    }

    // Method ini dipanggil dari LoginController untuk mengoper data User
    public void setSession(User user) {
        this.currentUser = user;
        loadHistory(); // Load data saat user masuk
    }

    @FXML
    public void handleCalculate() {
        try {
            // 1. Ambil Data Input
            String gender = comboGender.getValue();
            double height = Double.parseDouble(txtHeight.getText());
            double weight = Double.parseDouble(txtWeight.getText());
            double waist = Double.parseDouble(txtWaist.getText());
            double neck = Double.parseDouble(txtNeck.getText());
            // Hip boleh 0 jika laki-laki
            double hip = txtHip.getText().isEmpty() ? 0 : Double.parseDouble(txtHip.getText());

            // 2. Bungkus ke Object
            Measurement m = new Measurement();
            m.setUserId(currentUser.getUserId());
            m.setDate(LocalDate.now());
            m.setGender(gender);
            m.setHeight(height);
            m.setWeight(weight);
            m.setWaist(waist);
            m.setNeck(neck);
            m.setHip(hip);

            // 3. Panggil Service (Validasi + Hitung + Simpan)
            boolean success = service.calculateAndSave(m);

            if (success) {
                // Tampilkan Hasil Singkat di Label
                lblResult.setText(String.format("%.1f %%", m.getResult().getBodyFatPercentage()));
                lblCategory.setText(m.getResult().getCategory());
                
                // Refresh Tabel & Rata-rata
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
        // Ambil item yang dipilih di tabel
        Measurement selected = tableHistory.getSelectionModel().getSelectedItem();
        
        if (selected != null) {
            service.delete(selected.getMeasurementId());
            loadHistory(); // Refresh
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

    // Method pembantu untuk refresh data
    private void loadHistory() {
        if (currentUser == null) return;

        // 1. Ambil List dari Service
        List<Measurement> history = service.getHistory(currentUser.getUserId());
        
        // 2. Masukkan ke ObservableList untuk Tabel
        listData = FXCollections.observableArrayList(history);
        tableHistory.setItems(listData);

        // 3. Update Label Rata-rata
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