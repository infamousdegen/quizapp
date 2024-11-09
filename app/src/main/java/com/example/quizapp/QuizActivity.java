package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<Question> questions;
    private List<Question> remainingQuestions; // To keep track of remaining questions
    private TextView questionText, timerText, scoreText;
    private Button option1, option2, option3, option4;
    private int questionIndex = 0, score = 0;
    private CountDownTimer countDownTimer;
    private FirestoreUtils firestoreUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Initialize Firestore and FirestoreUtils
        db = FirebaseFirestore.getInstance();
        firestoreUtils = new FirestoreUtils();
        questions = new ArrayList<>();
        remainingQuestions = new ArrayList<>();

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        // Set click listeners for the answer options
        option1.setOnClickListener(v -> checkAnswer(option1));
        option2.setOnClickListener(v -> checkAnswer(option2));
        option3.setOnClickListener(v -> checkAnswer(option3));
        option4.setOnClickListener(v -> checkAnswer(option4));

        // Load questions based on the selected topic
        loadQuestions();
    }

    private void loadQuestions() {
        String topic = getIntent().getStringExtra("topic");

        if (topic == null || topic.isEmpty()) {
            Toast.makeText(this, "No topic selected.", Toast.LENGTH_SHORT).show();
            finish(); // End activity if no topic is provided
            return;
        }

        db.collection("questions")
                .whereEqualTo("topic", topic)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        questions.add(document.toObject(Question.class));
                    }

                    if (questions.isEmpty()) {
                        Toast.makeText(this, "No questions found for this topic.", Toast.LENGTH_SHORT).show();
                        finish(); // End activity if no questions are available
                    } else {
                        // Shuffle questions and select only 5
                        Collections.shuffle(questions);
                        remainingQuestions = questions.subList(0, 5); // Only select 5 questions
                        displayQuestion();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                    finish(); // End activity if there's an error loading questions
                });
    }

    private void displayQuestion() {
        if (questionIndex < remainingQuestions.size()) {
            Question question = remainingQuestions.get(questionIndex);
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
                nextQuestion(false); // Move to the next question if time runs out
            }
        }.start();
    }

    private void checkAnswer(Button selectedOption) {
        Question question = remainingQuestions.get(questionIndex);
        boolean isCorrect = selectedOption.getText().equals(question.getCorrectOption());

        // Update score if the answer is correct
        if (isCorrect) {
            score++;
            scoreText.setText("Score: " + score);
        }

        // Move to the next question regardless of whether the answer is correct or not
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
        // Calculate the percentage
        double percentage = ((double) score / remainingQuestions.size()) * 100;

        // Save the score to Firestore
        firestoreUtils.saveUserScore(score, remainingQuestions.size(), percentage);

        // Navigate to the ScoreSummaryActivity
        Intent intent = new Intent(QuizActivity.this, ScoreSummaryActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", remainingQuestions.size());
        startActivity(intent);
        finish();
    }
}
