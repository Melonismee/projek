package service;

import model.BodyFatResult;

public interface DietAnalyzer {
    void generateDietRecommendation(BodyFatResult result);
}