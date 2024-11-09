package com.example.quizapp;

import java.util.List;

public class Question {
    private String question;
    private List<String> options;
    private String correctOption;
    private String topic;

    // Required empty constructor for Firestore deserialization
    public Question() {
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getTopic() {
        return topic;
    }

    // Setters (optional, depending on how you set up Firestore or use the class)
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
