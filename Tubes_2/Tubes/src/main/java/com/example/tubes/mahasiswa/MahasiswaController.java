package com.example.tubes.mahasiswa;

import com.example.tubes.HelloApplication;
import com.example.tubes.appdata.AppData;
import com.example.tubes.dosen.DosenDao;
import com.example.tubes.extension.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.List;

public class MahasiswaController implements ControllerInterface {

    @FXML
    private ComboBox<String> dropdownDosen;

    @FXML
    private Button lanjutButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void initialize() {
        // Add any initialization code here
        initializeDropdownDosen();
    }

    private void initializeDropdownDosen() {
        // Add your logic to populate the ComboBox with data
        MahaasiswaDao mahaasiswaDao = new MahaasiswaDao();
        List<String> listDosen = mahaasiswaDao.getDosenList();
        dropdownDosen.getItems().addAll(listDosen);
    }

    @FXML
    private void handleLanjutButton() {
        // Add your logic when the "Lanjut Quiz" button is clicked

        if (validation()) {
            DosenDao dosenDao = new DosenDao();
            int idDosen = dosenDao.getDosenIdByName(dropdownDosen.getValue());
            AppData.setSelectedDosenId(idDosen);
            loadQuestionView();
        } else {
            showAlert("Error", "Silahkan pilih dosen dulu");
        }
    }

    private boolean validation() {
        return dropdownDosen.getValue() != null;
    }
    @FXML
    private void handleLogoutButton() {
        // Add your logic when the "Logout" button is clicked
        loadAdminView();
    }
    private void loadAdminView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadQuestionView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("quiz-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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