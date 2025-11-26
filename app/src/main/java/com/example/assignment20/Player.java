package com.example.assignment20;

public class Player {
    private String playerName;
    private String playerAvatar;
    private int playerScore;

    public Player(String name, String avatar, int score) {
        this.playerName = name;
        this.playerAvatar = avatar;
        this.playerScore = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerAvatar() {
        return playerAvatar;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}