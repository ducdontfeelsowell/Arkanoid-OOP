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

    /* For score history, RIP
    public void loadScore() {
        File scoreFile = new File(SCORE_FILE_PATH);
        if (scoreFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
                String player;
                while ((player = reader.readLine()) != null) {
                    Scanner playerScan = new Scanner(player);
                    String name = playerScan.next();
                    int score = playerScan.nextInt();
                    playerScan.close();
                    minChecker.add(new PlayerScore(name, score));
                    processScore();
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Lỗi khi đọc file score!");
            }
        } else {
            System.out.println("Không tìm thấy file score.");
        }
    }

    public void saveScore() {
        processScore();
        File scoreFile = new File(SCORE_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            while (!minChecker.isEmpty()) {
                PlayerScore player = minChecker.poll();
                writer.write(player.getName() + " " + player.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file user.txt!");
            e.printStackTrace();
        }
    }
     */

    public PriorityQueue<PlayerScore> getTopScores() {
        return topScores;
    }
}
