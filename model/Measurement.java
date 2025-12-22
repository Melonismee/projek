package model;
import java.time.LocalDate;

public class Measurement {
	private int measurementId;
	private int userId;
	private String gender;
	private double heightCm;
	private double weightKg;
	private double waistCm;
	private double neckCm;
	private double hipCm;
	private LocalDate measurementDate;
	
	public Measurement() {}

	public Measurement(int userId, String gender, double heightCm, double weightKg, double waistCm, double neckCm, double hipCm, LocalDate measurementDate) {
		this.userId = userId;
		this.gender = gender;
		this.heightCm = heightCm;
		this.weightKg = weightKg;
		this.waistCm = waistCm;
		this.neckCm = neckCm;
		this.hipCm = hipCm;
		this.measurementDate = measurementDate;
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
	
	public double getHeightCm() {
		return heightCm;
	}
	
	public void setHeightCm(double heightCm) {
		this.heightCm = heightCm;
	}
	
	public double getWeightKg() {
		return weightKg;
	}
	
	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}
	
	public double getWaistCm() {
		return waistCm;
	}
	
	public void setWaistCm(double waistCm) {
		this.waistCm = waistCm;
	}
	
	public double getNeckCm() {
		return neckCm;
	}
	
	public void setNeckCm(double neckCm) {
		this.neckCm = neckCm;
	}
	
	public double getHipCm() {
		return hipCm;
	}
	
	public void setHipCm(double hipCm) {
		this.hipCm = hipCm;
	}
	
	public LocalDate getMeasurementDate() {
		return measurementDate;
	}
	
	public void setMeasurementDate(LocalDate measurementDate) {
		this.measurementDate = measurementDate;
	}
}
