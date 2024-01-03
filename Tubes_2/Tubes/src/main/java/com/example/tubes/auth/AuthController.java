package com.example.tubes.auth;

import com.example.tubes.HelloApplication;
import com.example.tubes.admin.AdminDao;
import com.example.tubes.appdata.AppData;
import com.example.tubes.dosen.DosenDao;
import com.example.tubes.extension.ControllerInterface;
import com.example.tubes.mahasiswa.MahaasiswaDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AuthController implements ControllerInterface {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton dosenRadioButton;

    @FXML
    private RadioButton adminRadioButton;

    @FXML
    private RadioButton mahasiswaRadioButton;

    // Deklarasi grup ToggleGroup
    private ToggleGroup toggleGroup;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Get the selected radio button
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            String role = selectedRadioButton.getText().toLowerCase();

            // Perform login based on the selected role

            switch (role) {
                case "admin":
                    AdminDao authDao = new AdminDao();
                    if (authDao.login(username, password)) {
                        loadAdminView();
                    } else {
                        showAlert("Login Failed", "Invalid username or password.");
                    }
                    break;
                case "dosen":
                    DosenDao dosenDao = new DosenDao();
                    if (dosenDao.login(username, password)) {
                        AppData.setLoggedInUser(username);
                        loadDosenView();
                    } else {
                        showAlert("Login Failed", "Invalid username or password.");
                    }
                    break;
                // Add cases for other roles (e.g., "dosen", "mahasiswa") if needed
                case "mahasiswa":
                    MahaasiswaDao mahaasiswaDao = new MahaasiswaDao();
                    if (mahaasiswaDao.login(username, password)) {
                        AppData.setLoggedInUser(username);
                        loadMahasiswaView();
                    } else {
                        showAlert("Login Failed", "Invalid username or password.");
                    }
                    break;
                default:
                    showAlert("Login Failed", "Invalid role selected.");
            }
        } else {
            showAlert("Login Failed", "Please select a role.");
        }
    }

    private void loadAdminView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) mahasiswaRadioButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadMahasiswaView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mahasiswa-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) mahasiswaRadioButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDosenView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dosen-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) mahasiswaRadioButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        // Inisialisasi grup ToggleGroup
        toggleGroup = new ToggleGroup();

        // Menambahkan RadioButton ke dalam grup
        dosenRadioButton.setToggleGroup(toggleGroup);
        adminRadioButton.setToggleGroup(toggleGroup);
        mahasiswaRadioButton.setToggleGroup(toggleGroup);
    }

    @Override
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
