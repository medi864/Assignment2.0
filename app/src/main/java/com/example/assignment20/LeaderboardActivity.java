package com.example.assignment20;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        LinearLayout listContainer = findViewById(R.id.ll_list_container);

        // Get data from Singleton
        ArrayList<Player> players = Leaderboard.getInstance().getLeaderboard();

        if (players.isEmpty()) {
            TextView emptyView = new TextView(this);
            emptyView.setText("No scores yet!");
            emptyView.setPadding(20, 20, 20, 20);
            listContainer.addView(emptyView);
        } else {
            for (Player p : players) {
                // In a real assignment you might use a custom XML row layout inflater
                // Here we create a simple view programmatically
                TextView row = new TextView(this);
                row.setText(p.getPlayerName() + " (" + p.getPlayerAvatar() + ") scored: " + p.getPlayerScore());
                row.setTextSize(18);
                row.setPadding(20, 30, 20, 30);
                listContainer.addView(row);
            }
        }
    }
}