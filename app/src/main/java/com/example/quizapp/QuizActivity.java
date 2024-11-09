package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private Button option1, option2, option3, option4;
    private String correctAnswer = "Option 1";  // Hardcoded correct answer for this example

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize views
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        // Set question and options
        questionText.setText("What is the capital of France?");
        option1.setText("Option 1: Paris");
        option2.setText("Option 2: London");
        option3.setText("Option 3: Berlin");
        option4.setText("Option 4: Madrid");

        // Set click listeners for options
        option1.setOnClickListener(this::checkAnswer);
        option2.setOnClickListener(this::checkAnswer);
        option3.setOnClickListener(this::checkAnswer);
        option4.setOnClickListener(this::checkAnswer);
    }

    private void checkAnswer(View view) {
        Button selectedOption = (Button) view;
        String selectedAnswer = selectedOption.getText().toString();

        if (selectedAnswer.contains(correctAnswer)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
        }
    }
}
