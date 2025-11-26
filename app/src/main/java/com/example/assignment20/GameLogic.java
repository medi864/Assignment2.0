package com.example.assignment20;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    private static final long MOLE_DISPLAY_TIME = 800; // milliseconds
    private static final long GAME_DURATION = 30000; // 30 seconds

    private Context context;
    private ArrayList<ImageView> moleViews;
    private ArrayList<Mole> moles;
    private TextView scoreTextView;
    private TextView timerTextView;

    private int currentScore;
    private Handler moleHandler;
    private Runnable moleRunnable;
    private CountDownTimer gameTimer;
    private Random random;
    private int currentMoleIndex = -1;

    public GameLogic(Context context, ArrayList<ImageView> moleViews, TextView scoreText, TextView timerText) {
        this.context = context;
        this.moleViews = moleViews;
        this.scoreTextView = scoreText;
        this.timerTextView = timerText;
        this.random = new Random();
        this.moles = new ArrayList<>();
        this.moleHandler = new Handler();

        initializeMoles();
    }

    private void initializeMoles() {
        for (int i = 0; i < moleViews.size(); i++) {
            ImageView view = moleViews.get(i);
            Mole mole = new Mole(i, view);
            moles.add(mole);

            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoleClicked(index);
                }
            });
        }
    }

    public void startGame() {
        currentScore = 0;
        updateScoreText();
        startTimer();
        startMoleLoop();
    }

    private void startTimer() {
        gameTimer = new CountDownTimer(GAME_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                stopMoleLoop();
                timerTextView.setText("Timer: 0");
                if (context instanceof GameActivity) {
                    ((GameActivity) context).navigateToPlayerActivity(currentScore);
                }
            }
        }.start();
    }

    private void startMoleLoop() {
        moleRunnable = new Runnable() {
            @Override
            public void run() {
                hideMole();
                int newIndex = random.nextInt(moles.size());
                showMole(newIndex);
                moleHandler.postDelayed(this, MOLE_DISPLAY_TIME);
            }
        };
        moleHandler.post(moleRunnable);
    }

    private void stopMoleLoop() {
        moleHandler.removeCallbacks(moleRunnable);
        hideMole();
    }

    private void showMole(int index) {
        currentMoleIndex = index;
        Mole mole = moles.get(index);
        mole.setVisible(true);
        mole.getImageView().setImageResource(R.drawable.img_mole);
    }

    private void hideMole() {
        if (currentMoleIndex != -1) {
            Mole mole = moles.get(currentMoleIndex);
            mole.setVisible(false);
            mole.getImageView().setImageResource(R.drawable.img_without_mole);
            currentMoleIndex = -1;
        }
    }

    private void onMoleClicked(int index) {
        Mole mole = moles.get(index);
        if (mole.isVisible()) {
            currentScore++;
            updateScoreText();
            hideMole();

            // Restart loop immediately for better game feel
            moleHandler.removeCallbacks(moleRunnable);
            moleHandler.postDelayed(moleRunnable, 500);
        }
    }

    private void updateScoreText() {
        scoreTextView.setText("Score: " + currentScore);
    }
}