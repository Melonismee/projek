package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    /**
     * REGISTER: Mengembalikan true jika berhasil, false jika gagal.
     */
    public boolean register(String username, String email, String password) {
        try {
            // 1. Validasi Input Kosong
            if (username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
                return false;
            }

            // 2. Validasi Password Minimal 6 Karakter
            if (password.length() < 6) {
                System.out.println("Password terlalu pendek");
                return false;
            }

            // 3. Cek apakah username sudah ada (Cegah Duplikasi)
            if (userDAO.findByUsername(username) != null) {
                System.out.println("Username sudah terpakai");
                return false;
            }

            // 4. Simpan User Baru
            User newUser = new User(username, email, password);
            userDAO.insert(newUser); // Menggunakan method 'insert'
            
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * LOGIN: Mengembalikan object User jika sukses, null jika gagal.
     */
    public User login(String username, String password) {
        try {
            if (username == null || password == null) return null;

            // Ambil data user dari DAO
            User user = userDAO.findByUsername(username);

            // Cek apakah user ada DAN password cocok
            if (user != null && user.getPassword().equals(password)) {
                return user; // Login Sukses
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null; // Login Gagal
    }
}