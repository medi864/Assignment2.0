package com.example.assignment20;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Singleton class to manage the leaderboard data.
 * Matches the UML diagram in Appendix D.
 */
public class Leaderboard {
    private static Leaderboard leaderboardInstance;
    private ArrayList<Player> leaderboard;
    private static final int MAX_LEADERBOARD_SIZE = 5;

    // Private constructor for Singleton pattern
    private Leaderboard() {
        leaderboard = new ArrayList<>();
    }

    // Public access method
    public static Leaderboard getInstance() {
        if (leaderboardInstance == null) {
            leaderboardInstance = new Leaderboard();
        }
        return leaderboardInstance;
    }

    public void addPlayer(Player player) {
        leaderboard.add(player);
        sortLeaderboard();
        // Keep only top 5
        if (leaderboard.size() > MAX_LEADERBOARD_SIZE) {
            leaderboard.remove(leaderboard.size() - 1);
        }
    }

    // Optional: Method to populate data (e.g., from SharedPreferences)
    // You would call this when the app starts if you implemented persistence
    public void setLeaderboard(ArrayList<Player> players) {
        this.leaderboard = players;
        sortLeaderboard();
    }

    public ArrayList<Player> getLeaderboard() {
        return leaderboard;
    }

    private void sortLeaderboard() {
        Collections.sort(leaderboard, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                // Descending order by score
                return Integer.compare(p2.getPlayerScore(), p1.getPlayerScore());
            }
        });
    }
}