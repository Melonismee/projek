package model;
import java.time.LocalDate;

public class Measurement {
	private int measurementId;
	private int userId;
	private String gender;
	private double height;
	private double weight;
	private double waist;
	private double neck;
	private double hip;
	private BodyFatResult result;
	private LocalDate date;
	
	public Measurement() {}

	public Measurement(int userId, String gender, double height, double weight, double waist, double neck, double hip, LocalDate date) {
		this.userId = userId;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.waist = waist;
		this.neck = neck;
		this.hip = hip;
		this.date = date;
	}
	
	public String getBodyFatDisplay() {
	    if (result != null) {
	        return String.format("%.2f", result.getBodyFatPercentage());
	    }
	    return "0.00";
	}
	
	public String getCategoryDisplay() {
        return (result != null) ? result.getCategory() : "-";
    }

    public String getDietDisplay() {
        if (result == null) return "-";
        return result.getDietRecommenString();
    }
	
	public int getMeasurementId() {
		return measurementId;
	}
	
	public void setMeasurementId(int measurementId) {
		this.measurementId = measurementId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWaist() {
		return waist;
	}
	
	public void setWaist(double waist) {
		this.waist = waist;
	}
	
	public double getNeck() {
		return neck;
	}
	
	public void setNeck(double neck) {
		this.neck = neck;
	}
	
	public double getHip() {
		return hip;
	}
	
	public void setHip(double hip) {
		this.hip = hip;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public BodyFatResult getResult() {
		return result;
	}
	
	public void setResult(BodyFatResult result) {
		this.result = result;
	}
}
