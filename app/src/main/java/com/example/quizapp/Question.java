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

    // Parameterized constructor for convenience
    public Question(String question, List<String> options, String correctOption, String topic) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
        this.topic = topic;
    }

    // Getters and setters
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getCorrectOption() { return correctOption; }
    public void setCorrectOption(String correctOption) { this.correctOption = correctOption; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
}
