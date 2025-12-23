package dao;

import config.DatabaseConfig;
import model.BodyFatResult;
import model.Measurement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    public void insert(Measurement m) {
        BodyFatResult r = m.getResult(); 

        String sql = "INSERT INTO measurement_history (user_id, measurement_date, gender, height, weight, waist, neck, hip, body_fat_percentage, category, carb_percentage, protein_percentage, fat_percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, m.getUserId());
            stmt.setDate(2, Date.valueOf(m.getDate())); 
            stmt.setString(3, m.getGender());
            stmt.setDouble(4, m.getHeight());
            stmt.setDouble(5, m.getWeight());
            stmt.setDouble(6, m.getWaist());
            stmt.setDouble(7, m.getNeck());
            stmt.setDouble(8, m.getHip());
            
            if (r != null) {
                stmt.setDouble(9, r.getBodyFatPercentage());
                stmt.setString(10, r.getCategory());
                stmt.setDouble(11, r.getCarbPercentage());
                stmt.setDouble(12, r.getProteinPercentage());
                stmt.setDouble(13, r.getFatPercentage());
            } else {
                stmt.setDouble(9, 0.0);
                stmt.setString(10, "Unknown");
                stmt.setDouble(11, 0.0);
                stmt.setDouble(12, 0.0);
                stmt.setDouble(13, 0.0);
            }
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Measurement> getAllByUserId(int userId) {
        List<Measurement> list = new ArrayList<>();
        
        String sql = "SELECT id, user_id, measurement_date, gender, body_fat_percentage, category, carb_percentage, protein_percentage, fat_percentage FROM measurement_history WHERE user_id = ? ORDER BY measurement_date DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Measurement m = new Measurement();
                m.setMeasurementId(rs.getInt("id")); 
                m.setUserId(rs.getInt("user_id"));
                m.setDate(rs.getDate("measurement_date").toLocalDate());
                m.setGender(rs.getString("gender"));
                
                BodyFatResult r = new BodyFatResult();
                r.setBodyFatPercentage(rs.getDouble("body_fat_percentage"));
                r.setCategory(rs.getString("category"));
                r.setCarbPercentage(rs.getDouble("carb_percentage"));
                r.setProteinPercentage(rs.getDouble("protein_percentage"));
                r.setFatPercentage(rs.getDouble("fat_percentage"));
                
                m.setResult(r);
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delete(int measurementId) {
        String sql = "DELETE FROM measurement_history WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, measurementId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}