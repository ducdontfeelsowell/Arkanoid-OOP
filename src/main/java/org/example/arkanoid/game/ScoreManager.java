package org.example.arkanoid.game;

import java.io.*;
import java.util.*;

public class ScoreManager {
    public static class PlayerScore {
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

    // When score history was still a thing
    // private static final String SCORE_FILE_PATH = "src/main/resources/Profiles/scoreboard.txt";
    private PriorityQueue<PlayerScore> topScores = new PriorityQueue<>(
            (a, b) -> {
                int cmp = Integer.compare(b.getScore(), a.getScore()); // Score first, b first to get reverse order
                if (cmp == 0) {
                    cmp = a.getName().compareToIgnoreCase(b.getName()); // Name if tie, a first to get alphabetical order
                }
                return cmp;
            }
    );

    private PriorityQueue<PlayerScore> minChecker = new PriorityQueue<>(
            (a, b) -> {
                int cmp = Integer.compare(a.getScore(), b.getScore()); // Score, min-heap as a first
                return cmp;
            }
    );

    public ScoreManager() {
    }

    public void processScore() {
        // Lỗi "cannot find symbol" đã xảy ra ở đây.
        // Bây giờ nó sẽ hoạt động sau khi chúng ta thêm seeCurrentData() vào ProgressManager.
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
