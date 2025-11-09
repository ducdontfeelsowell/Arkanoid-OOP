package org.example.arkanoid.game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressManager {

    // Biến tĩnh lưu trữ thông tin người chơi HIỆN TẠI
    public static String currentPlayerName;
    public static int maxLevelUnlocked;
    public static int currentCoins;
    public static int totalScore; // <-- THÊM MỚI: Tổng điểm

    // Đường dẫn file mới
    private static final String PROFILE_DIR_PATH = "src/main/resources/Profiles/";
    private static final String USER_FILE_PATH = PROFILE_DIR_PATH + "user.txt";

    // Một Map để lưu trữ dữ liệu của TẤT CẢ người chơi
    private static Map<String, UserData> userDatabase = new HashMap<>();

    /**
     * Lớp nội bộ (inner class) để chứa dữ liệu của mỗi người chơi
     */
    private static class UserData {
        int level;
        int coins;
        int score;

        UserData(int level, int coins, int score) {
            this.level = level;
            this.coins = coins;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            // Format để lưu vào file: level,coins,score
            return level + "," + coins + "," + score;
        }
    }

    /**
     * Tải toàn bộ file user.txt vào bộ nhớ (HashMap)
     */
    private static void loadUserDatabase() {
        userDatabase.clear();
        File profileDir = new File(PROFILE_DIR_PATH);
        if (!profileDir.exists()) {
            profileDir.mkdirs(); // Tạo thư mục nếu chưa có
        }

        File userFile = new File(USER_FILE_PATH);
        if (!userFile.exists()) {
            System.out.println("Không tìm thấy user.txt. Sẽ tạo file mới khi lưu.");
            return; // File chưa tồn tại, database rỗng
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("="); // Tách Tên = Dữ liệu
                if (parts.length == 2) {
                    String name = parts[0];
                    String[] data = parts[1].split(","); // Tách Dữ liệu (level,coins,score)
                    if (data.length == 3) {
                        int level = Integer.parseInt(data[0]);
                        int coins = Integer.parseInt(data[1]);
                        int score = Integer.parseInt(data[2]);
                        userDatabase.put(name, new UserData(level, coins, score));
                    }
                }
            }
            System.out.println("Đã tải " + userDatabase.size() + " người chơi từ user.txt.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Lỗi khi đọc file user.txt!");
            e.printStackTrace();
        }
    }

    /**
     * Lưu toàn bộ database (HashMap) trở lại file user.txt (Ghi đè)
     */
    private static void saveUserDatabase() {
        File userFile = new File(USER_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
                // Ghi theo format: TenNguoiChoi=level,coins,score
                writer.write(entry.getKey() + "=" + entry.getValue().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file user.txt!");
            e.printStackTrace();
        }
    }

    public static List<ScoreManager.PlayerScore> seeCurrentData() {
        List<ScoreManager.PlayerScore> checks = new ArrayList<>();
        ScoreManager scoreManager = new ScoreManager();
        for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
            ScoreManager.PlayerScore player = scoreManager.new PlayerScore(entry.getKey(), entry.getValue().getScore());
            checks.add(player);
        }
        return checks;
    }

    /**
     * Được gọi bởi MenuController.
     * Tải dữ liệu nếu người chơi tồn tại, hoặc tạo mới nếu không.
     */
    public static void login(String playerName) {
        currentPlayerName = playerName.trim();
        loadUserDatabase(); // Tải dữ liệu mới nhất từ file

        if (userDatabase.containsKey(currentPlayerName)) {
            // --- NGƯỜI CHƠI CŨ: Tải dữ liệu ---
            System.out.println("Chào mừng trở lại, " + currentPlayerName);
            UserData data = userDatabase.get(currentPlayerName);
            maxLevelUnlocked = data.level;
            currentCoins = data.coins;
            totalScore = data.score;
        } else {
            // --- NGƯỜi CHƠI MỚI: Tạo dữ liệu ---
            System.out.println("Tạo người chơi mới: " + currentPlayerName);
            maxLevelUnlocked = 1;
            currentCoins = 0;
            totalScore = 0;
            // Lưu người chơi mới vào database và lưu file
            userDatabase.put(currentPlayerName, new UserData(maxLevelUnlocked, currentCoins, totalScore));
            saveUserDatabase(); // Ghi đè file với người chơi mới
        }
        System.out.println("Đã đăng nhập: Level=" + maxLevelUnlocked + ", Coins=" + currentCoins + ", Score=" + totalScore);
    }

    /**
     * Lưu trạng thái HIỆN TẠI của người chơi vào database VÀ file.
     */
    private static void saveCurrentProfile() {
        if (currentPlayerName == null || currentPlayerName.isEmpty()) {
            System.err.println("Lỗi: Không có người chơi nào đang đăng nhập để lưu!");
            return;
        }

        // Cập nhật database trong bộ nhớ
        UserData currentData = userDatabase.get(currentPlayerName);
        if (currentData == null) {
            // Trường hợp dự phòng nếu người chơi không có trong map
            currentData = new UserData(maxLevelUnlocked, currentCoins, totalScore);
        }

        currentData.level = maxLevelUnlocked;
        currentData.coins = currentCoins;
        currentData.score = totalScore;

        userDatabase.put(currentPlayerName, currentData);

        // Lưu toàn bộ database (bao gồm thay đổi) ra file
        saveUserDatabase();
    }

    /**
     * Được gọi khi người chơi hoàn thành một màn.
     * Sẽ kiểm tra xem đây có phải là màn mới không trước khi lưu điểm và level.
     * @param levelJustBeaten Cấp độ vừa hoàn thành (ví dụ: 1)
     * @param scoreEarned Điểm số kiếm được trong màn đó
     */
    public static void completeLevel(int levelJustBeaten, int scoreEarned) {
        int nextLevel = levelJustBeaten + 1;

        // Quy tắc: Chỉ cộng điểm VÀ mở khóa level nếu màn vừa qua
        // là màn cao nhất họ từng đạt tới (levelJustBeaten >= maxLevelUnlocked)
        if (levelJustBeaten >= maxLevelUnlocked && nextLevel <= 12) {

            System.out.println("Lần đầu vượt qua màn " + levelJustBeaten + ". Cộng " + scoreEarned + " điểm!");
            maxLevelUnlocked = nextLevel; // Mở khóa màn tiếp theo
            totalScore += scoreEarned; // Chỉ cộng điểm khi là màn mới

            saveCurrentProfile(); // Lưu cả level mới và điểm mới

        } else if (nextLevel > 12 && levelJustBeaten == 12 && maxLevelUnlocked == 12) {
            // Xử lý trường hợp đặc biệt: Đánh bại màn 12
            // Kiểm tra xem điểm đã được cộng cho màn 12 chưa (bằng cách so sánh level)
            // (Logic này có thể cần điều chỉnh nếu bạn muốn cho phép cộng điểm màn 12 nhiều lần)

            // Giả định: totalScore chỉ được cộng 1 lần duy nhất cho mỗi màn
            // Nếu maxLevelUnlocked đã > 12 (ví dụ 13), nghĩa là màn 12 đã được tính điểm.
            if (maxLevelUnlocked <= 12) {
                System.out.println("Lần đầu vượt qua màn 12. Cộng " + scoreEarned + " điểm!");
                totalScore += scoreEarned;
                maxLevelUnlocked = 13; // Đặt giá trị đặc biệt để biết đã hoàn thành
                saveCurrentProfile();
            }
        } else {
            // Màn này đã chơi rồi, không cộng điểm, không lưu
            System.out.println("Đã chơi lại màn " + levelJustBeaten + ". Không cộng điểm.");
        }
    }

    /**
     * Cộng điểm nếu người chơi thua ở màn chơi mới (chưa qua).
     * KHÔNG tăng maxLevelUnlocked.
     */
    public static void addScoreFromFailedLevel(int levelPlayed, int scoreEarned) {
        // Quy tắc: Chỉ cộng điểm nếu đây là màn chơi cao nhất
        // (levelPlayed >= maxLevelUnlocked) VÀ họ chưa hoàn thành game (maxLevelUnlocked <= 12)
        if (levelPlayed >= maxLevelUnlocked && maxLevelUnlocked <= 12) {
            System.out.println("Thua ở màn mới " + levelPlayed + ". Cộng " + scoreEarned + " điểm!");
            totalScore += scoreEarned;
            saveCurrentProfile(); // Chỉ lưu điểm, không lưu level
        } else {
            System.out.println("Thua ở màn cũ " + levelPlayed + ". Không cộng điểm.");
        }
    }

    /**
     * Cộng coin VÀ LƯU.
     */
    public static void addCoins(int amount) {
        if (amount > 0) {
            currentCoins += amount;
            System.out.println("Đã nhận " + amount + " coins. Tổng: " + currentCoins);
            saveCurrentProfile(); // Lưu coin ngay lập tức
        }
    }
}
