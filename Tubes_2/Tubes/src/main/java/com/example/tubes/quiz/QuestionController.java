package com.example.tubes.quiz;

import com.example.tubes.HelloApplication;
import com.example.tubes.appdata.AppData;
import com.example.tubes.extension.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class QuestionController implements ControllerInterface {
    @FXML
    private Button logoutButton;

    @FXML
    private TextArea textSoal;

    @FXML
    private RadioButton choiceA;

    @FXML
    private RadioButton choiceB;

    @FXML
    private RadioButton choiceC;

    @FXML
    private Button lanjutButton;

    private int currentQuestion = 0;
    private int total_questions = 0;
    private List<QuestionData> listQuestions;

    private ToggleGroup toggleGroup;
    private int correct = 0;

    @FXML
    private void initialize() {
        // Inisialisasi komponen dan tambahkan radio button ke dalam toggle group
        toggleGroup = new ToggleGroup();
        choiceA.setToggleGroup(toggleGroup);
        choiceB.setToggleGroup(toggleGroup);
        choiceC.setToggleGroup(toggleGroup);
        QuestionDao questionDao = new QuestionDao();
        listQuestions = questionDao.fetchQuestions(AppData.getSelectedDosenId());
        total_questions = listQuestions.size();
        setPertanyaan(listQuestions.get(currentQuestion));
    }

    @FXML
    private void handleLanjutButton() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String answer = selectedRadioButton.getText().toLowerCase();
            String choosed = String.valueOf(answer.charAt(0));
            if (choosed.equals(listQuestions.get(currentQuestion).getJawabanBenar().toLowerCase())) {
                correct += 1;
            }
            if (currentQuestion < total_questions-1) {
                currentQuestion += 1;
                setPertanyaan(listQuestions.get(currentQuestion));
            } else {
                lanjutButton.setText("Finish");
                showAlert("Selesai", "Skor Kamu "+ ((double) correct /2) * 100);
                AppData.logout();
                loadAdminView();
            }
        } else {
            showAlert("Error", "Kamu belum memilih jawaban");
        }

    }
    @FXML
    private void handleLogoutButton() {
        AppData.logout();
        loadAdminView();
    }

    @Override
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void setPertanyaan(QuestionData data) {
        toggleGroup.selectToggle(null);
        textSoal.setText(data.getSoal());
        choiceA.setText("A. "+data.getChoiceA());
        choiceB.setText("B. "+data.getChoiceB());
        choiceC.setText("C. "+data.getChoiceC());
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

}
