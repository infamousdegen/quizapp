package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<ScoreEntry> scoreHistory;

    public HistoryAdapter(List<ScoreEntry> scoreHistory) {
        this.scoreHistory = scoreHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScoreEntry entry = scoreHistory.get(position);
        holder.scoreText.setText("Score: " + entry.getScore() + " / " + entry.getTotalQuestions());
        holder.percentageText.setText("Percentage: " + entry.getPercentage() + "%");

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        String dateString = sdf.format(new Date(entry.getDate()));
        holder.dateText.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return scoreHistory.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView scoreText, percentageText, dateText;

        ViewHolder(View itemView) {
            super(itemView);
            scoreText = itemView.findViewById(R.id.scoreText);
            percentageText = itemView.findViewById(R.id.percentageText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}
