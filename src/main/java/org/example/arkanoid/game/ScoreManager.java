package org.example.arkanoid.game;

import java.io.*;
import java.util.*;

public class ScoreManager {
    public class PlayerScore {
        private String name;
        private int score;

        public PlayerScore(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }


    private PriorityQueue<PlayerScore> topScores = new PriorityQueue<>(
        (a, b) -> {
            int cmp = Integer.compare(b.getScore(), a.getScore());
            if (cmp == 0) {
                cmp = a.getName().compareToIgnoreCase(b.getName());
            }
            return cmp;
        }
    );

    private PriorityQueue<PlayerScore> minChecker = new PriorityQueue<>(
        (a, b) -> {
            int cmp = Integer.compare(a.getScore(), b.getScore());
            return cmp;
        }
    );

    public ScoreManager() {
    }

    public void processScore() {
        List<PlayerScore> checks = ProgressManager.seeCurrentData();
        for (PlayerScore check: checks) {
            if (minChecker.size() < 7) {
                minChecker.add(check);
            } else {
                PlayerScore temp = minChecker.peek();
                if (temp.getScore() < check.getScore()) {
                    System.out.println(temp.getScore());
                    minChecker.poll();
                    minChecker.add(check);
                }
            }
            System.out.println(check.getScore());
        }
        topScores.addAll(minChecker);
    }



    public PriorityQueue<PlayerScore> getTopScores() {
        return topScores;
    }
}
