package model;

public class BodyFatResult {

    private double bodyFatPercentage;
    private String category;
    private double carbPercentage;
    private double proteinPercentage;
    private double fatPercentage;

    public BodyFatResult() {
    }

    public BodyFatResult(double bodyFatPercentage, String category,double carbPercentage, double proteinPercentage, double fatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
        this.category = category;
        this.carbPercentage = carbPercentage;
        this.proteinPercentage = proteinPercentage;
        this.fatPercentage = fatPercentage;
    }

    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCarbPercentage() {
        return carbPercentage;
    }

    public void setCarbPercentage(double carbPercentage) {
        this.carbPercentage = carbPercentage;
    }

    public double getProteinPercentage() {
        return proteinPercentage;
    }

    public void setProteinPercentage(double proteinPercentage) {
        this.proteinPercentage = proteinPercentage;
    }

    public double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }
    
    public String getDietRecommenString() {
    	return String.format("Carbs: %.0f%%, Protein: %.0f%%, Fat: %.0f%%", carbPercentage, proteinPercentage, fatPercentage);
    }
}
