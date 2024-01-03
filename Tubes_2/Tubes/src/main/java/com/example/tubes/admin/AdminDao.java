package com.example.tubes.admin;


import com.example.tubes.extension.DaoInterface;

import java.sql.*;

public class AdminDao implements DaoInterface {
    // Replace below database url, username and password with your actual database credentials
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tubes_data?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String LOGIN_QUERY = "SELECT * FROM admin WHERE admin_user_name = ? AND admin_password = ?";
    private static final String INSERT_DOSEN_QUERY = "INSERT INTO dosen (nama_dosen, username_dosen, password_dosen) VALUES (?, ?, ?)";
    private static final String INSERT_MAHASISWA_QUERY = "INSERT INTO mahasiswa (nama_mahasiswa, username_mahasiswa, password_mahasiswa) VALUES (?, ?, ?)";



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

    public void insertDosen(String name, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DOSEN_QUERY)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Handle the SQL exception (print or log the details)
            e.printStackTrace();
        }
    }
    public void insertMahasiswa(String name, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MAHASISWA_QUERY)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Handle the SQL exception (print or log the details)
            e.printStackTrace();
        }
    }
}
