package com.example.tubes.mahasiswa;

import com.example.tubes.extension.DaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahaasiswaDao {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tubes_data?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String LOGIN_QUERY = "SELECT * FROM mahasiswa WHERE username_mahasiswa = ? AND password_mahasiswa = ?";
    private static final String LIST_DOSEN_QUERY = "SELECT DISTINCT dosen.nama_dosen FROM bank_soal JOIN dosen ON bank_soal.id_dosen = dosen.iddosen";
    public boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Check if the result set has any rows
                return resultSet.next();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            DaoInterface.printSQLException(e);
            return false;
        }
    }
    public List<String> getDosenList() {
        List<String> dosenList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);// get connection from your connection manager
                     PreparedStatement preparedStatement = connection.prepareStatement(LIST_DOSEN_QUERY);
        ResultSet resultSet = preparedStatement.executeQuery()) {

            // Mengambil nama dosen dari hasil query
            while (resultSet.next()) {
                String namaDosen = resultSet.getString("nama_dosen");
                dosenList.add(namaDosen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dosenList;
    }
}
