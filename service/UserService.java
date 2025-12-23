package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public String validateAndRegister(String username, String email, String password) {
        if (isAnyEmpty(username, email, password)) {
            return "Semua kolom wajib diisi!";
        }

        if (!email.contains("@")) {
            return "Format email salah! Harus mengandung '@'.";
        }

        if (password.length() < 6) {
            return "Password terlalu pendek! Minimal 6 karakter.";
        }

        if (userDAO.findByUsername(username) != null) {
            return "Username '" + username + "' sudah terpakai.";
        }

        try {
            User newUser = new User(username, email, password);
            userDAO.insert(newUser);
            return null; 
        } catch (Exception e) {
            e.printStackTrace();
            return "Terjadi kesalahan database.";
        }
    }

    private boolean isAnyEmpty(String... texts) {
        for (String t : texts) {
            if (t == null || t.trim().isEmpty()) return true;
        }
        return false;
    }
    
    public User login(String username, String password) {
        if (username == null || password == null) return null;
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}