package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button natureButton = findViewById(R.id.natureButton);
        Button scienceButton = findViewById(R.id.scienceButton);
        Button csButton = findViewById(R.id.csButton);
        Button historyButton = findViewById(R.id.historyButton);

        natureButton.setOnClickListener(view -> startQuiz("Nature"));
        scienceButton.setOnClickListener(view -> startQuiz("Science"));
        csButton.setOnClickListener(view -> startQuiz("Computer Science"));
        historyButton.setOnClickListener(view -> startHistory());
    }

    private void startQuiz(String topic) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }

    private void startHistory() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}
