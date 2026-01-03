package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public String validateAndRegister(String username, String email, String password) {
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return "Semua kolom wajib diisi!";
        }
        
        if (!email.contains("@")) {
            return "Format email salah (harus ada '@')";
        }
        
        if (password.length() < 6) return "Password minimal 6 karakter";
        if (userDAO.findByUsername(username) != null) return "Username sudah terpakai";

        User newUser = new User(username, email, password);
        userDAO.insert(newUser);
        
        return null;
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