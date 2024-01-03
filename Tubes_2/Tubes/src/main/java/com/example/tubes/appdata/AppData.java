package com.example.tubes.appdata;

public class AppData {
    private static String loggedInUsername  = "";
    private static int selectedDosenId = 0;
    private static double quizScore = 0.0;

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUser(String username) {
        loggedInUsername = username;
    }

    public static int getSelectedDosenId() {return selectedDosenId;}
    public static void setSelectedDosenId(int id) {
        selectedDosenId = id;
    }

    public static String getQuizScore() {return String.valueOf(quizScore);}
    public static void setQuizScore(double score) {quizScore = score;}
    public static void logout() {
        loggedInUsername = "";
        selectedDosenId = 0;
        quizScore = 0.0;
    }
}
