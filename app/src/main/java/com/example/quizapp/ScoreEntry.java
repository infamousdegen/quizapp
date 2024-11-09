package com.example.quizapp;

public class ScoreEntry {
    private int score;
    private int totalQuestions;
    private double percentage;
    private long date;

    public ScoreEntry() { } // Required for Firestore

    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public double getPercentage() { return percentage; }
    public long getDate() { return date; }
}
