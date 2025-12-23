package service;

import model.Measurement;
import model.BodyFatResult;

public abstract class BodyFatCalculator implements DietAnalyzer {

    public BodyFatResult analyze(Measurement m) {
        // Balik ke cara lama: Buat object kosong
        BodyFatResult result = new BodyFatResult();
        
        double bf = calculateBodyFat(m);
        result.setBodyFatPercentage(bf);
        
        String cat = determineCategory(bf, m.getGender());
        result.setCategory(cat);
        
        generateDietRecommendation(result);
        
        return result;
    }

    protected abstract double calculateBodyFat(Measurement m);

    protected String determineCategory(double bf, String gender) {
        if (gender.equalsIgnoreCase("Female")) {
            return (bf < 21) ? "Underfat" : (bf < 33) ? "Normal" : (bf < 39) ? "Overfat" : "Obese";
        } else {
            return (bf < 8) ? "Underfat" : (bf < 19) ? "Normal" : (bf < 25) ? "Overfat" : "Obese";
        }
    }
    
    @Override
    public void generateDietRecommendation(BodyFatResult result) {
        String cat = result.getCategory();
        if (cat.equals("Obese") || cat.equals("Overfat")) {
            result.setCarbPercentage(20); result.setProteinPercentage(50); result.setFatPercentage(30);
        } else if (cat.equals("Underfat")) {
            result.setCarbPercentage(55); result.setProteinPercentage(25); result.setFatPercentage(20);
        } else {
            result.setCarbPercentage(45); result.setProteinPercentage(30); result.setFatPercentage(25);
        }
    }
}