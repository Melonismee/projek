package service;

import model.Measurement;

public class MaleCalculator extends BodyFatCalculator {
    @Override
    protected double calculateBodyFat(Measurement m) {
        double waistNeck = m.getWaist() - m.getNeck();
        double height = m.getHeight();
        
        if (waistNeck <= 0) waistNeck = 1; 
        
        return 495 / (1.0324 - 0.19077 * Math.log10(waistNeck) + 0.15456 * Math.log10(height)) - 450;
    }
}