package org.example.arkanoid.game;

import java.io.*;
import java.util.ArrayList; // THÊM MỚI
import java.util.HashMap;
import java.util.List; // THÊM MỚI
import java.util.Map;
// THÊM MỚI: Import lớp PlayerScore (giờ đã là static) từ ScoreManager
import org.example.arkanoid.game.ScoreManager.PlayerScore;

public class ProgressManager {

    // THÊM MỚI: Hằng số độ khó
    /** 0 = Easy */
    public static final int DIFFICULTY_EASY = 0;
    /** 1 = Normal */
    public static final int DIFFICULTY_NORMAL = 1;
    /** 2 = Hard */
    public static final int DIFFICULTY_HARD = 2;

    // THÊM MỚI: Hằng số trạng thái hoàn thành (Dùng trong mảng lưu trữ)
    /** 0 = Chưa hoàn thành Easy */
    public static final int STATUS_LOCKED = 0;
    /** 1 = Đã hoàn thành Easy */
    public static final int STATUS_EASY_COMPLETED = 1;
    /** 2 = Đã hoàn thành Normal */
    public static final int STATUS_NORMAL_COMPLETED = 2;
    /** 3 = Đã hoàn thành Hard */
    public static final int STATUS_HARD_COMPLETED = 3;


    // Biến tĩnh lưu trữ thông tin người chơi HIỆN TẠI
    public static String currentPlayerName;
    public static int maxLevelUnlocked; // Level cao nhất ĐƯỢC PHÉP CHƠI (ví dụ: 3)
    public static int currentCoins;
    public static int totalScore;
    /** THÊM MỚI: Mảng lưu trạng thái hoàn thành độ khó (index 1-12) */
    public static int[] currentDifficultyCompleted = new int[13]; // 0=Locked, 1=EasyDone, 2=NormalDone, 3=HardDone

    // Đường dẫn file mới
    private static final String PROFILE_DIR_PATH = "src/main/resources/Profiles/";
    private static final String USER_FILE_PATH = PROFILE_DIR_PATH + "user.txt";

    // Một Map để lưu trữ dữ liệu của TẤT CẢ người chơi
    private static Map<String, UserData> userDatabase = new HashMap<>();

    /**
     * Lớp nội bộ (inner class) để chứa dữ liệu của mỗi người chơi
     */
    private static class UserData {
        int maxLevel; // Đổi tên 'level' thành 'maxLevel'
        int coins;
        int score;
        int[] difficultyCompleted; // Mảng 13 phần tử (index 1-12)

        UserData(int maxLevel, int coins, int score, int[] difficultyCompleted) {
            this.maxLevel = maxLevel;
            this.coins = coins;
            this.score = score;
            // Đảm bảo mảng luôn hợp lệ
            if (difficultyCompleted == null || difficultyCompleted.length != 13) {
                this.difficultyCompleted = new int[13];
            } else {
                this.difficultyCompleted = difficultyCompleted;
            }
        }

        /** Constructor tương thích ngược (khi load file cũ) */
        UserData(int maxLevel, int coins, int score) {
            this(maxLevel, coins, score, new int[13]); // Gọi constructor mới với mảng rỗng
        }

        @Override
        public String toString() {
            // Format mới: maxLevel,coins,score,diff1,diff2,...,diff12 (Tổng 15 giá trị)
            StringBuilder sb = new StringBuilder();
            sb.append(maxLevel).append(",").append(coins).append(",").append(score);
            // Bắt đầu từ index 1 (bỏ qua 0)
            for (int i = 1; i <= 12; i++) {
                sb.append(",").append(difficultyCompleted[i]);
            }
            return sb.toString();
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
                    String[] data = parts[1].split(","); // Tách Dữ liệu

                    try {
                        if (data.length == 3) {
                            // --- Tương thích ngược (Format cũ: level,coins,score) ---
                            int level = Integer.parseInt(data[0]);
                            int coins = Integer.parseInt(data[1]);
                            int score = Integer.parseInt(data[2]);
                            userDatabase.put(name, new UserData(level, coins, score)); // Dùng constructor cũ
                        } else if (data.length == 15) {
                            // --- Format mới (3 + 12 = 15 giá trị) ---
                            int level = Integer.parseInt(data[0]);
                            int coins = Integer.parseInt(data[1]);
                            int score = Integer.parseInt(data[2]);
                            int[] difficulties = new int[13];
                            for (int i = 0; i < 12; i++) {
                                difficulties[i + 1] = Integer.parseInt(data[i + 3]);
                            }
                            userDatabase.put(name, new UserData(level, coins, score, difficulties));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi parse dữ liệu cho người chơi: " + name);
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
                // Ghi theo format mới: TenNguoiChoi=maxLevel,coins,score,diff1,...
                writer.write(entry.getKey() + "=" + entry.getValue().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file user.txt!");
            e.printStackTrace();
        }
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
            maxLevelUnlocked = data.maxLevel;
            currentCoins = data.coins;
            totalScore = data.score;
            currentDifficultyCompleted = data.difficultyCompleted; // Tải mảng độ khó
        } else {
            // --- NGƯỜi CHƠI MỚI: Tạo dữ liệu ---
            System.out.println("Tạo người chơi mới: " + currentPlayerName);
            maxLevelUnlocked = 1; // Mở khóa màn 1
            currentCoins = 0;
            totalScore = 0;
            currentDifficultyCompleted = new int[13]; // Mảng mới (toàn số 0)
            // Lưu người chơi mới vào database và lưu file
            userDatabase.put(currentPlayerName, new UserData(maxLevelUnlocked, currentCoins, totalScore, currentDifficultyCompleted));
            saveUserDatabase(); // Ghi đè file với người chơi mới
        }
        System.out.println("Đã đăng nhập: Level Mở Khóa=" + maxLevelUnlocked);
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
            currentData = new UserData(maxLevelUnlocked, currentCoins, totalScore, currentDifficultyCompleted);
        }

        currentData.maxLevel = maxLevelUnlocked;
        currentData.coins = currentCoins;
        currentData.score = totalScore;
        currentData.difficultyCompleted = currentDifficultyCompleted; // Lưu cả mảng độ khó

        userDatabase.put(currentPlayerName, currentData);

        // Lưu toàn bộ database (bao gồm thay đổi) ra file
        saveUserDatabase();
    }

