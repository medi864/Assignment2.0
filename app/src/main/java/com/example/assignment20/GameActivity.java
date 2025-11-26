package com.example.assignment20;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private GameLogic gameLogic;
    private ArrayList<ImageView> moleViews;
    private TextView scoreTextView;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.tv_score_text);
        timerTextView = findViewById(R.id.tv_timer_text);
        moleViews = new ArrayList<>();

        // Add all 9 mole ImageViews to the list
        moleViews.add(findViewById(R.id.iv_mole_1));
        moleViews.add(findViewById(R.id.iv_mole_2));
        moleViews.add(findViewById(R.id.iv_mole_3));
        moleViews.add(findViewById(R.id.iv_mole_4));
        moleViews.add(findViewById(R.id.iv_mole_5));
        moleViews.add(findViewById(R.id.iv_mole_6));
        moleViews.add(findViewById(R.id.iv_mole_7));
        moleViews.add(findViewById(R.id.iv_mole_8));
        moleViews.add(findViewById(R.id.iv_mole_9));

        // Initialize Game Logic
        gameLogic = new GameLogic(this, moleViews, scoreTextView, timerTextView);
        gameLogic.startGame();
    }

    public void navigateToPlayerActivity(int finalScore) {
        Intent intent = new Intent(GameActivity.this, PlayerActivity.class);
        intent.putExtra("SCORE", finalScore);
        startActivity(intent);
        finish(); // Remove GameActivity from back stack so user can't go back to finished game
    }
}