package com.example.quizapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.*;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<Question> questions;
    private TextView questionText, timerText, scoreText;
    private Button option1, option2, option3, option4;
    private int questionIndex = 0, score = 0;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db = FirebaseFirestore.getInstance();
        questions = new ArrayList<>();

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        loadQuestions();
    }

    private void loadQuestions() {
        String topic = getIntent().getStringExtra("topic");

        db.collection("questions")
                .whereEqualTo("topic", topic)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        questions.add(document.toObject(Question.class));
                    }
                    displayQuestion();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show());
    }

    private void displayQuestion() {
        if (questionIndex < questions.size()) {
            Question question = questions.get(questionIndex);
            questionText.setText(question.getQuestion());
            List<String> options = question.getOptions();
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            startTimer();
        } else {
            showScoreSummary();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                nextQuestion(false);
            }
        }.start();
    }

    private void checkAnswer(Button selectedOption) {
        Question question = questions.get(questionIndex);
        if (selectedOption.getText().equals(question.getCorrectOption())) {
            score++;
            scoreText.setText("Score: " + score);
        }
        nextQuestion(true);
    }

    private void nextQuestion(boolean answered) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        questionIndex++;
        displayQuestion();
    }

    private void showScoreSummary() {
        // Navigate to the ScoreSummaryActivity
        Intent intent = new Intent(QuizActivity.this, ScoreSummaryActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", questions.size());
        startActivity(intent);
        finish();
    }
}
