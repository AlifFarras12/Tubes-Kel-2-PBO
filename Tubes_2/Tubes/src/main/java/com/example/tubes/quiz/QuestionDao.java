package com.example.tubes.quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tubes_data?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String LIST_QUESTION_QUERY = "SELECT * FROM bank_soal WHERE id_dosen = ?";


    public List<QuestionData> fetchQuestions(int idDosen) {
        List<QuestionData> questions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(LIST_QUESTION_QUERY)) {

            preparedStatement.setInt(1, idDosen);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String soal = resultSet.getString("soal");
                    String choiceA = resultSet.getString("jawaban_a");
                    String choiceB = resultSet.getString("jawaban_b");
                    String choiceC = resultSet.getString("jawaban_c");
                    String jawabanBenar = resultSet.getString("jawaban_benar");

                    QuestionData question = new QuestionData(soal, choiceA, choiceB, choiceC, jawabanBenar);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
