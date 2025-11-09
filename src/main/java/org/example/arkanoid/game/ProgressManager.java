package org.example.arkanoid.game;

import java.io.*;

public class ProgressManager {


    public static int maxLevelUnlocked;
    public static int currentCoins;

    private static final String SAVE_FILE_PATH = "/Progress/progress.txt";
    private static final String COIN_SAVE_FILE_PATH = "/Progress/coin.txt";

    /**
     * Tải tiến độ từ file khi game khởi động.
     */
    public static void loadProgress() {
        File saveFile = new File(SAVE_FILE_PATH);
        if (saveFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(saveFile))) {
                String line = reader.readLine();
                maxLevelUnlocked = Integer.parseInt(line);


                if (maxLevelUnlocked < 1) maxLevelUnlocked = 1;
                if (maxLevelUnlocked > 12) maxLevelUnlocked = 12;

            } catch (IOException | NumberFormatException e) {
                System.err.println("Lỗi khi đọc file save! Đặt về mặc định.");
                maxLevelUnlocked = 1;
            }
        } else {
            System.out.println("Không tìm thấy file save. Tạo file mới.");
            maxLevelUnlocked = 1;
            saveProgress();
        }
        System.out.println("Tiến độ đã tải. Màn cao nhất đã mở: " + maxLevelUnlocked);

        loadCoins();
    }

    private static void loadCoins() {
        File coinFile = new File(COIN_SAVE_FILE_PATH);
        if (coinFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(coinFile))) {
                String line = reader.readLine();
                currentCoins = Integer.parseInt(line);
                if (currentCoins < 0) currentCoins = 0;
            } catch (IOException | NumberFormatException e) {
                System.err.println("Lỗi khi đọc file coin! Đặt về 0.");
                currentCoins = 0;
            }
        } else {
            System.out.println("Không tìm thấy file coin. Đặt về 0.");
            currentCoins = 0;
            saveCoins(); // Tạo file coin nếu chưa có
        }
        System.out.println("Coins đã tải: " + currentCoins);
    }

    public static void saveProgress() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_PATH))) {

            writer.write(String.valueOf(maxLevelUnlocked));

        } catch (IOException e) {

            System.err.println("Lỗi khi lưu tiến độ!");

            e.printStackTrace();

        }
        saveCoins();
    }

    private static void saveCoins() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COIN_SAVE_FILE_PATH))) {
            writer.write(String.valueOf(currentCoins));
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu coin!");
            e.printStackTrace();
        }
    }

    public static void addCoins(int amount) {
        if (amount > 0) {
            currentCoins += amount;
            System.out.println("Đã nhận " + amount + " coins. Tổng: " + currentCoins);
            saveCoins(); // Lưu ngay khi nhận
        }
    }

    /**
     * Được gọi khi người chơi hoàn thành một màn.
     * @param levelJustBeaten Màn mà người chơi vừa thắng.
     */
    public static void unlockNextLevel(int levelJustBeaten) {
        int nextLevel = levelJustBeaten + 1;


        if (nextLevel > maxLevelUnlocked && nextLevel <= 12) {
            maxLevelUnlocked = nextLevel;
            saveProgress();
            System.out.println("Đã mở khóa màn " + maxLevelUnlocked);
        }
    }
}