package com.example.quizapp;

public class ScoreEntry {
    private String userId; // Add userId to store the user's unique ID
    private int score;
    private int totalQuestions;
    private double percentage;
    private long date;

    // No-argument constructor for Firestore deserialization
    public ScoreEntry() {
        // Firestore requires an empty constructor to deserialize the object
    }

    // Constructor to initialize ScoreEntry object
    public ScoreEntry(int score, int totalQuestions, double percentage, long date, String userId) {
        this.userId = userId; // Set the current user's ID
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.percentage = percentage;
        this.date = date;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
