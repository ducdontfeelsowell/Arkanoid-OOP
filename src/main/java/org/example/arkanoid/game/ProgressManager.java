package org.example.arkanoid.game;

import java.io.*;

public class ProgressManager {


    public static int maxLevelUnlocked;

    private static final String SAVE_FILE_PATH = "target/classes/Progress/progress.txt";
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
    }

    public static void saveProgress() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_PATH))) {

            writer.write(String.valueOf(maxLevelUnlocked));

        } catch (IOException e) {

            System.err.println("Lỗi khi lưu tiến độ!");

            e.printStackTrace();

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