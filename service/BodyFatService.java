package service;

import dao.HistoryDAO;
import model.Measurement;
import model.BodyFatResult;
import java.util.List;

public class BodyFatService {

    private HistoryDAO historyDAO = new HistoryDAO();

    public boolean calculateAndSave(Measurement record) {
        try {
            if (record == null) return false;
            
            if (record.getHeight() <= 0 || record.getWeight() <= 0 || record.getWaist() <= 0 || record.getNeck() <= 0) {
                System.out.println("Validasi Gagal: Ukuran tubuh harus positif.");
                return false;
            }

            if (record.getGender().equalsIgnoreCase("Male")) {
                if (record.getWaist() <= record.getNeck()) {
                    System.out.println("Validasi Pria Gagal: Pinggang harus > Leher");
                    return false;
                }
            } else {
                if (record.getHip() <= 0) return false;
                if ((record.getWaist() + record.getHip()) <= record.getNeck()) {
                    System.out.println("Validasi Wanita Gagal: Pinggang+Pinggul harus > Leher");
                    return false;
                }
            }

            BodyFatCalculator calculator;
            if (record.getGender().equalsIgnoreCase("Male")) {
                calculator = new MaleCalculator();
            } else {
                calculator = new FemaleCalculator();
            }

            BodyFatResult result = calculator.analyze(record);
            
            record.setResult(result);

            historyDAO.insert(record);
            
            return true; 

        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

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
    
    public List<Measurement> getHistory(int userId) {
        try {
            return historyDAO.getAllByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void delete(int id) {
        try {
            historyDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}	 	