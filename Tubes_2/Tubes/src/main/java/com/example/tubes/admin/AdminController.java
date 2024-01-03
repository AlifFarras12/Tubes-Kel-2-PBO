package com.example.tubes.admin;

import com.example.tubes.HelloApplication;
import com.example.tubes.extension.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminController implements ControllerInterface {

    @FXML
    private TextField nameTextField;


    @FXML
    private Button roleButton;

    @FXML
    private Button logoutButton;


    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private RadioButton dosenRadioButton;

    @FXML
    private RadioButton mahasiswaRadioButton;

    @FXML
    private ToggleGroup roleToggleGroup; // Add this line

    @FXML
    private void handleAddActionButton() {
        if (validateFields()) {
            // Get the selected Toggle (RadioButton) from the ToggleGroup
            RadioButton selectedRadioButton = (RadioButton) roleToggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedRole = selectedRadioButton.getText().toLowerCase();
                AdminDao adminDao = new AdminDao();
                String name = "";
                String username = "";
                String password = "";
                switch (selectedRole) {
                    case "dosen" :
                        name = nameTextField.getText();
                        username = usernameTextField.getText();
                        password = passwordTextField.getText();

                        adminDao.insertDosen(name, username, password);
                        showAlert("Add Successfully", "Success Add Dosen");
                        break;
                    case "mahasiswa":
                        name = nameTextField.getText();
                        username = usernameTextField.getText();
                        password = passwordTextField.getText();

                        adminDao.insertMahasiswa(name, username, password);
                        showAlert("Add Successfully", "Success Add Mahasiswa");
                        break;
                    default:
                        showAlert("Login Failed", "Invalid role selected.");
                }
            } else {
                showAlert("Error", "Please select a role.");
            }
        } else {
            showAlert("Error", "Please fill in all fields.");
        }
    }

    @FXML
    private void handleLogoutButton() {
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
    @FXML
    private void initialize() {
        // Add any initialization code here
        roleToggleGroup = new ToggleGroup();

        // Assign the RadioButtons to the ToggleGroup
        dosenRadioButton.setToggleGroup(roleToggleGroup);
        mahasiswaRadioButton.setToggleGroup(roleToggleGroup);
    }
    private boolean validateFields() {
        return !nameTextField.getText().isEmpty() &&
                !usernameTextField.getText().isEmpty() &&
                !passwordTextField.getText().isEmpty();
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