    /**
     * Được gọi khi người chơi hoàn thành một màn.
     * Sẽ kiểm tra xem đây có phải là độ khó mới không trước khi lưu điểm và level.
     * @param levelJustBeaten Cấp độ vừa hoàn thành (ví dụ: 1)
     * @param scoreEarned Điểm số kiếm được trong màn đó
     * @param difficultyJustBeaten Độ khó vừa hoàn thành (0=Easy, 1=Normal, 2=Hard)
     */
    public static void completeLevel(int levelJustBeaten, int scoreEarned, int difficultyJustBeaten) {

        // 1. Xác định trạng thái mới
        int newDifficultyStatus;
        if (difficultyJustBeaten == DIFFICULTY_EASY) newDifficultyStatus = STATUS_EASY_COMPLETED; // 1
        else if (difficultyJustBeaten == DIFFICULTY_NORMAL) newDifficultyStatus = STATUS_NORMAL_COMPLETED; // 2
        else newDifficultyStatus = STATUS_HARD_COMPLETED; // 3

        // 2. Cập nhật trạng thái độ khó VÀ cộng điểm
        if (levelJustBeaten > 0 && levelJustBeaten <= 12) {
            // Nếu trạng thái mới (ví dụ: 2-NormalDone) > trạng thái cũ (ví dụ: 1-EasyDone)
            if (newDifficultyStatus > currentDifficultyCompleted[levelJustBeaten]) {

                System.out.println("Lần đầu hoàn thành Level " + levelJustBeaten + " ở độ khó " + difficultyJustBeaten + ". Cộng " + scoreEarned + " điểm!");

                currentDifficultyCompleted[levelJustBeaten] = newDifficultyStatus; // Cập nhật trạng thái
                totalScore += scoreEarned; // Chỉ cộng điểm khi là độ khó mới
            } else {
                System.out.println("Chơi lại Level " + levelJustBeaten + " ở độ khó " + difficultyJustBeaten + " (đã hoàn thành). Không cộng điểm.");
            }
        }

        // 3. Mở khóa level tiếp theo (Logic cũ)
        // Chỉ mở khóa level tiếp theo NẾU người chơi vừa hoàn thành
        // level cao nhất họ từng tới (levelJustBeaten == maxLevelUnlocked)

        int nextLevel = levelJustBeaten + 1;

        if (levelJustBeaten == maxLevelUnlocked && nextLevel <= 12) {
            System.out.println("Mở khóa màn " + nextLevel);
            maxLevelUnlocked = nextLevel; // Mở khóa màn tiếp theo

        } else if (levelJustBeaten == 12 && maxLevelUnlocked == 12) {
            // Xử lý hoàn thành game (Màn 12)
            // (Chỉ cần thắng màn 12 ở bất kỳ độ khó nào là đủ?)
            if (maxLevelUnlocked <= 12) {
                System.out.println("Hoàn thành màn 12. Đánh dấu đã hoàn thành game.");
                maxLevelUnlocked = 13; // Đặt giá trị đặc biệt để biết đã hoàn thành
            }
        }

        // 4. Lưu tất cả thay đổi (điểm, level mở khóa, trạng thái độ khó)
        saveCurrentProfile();
    }

    /**
     * Được gọi khi người chơi thua.
     * THEO LOGIC MỚI: Không cộng điểm khi thua.
     */
    public static void addScoreFromFailedLevel(int levelPlayed, int scoreEarned, int difficultyPlayed) {
        // [Suy luận] Theo yêu cầu mới, điểm chỉ được cộng khi HOÀN THÀNH một độ khó mới.
        // Do đó, chúng ta không cộng điểm khi thua.
        System.out.println("Thua ở màn " + levelPlayed + ". Không cộng điểm khi thua.");

        // Không gọi saveCurrentProfile() vì không có gì thay đổi.
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

    /**
     * THÊM MỚI: Trả về danh sách tên và tổng điểm của TẤT CẢ người chơi.
     * Được sử dụng bởi ScoreManager.
     * @return List<PlayerScore>
     */
    public static List<PlayerScore> seeCurrentData() {
        // Tải lại DB để đảm bảo dữ liệu là mới nhất
        // (ScoreManager có thể được khởi tạo bất cứ lúc nào)
        loadUserDatabase();

        List<PlayerScore> scores = new ArrayList<>();

        if (userDatabase == null || userDatabase.isEmpty()) {
            System.out.println("ProgressManager.seeCurrentData: Không có dữ liệu người chơi để xử lý.");
            return scores; // Trả về danh sách rỗng
        }

        // Duyệt qua map (database)
        for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue().score;

            // Tạo đối tượng PlayerScore (đã import) và thêm vào danh sách
            scores.add(new PlayerScore(name, score));
        }

        System.out.println("ProgressManager.seeCurrentData: Đã xử lý " + scores.size() + " điểm số.");
        return scores;
    }
}