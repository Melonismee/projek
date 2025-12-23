package service;

import dao.HistoryDAO;
import model.Measurement;
import model.BodyFatResult;
import java.util.List;

public class BodyFatService {

    private HistoryDAO historyDAO = new HistoryDAO();

    /**
     * LOGIKA UTAMA: Validasi -> Hitung -> Simpan
     * Mengembalikan true jika sukses, false jika gagal.
     */
    public boolean calculateAndSave(Measurement record) {
        try {
            // --- 1. VALIDASI INPUT ---
            if (record == null) return false;
            
            // Cek angka negatif/nol
            if (record.getHeight() <= 0 || record.getWeight() <= 0 || 
                record.getWaist() <= 0 || record.getNeck() <= 0) {
                System.out.println("Validasi Gagal: Ukuran tubuh harus positif.");
                return false;
            }

            // Cek Logika Rumus (Agar matematika masuk akal)
            if (record.getGender().equalsIgnoreCase("Male")) {
                // Untuk Pria: Pinggang harus lebih besar dari Leher
                if (record.getWaist() <= record.getNeck()) {
                    System.out.println("Validasi Pria Gagal: Pinggang harus > Leher");
                    return false;
                }
            } else {
                // Untuk Wanita: (Pinggang+Pinggul) harus lebih besar dari Leher
                if (record.getHip() <= 0) return false;
                if ((record.getWaist() + record.getHip()) <= record.getNeck()) {
                    System.out.println("Validasi Wanita Gagal: Pinggang+Pinggul harus > Leher");
                    return false;
                }
            }

            // --- 2. PROSES HITUNG (Polymorphism) ---
            BodyFatCalculator calculator;
            if (record.getGender().equalsIgnoreCase("Male")) {
                calculator = new MaleCalculator();
            } else {
                calculator = new FemaleCalculator();
            }

            // Lakukan analisis rumus
            BodyFatResult result = calculator.analyze(record);
            
            // Masukkan hasil ke dalam object measurement
            record.setResult(result);

            // --- 3. SIMPAN KE DATABASE ---
            // Menggunakan method 'insert' sesuai DAO Anda
            historyDAO.insert(record);
            
            return true; // Sukses

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Gagal
        }
    }

    /**
     * LOGIKA BISNIS: Menghitung Rata-rata Body Fat
     * Dilakukan di Service (bukan DAO).
     */
    public double getAverageBodyFat(int userId) {
        try {
            List<Measurement> history = historyDAO.getAllByUserId(userId);
            
            if (history.isEmpty()) return 0.0;

            double total = 0;
            int count = 0;

            for (Measurement m : history) {
                if (m.getResult() != null) {
                    total += m.getResult().getBodyFatPercentage();
                    count++;
                }
            }
            
            if (count == 0) return 0.0;
            return total / count;

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    // Wrapper: Ambil semua history
    public List<Measurement> getHistory(int userId) {
        try {
            return historyDAO.getAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Wrapper: Hapus history
    public void delete(int id) {
        try {
            historyDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}	 	