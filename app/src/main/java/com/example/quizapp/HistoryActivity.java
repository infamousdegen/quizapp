package com.example.quizapp;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<ScoreEntry> scoreHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = FirebaseFirestore.getInstance();
        scoreHistory = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new HistoryAdapter(scoreHistory);
            recyclerView.setAdapter(adapter);
        }

        // Ensure the user is logged in before fetching history
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loadScoreHistory();
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadScoreHistory() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user's UID
        db.collection("scores")
                .whereEqualTo("userId", userId) // Filter by userId
                .get() // Removed the orderBy() method to avoid needing an index
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        scoreHistory.clear();  // Clear list to prevent duplicates on reloading
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            ScoreEntry entry = document.toObject(ScoreEntry.class);
                            scoreHistory.add(entry);
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(HistoryActivity.this, "History loaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HistoryActivity.this, "No history available", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(HistoryActivity.this, "Failed to load history: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}
