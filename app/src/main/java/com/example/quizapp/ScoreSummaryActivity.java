package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ScoreSummaryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private int score;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_summary);

        db = FirebaseFirestore.getInstance();

        // Retrieve score and total questions from intent
        score = getIntent().getIntExtra("score", 0);
        totalQuestions = getIntent().getIntExtra("totalQuestions", 1);

        TextView scoreText = findViewById(R.id.scoreText);
        TextView percentageText = findViewById(R.id.percentageText);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        Button viewHistoryButton = findViewById(R.id.viewHistoryButton);

        // Calculate percentage
        double percentage = (double) score / totalQuestions * 100;

        // Display score and percentage
        scoreText.setText("Score: " + score + " / " + totalQuestions);
        percentageText.setText("Percentage: " + String.format("%.2f", percentage) + "%");

        // Save score to Firestore
        saveScoreToFirestore(score, totalQuestions, percentage);

        // Play Again button
        playAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(ScoreSummaryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // View History button
        viewHistoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(ScoreSummaryActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void saveScoreToFirestore(int score, int totalQuestions, double percentage) {
        Map<String, Object> scoreData = new HashMap<>();
        scoreData.put("score", score);
        scoreData.put("totalQuestions", totalQuestions);
        scoreData.put("percentage", percentage);
        scoreData.put("date", System.currentTimeMillis());  // Store timestamp

        db.collection("scores")
                .add(scoreData)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(ScoreSummaryActivity.this, "Score saved successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(ScoreSummaryActivity.this, "Failed to save score.", Toast.LENGTH_SHORT).show());
    }
}
