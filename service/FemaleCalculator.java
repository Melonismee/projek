package service;

import model.Measurement;

public class FemaleCalculator extends BodyFatCalculator {
    @Override
    protected double calculateBodyFat(Measurement m) {
        // Rumus US Navy Wanita: Pinggang + Pinggul - Leher
        double waistHipNeck = m.getWaist() + m.getHip() - m.getNeck();
        double height = m.getHeight();
        
        // Safety check
        if (waistHipNeck <= 0) waistHipNeck = 1;

        return 495 / (1.29579 - 0.35004 * Math.log10(waistHipNeck) + 0.22100 * Math.log10(height)) - 450;
    }
}