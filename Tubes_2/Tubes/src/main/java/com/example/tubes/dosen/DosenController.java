package com.example.tubes.dosen;

import com.example.tubes.HelloApplication;
import com.example.tubes.appdata.AppData;
import com.example.tubes.extension.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DosenController implements ControllerInterface {

    @FXML
    private TextField soalTextField;

    @FXML
    private TextField pilihanATextField;

    @FXML
    private TextField pilihanBTextField;

    @FXML
    private TextField pilihanCTextField;
    @FXML
    private RadioButton choiceA;

    @FXML
    private RadioButton choiceB;

    @FXML
    private RadioButton choiceC;

    @FXML
    private Button tambahButton;
    private ToggleGroup toggleGroup;


    @FXML
    private void initialize() {
        toggleGroup = new ToggleGroup();

        // Menambahkan RadioButton ke dalam grup
        choiceA.setToggleGroup(toggleGroup);
        choiceB.setToggleGroup(toggleGroup);
        choiceC.setToggleGroup(toggleGroup);
    }

    @FXML
    private void handleTambahSoal() {
        // Check if any of the text fields is empty
        if (isAnyFieldEmpty()) {
            showAlert("Error", "Semua kolom harus diisi.");
        } else {
            DosenDao dosenDao = new DosenDao();
            int dosenId = dosenDao.getDosenIdByUsername(AppData.getLoggedInUsername());
            dosenDao.insertSoal(
                    soalTextField.getText(), pilihanATextField.getText(), pilihanBTextField.getText(), pilihanCTextField.getText(), "A", dosenId
            );
            clearTextFields();
        }
    }

    @FXML
    private void logout() {
        AppData.logout();
        loadAdminView();

    }
    private void loadAdminView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
            Parent adminView = fxmlLoader.load();
            Scene adminScene = new Scene(adminView);

            Stage currentStage = (Stage) tambahButton.getScene().getWindow();
            currentStage.setScene(adminScene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isAnyFieldEmpty() {
        return soalTextField.getText().isEmpty()
                || pilihanATextField.getText().isEmpty()
                || pilihanBTextField.getText().isEmpty()
                || pilihanCTextField.getText().isEmpty()
                || toggleGroup == null;
    }


    @Override
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearTextFields() {
        soalTextField.clear();
        pilihanATextField.clear();
        pilihanBTextField.clear();
        pilihanCTextField.clear();
        toggleGroup.selectToggle(null);
    }
}
