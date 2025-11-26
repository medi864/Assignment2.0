package com.example.assignment20;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private int score;
    private EditText nameEditText;
    private RadioGroup avatarRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        score = getIntent().getIntExtra("SCORE", 0);

        TextView scoreView = findViewById(R.id.tv_playerscore);
        scoreView.setText("Score: " + score);

        nameEditText = findViewById(R.id.et_playername);
        avatarRadioGroup = findViewById(R.id.rg_avatar);
        Button submitButton = findViewById(R.id.btn_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
                Intent intent = new Intent(PlayerActivity.this, LeaderboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Load existing scores into Singleton on start (optional, normally done in App start)
        loadScoresToSingleton();
    }

    private void saveScore() {
        String name = nameEditText.getText().toString();
        if (name.isEmpty()) name = "Unknown";

        String color = "Grey";
        int checkedId = avatarRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.rb_blue) color = "Blue";
        else if (checkedId == R.id.rb_orange) color = "Orange";
        else if (checkedId == R.id.rb_green) color = "Green";
        else if (checkedId == R.id.rb_purple) color = "Purple";
        else if (checkedId == R.id.rb_pink) color = "Pink";

        Player newPlayer = new Player(name, color, score);

        // 1. Add to Singleton (In-memory)
        Leaderboard.getInstance().addPlayer(newPlayer);

        // 2. Persist to SharedPreferences
        saveLeaderboardToPrefs();
    }

    private void saveLeaderboardToPrefs() {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        JSONArray jsonArray = new JSONArray();

        for (Player p : Leaderboard.getInstance().getLeaderboard()) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("name", p.getPlayerName());
                obj.put("score", p.getPlayerScore());
                obj.put("color", p.getPlayerAvatar());
                jsonArray.put(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        prefs.edit().putString("scores", jsonArray.toString()).apply();
    }

    private void loadScoresToSingleton() {
        SharedPreferences prefs = getSharedPreferences("Leaderboard", Context.MODE_PRIVATE);
        String currentList = prefs.getString("scores", "[]");
        ArrayList<Player> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(currentList);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                list.add(new Player(obj.getString("name"), obj.getString("color"), obj.getInt("score")));
            }
            Leaderboard.getInstance().setLeaderboard(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}