package service;

import model.Measurement;
import model.BodyFatResult;

public abstract class BodyFatCalculator implements DietAnalyzer {

    // Template Method: Mengatur alur (Hitung -> Kategori -> Diet)
    public BodyFatResult analyze(Measurement m) {
        BodyFatResult result = new BodyFatResult();
        
        // 1. Hitung (Dikerjakan oleh Anak Kelas)
        double bf = calculateBodyFat(m);
        result.setBodyFatPercentage(bf);
        
        // 2. Kategori (Logika Umum)
        String cat = determineCategory(bf, m.getGender());
        result.setCategory(cat);
        
        // 3. Diet (Logika Umum dari Interface)
        generateDietRecommendation(result);
        
        return result;
    }

    // Method Abstrak: Wajib diisi oleh Male/Female Calculator
    protected abstract double calculateBodyFat(Measurement m);

    // Logika Kategori (Shared Logic)
    protected String determineCategory(double bf, String gender) {
        if (gender.equalsIgnoreCase("Female")) {
            return (bf < 21) ? "Underfat" : (bf < 33) ? "Normal" : (bf < 39) ? "Overfat" : "Obese";
        } else {
            return (bf < 8) ? "Underfat" : (bf < 19) ? "Normal" : (bf < 25) ? "Overfat" : "Obese";
        }
    }
    
    // Implementasi Interface DietAnalyzer
    @Override
    public void generateDietRecommendation(BodyFatResult result) {
        String cat = result.getCategory();
        if (cat.equals("Obese") || cat.equals("Overfat")) {
            // Rendah Karbo, Tinggi Protein
            result.setCarbPercentage(20); result.setProteinPercentage(50); result.setFatPercentage(30);
        } else if (cat.equals("Underfat")) {
            // Tinggi Karbo
            result.setCarbPercentage(55); result.setProteinPercentage(25); result.setFatPercentage(20);
        } else {
            // Seimbang
            result.setCarbPercentage(45); result.setProteinPercentage(30); result.setFatPercentage(25);
        }
    }
}