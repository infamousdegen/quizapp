package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirestoreUtils firestoreUtils;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirestoreUtils (if necessary)
        firestoreUtils = new FirestoreUtils();

        // Uncomment the line below to add sample questions to Firestore each time the app launches
        // It's recommended to run this once and then comment it out to avoid duplicate entries
        // firestoreUtils.addSampleQuestions();

        // Set up buttons
        Button natureButton = findViewById(R.id.natureButton);
        Button scienceButton = findViewById(R.id.scienceButton);
        Button csButton = findViewById(R.id.csButton);
        Button historyButton = findViewById(R.id.historyButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Set button click listeners
        natureButton.setOnClickListener(view -> startQuiz("Nature"));
        scienceButton.setOnClickListener(view -> startQuiz("Science"));
        csButton.setOnClickListener(view -> startQuiz("Computer Science"));
        historyButton.setOnClickListener(view -> startHistory());

        logoutButton.setOnClickListener(v -> logout());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is logged in and show/hide the logout button accordingly
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // If not logged in, redirect to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Prevents user from going back to MainActivity without logging in
        } else {
            // If logged in, show the logout button
            logoutButton.setVisibility(View.VISIBLE);
        }
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

    // Logout the user
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity after logout
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity to prevent the user from going back after logging out
    }
}
