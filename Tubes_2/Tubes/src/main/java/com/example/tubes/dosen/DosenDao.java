package com.example.tubes.dosen;

import com.example.tubes.extension.DaoInterface;

import java.sql.*;

public class DosenDao implements DaoInterface {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tubes_data?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String LOGIN_QUERY = "SELECT * FROM dosen WHERE username_dosen = ? AND password_dosen = ?";
    private static final String INSERT_SOAL_QUERY = "INSERT INTO bank_soal (soal, jawaban_a, jawaban_b, jawaban_c, jawaban_benar, id_dosen) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_DOSEN_ID_BY_USERNAME_QUERY = "SELECT iddosen FROM dosen WHERE username_dosen = ?";
    private static final String GET_DOSEN_ID_BY_NAME_QUERY = "SELECT iddosen FROM dosen WHERE nama_dosen = ?";


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

    public boolean insertSoal(String soal, String pilihanA, String pilihanB, String pilihanC, String jawabanBenar, int dosenId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SOAL_QUERY)) {

            preparedStatement.setString(1, soal);
            preparedStatement.setString(2, pilihanA);
            preparedStatement.setString(3, pilihanB);
            preparedStatement.setString(4, pilihanC);
            preparedStatement.setString(5, jawabanBenar);
            preparedStatement.setInt(6, dosenId); // Set the foreign key value

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getDosenIdByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DOSEN_ID_BY_USERNAME_QUERY)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("iddosen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default or error value (-1) if no matching record is found
        return -1;
    }

    public int getDosenIdByName(String name) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(GET_DOSEN_ID_BY_NAME_QUERY)) {

            preparedStatement.setString(1,  name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("iddosen");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default or error value (-1) if no matching record is found
        return -1;
    }
}
