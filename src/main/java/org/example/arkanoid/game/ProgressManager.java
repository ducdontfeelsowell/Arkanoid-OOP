package org.example.arkanoid.game;

import org.example.arkanoid.config.Constants;

import java.io.*;
import java.util.*;

public class ProgressManager {

    // Biến tĩnh lưu trữ thông tin người chơi HIỆN TẠI
    public static String currentPlayerName;
    public static int maxLevelUnlocked;
    public static int currentCoins;
    public static int totalScore;

    // --- DỮ LIỆU SHOP MỚI ---
    public static String equippedBall;
    public static String equippedTrail;
    public static String equippedPaddle; // <-- THÊM MỚI

    public static Set<String> ownedBalls = new HashSet<>();
    public static Set<String> ownedTrails = new HashSet<>();
    public static Set<String> ownedPaddles = new HashSet<>(); // <-- THÊM MỚI


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
        String equippedBall;
        String equippedTrail;
        String equippedPaddle; // <-- THÊM MỚI
        Set<String> ownedBalls;
        Set<String> ownedTrails;
        Set<String> ownedPaddles; // <-- THÊM MỚI

        UserData(int level, int coins, int score, String eqBall, String eqTrail, String eqPaddle, Set<String> ownBalls, Set<String> ownTrails, Set<String> ownPaddles) {
            this.level = level;
            this.coins = coins;
            this.score = score;
            this.equippedBall = eqBall;
            this.equippedTrail = eqTrail;
            this.equippedPaddle = eqPaddle; // <-- THÊM MỚI
            // Luôn đảm bảo Set không bị null
            this.ownedBalls = (ownBalls != null) ? ownBalls : new HashSet<>();
            this.ownedTrails = (ownTrails != null) ? ownTrails : new HashSet<>();
            this.ownedPaddles = (ownPaddles != null) ? ownPaddles : new HashSet<>(); // <-- THÊM MỚI
        }

        // --- THÊM MỚI: Getter cho seeCurrentData() ---
        public int getScore() {
            return score;
        }
        // --- KẾT THÚC THÊM MỚI ---

        @Override
        public String toString() {
            // *** SỬA ĐỊNH DẠNG: Thêm 2 phần tử mới ***
            // Format: level,coins,score|eqBall,eqTrail,eqPaddle|ownedBallsCSV|ownedTrailsCSV|ownedPaddlesCSV
            String progress = level + "," + coins + "," + score;
            String equipped = equippedBall + "," + equippedTrail + "," + equippedPaddle; // <-- SỬA
            String ownedBallsStr = String.join(",", ownedBalls);
            String ownedTrailsStr = String.join(",", ownedTrails);
            String ownedPaddlesStr = String.join(",", ownedPaddles); // <-- THÊM MỚI

            // Sử dụng "|" làm dấu phân cách chính
            return progress + "|" + equipped + "|" + ownedBallsStr + "|" + ownedTrailsStr + "|" + ownedPaddlesStr; // <-- SỬA
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
                    // Tách dữ liệu bằng dấu |
                    String[] mainParts = parts[1].split("\\|"); // Phải escape dấu |

                    // --- PHẦN 1: PROGRESS (level,coins,score) ---
                    String[] progressData = mainParts[0].split(",");
                    if (progressData.length != 3) continue; // Bỏ qua dòng lỗi

                    int level = Integer.parseInt(progressData[0]);
                    int coins = Integer.parseInt(progressData[1]);
                    int score = Integer.parseInt(progressData[2]);

                    // --- DỮ LIỆU MẶC ĐỊNH (Cho người chơi cũ) ---
                    String eqBall = Constants.BALL_SKIN_PANCAKE;
                    String eqTrail = Constants.TRAIL_SKIN_LGBT;
                    String eqPaddle = Constants.PADDLE_SKIN_DEFAULT; // <-- THÊM MỚI
                    Set<String> ownBalls = new HashSet<>();
                    Set<String> ownTrails = new HashSet<>();
                    Set<String> ownPaddles = new HashSet<>(); // <-- THÊM MỚI

                    // --- PHẦN 2, 3, 4, 5: SHOP (Nếu tồn tại) ---
                    // *** SỬA: Kiểm tra 5 phần (đã thêm paddle) ***
                    if (mainParts.length == 5) {
                        // Định dạng mới: progress|equipped|ownedBalls|ownedTrails|ownedPaddles
                        // 2. Equipped
                        String[] equipped = mainParts[1].split(",");
                        // *** SỬA: Kiểm tra 3 vật phẩm trang bị ***
                        if (equipped.length == 3) {
                            eqBall = equipped[0];
                            eqTrail = equipped[1];
                            eqPaddle = equipped[2]; // <-- THÊM MỚI
                        }

                        // 3. Owned Balls
                        if (!mainParts[2].isEmpty()) {
                            ownBalls.addAll(Arrays.asList(mainParts[2].split(",")));
                        }

                        // 4. Owned Trails
                        if (!mainParts[3].isEmpty()) {
                            ownTrails.addAll(Arrays.asList(mainParts[3].split(",")));
                        }

                        // 5. Owned Paddles <-- THÊM MỚI
                        if (!mainParts[4].isEmpty()) {
                            ownPaddles.addAll(Arrays.asList(mainParts[4].split(",")));
                        }
                    } else {
                        // Định dạng cũ (mainParts.length == 4 hoặc 1)
                        System.out.println("Phát hiện định dạng cũ, đang di chuyển dữ liệu cho: " + name);
                        // (Sẽ tự động dùng giá trị mặc định ở trên)
                    }

                    // Luôn đảm bảo vật phẩm mặc định được sở hữu
                    ownBalls.add(Constants.BALL_SKIN_PANCAKE);
                    ownTrails.add(Constants.TRAIL_SKIN_LGBT);
                    ownBalls.add(Constants.BALL_SKIN_EARTH);
                    ownPaddles.add(Constants.PADDLE_SKIN_DEFAULT); // <-- THÊM MỚI

                    userDatabase.put(name, new UserData(level, coins, score, eqBall, eqTrail, eqPaddle, ownBalls, ownTrails, ownPaddles)); // <-- SỬA
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
                // Ghi theo format mới (đã được xử lý trong UserData.toString())
                writer.write(entry.getKey() + "=" + entry.getValue().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file user.txt!");
            e.printStackTrace();
        }
    }

    // --- LẤY TỪ BẢN 3 ---
    /**
     * Trả về danh sách PlayerScore cho ScoreManager
     */
    public static List<ScoreManager.PlayerScore> seeCurrentData() {
        List<ScoreManager.PlayerScore> checks = new ArrayList<>();
        ScoreManager scoreManager = new ScoreManager();
        for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
            ScoreManager.PlayerScore player = scoreManager.new PlayerScore(entry.getKey(), entry.getValue().getScore());
            checks.add(player);
        }
        return checks;
    }
    // --- KẾT THÚC LẤY TỪ BẢN 3 ---


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

            // Tải dữ liệu shop
            equippedBall = data.equippedBall;
            equippedTrail = data.equippedTrail;
            equippedPaddle = data.equippedPaddle; // <-- THÊM MỚI
            ownedBalls = new HashSet<>(data.ownedBalls); // Tạo bản sao
            ownedTrails = new HashSet<>(data.ownedTrails); // Tạo bản sao
            ownedPaddles = new HashSet<>(data.ownedPaddles); // <-- THÊM MỚI

        } else {
            // --- NGƯỜi CHƠI MỚI: Tạo dữ liệu ---
            System.out.println("Tạo người chơi mới: " + currentPlayerName);
            maxLevelUnlocked = 1;
            currentCoins = 0;
            totalScore = 0;

            // Thiết lập shop mặc định
            equippedBall = Constants.BALL_SKIN_EARTH; // Mặc định
            equippedTrail = Constants.TRAIL_SKIN_LGBT; // Mặc định
            equippedPaddle = Constants.PADDLE_SKIN_DEFAULT; // <-- THÊM MỚI
            ownedBalls = new HashSet<>();
            ownedTrails = new HashSet<>();
            ownedPaddles = new HashSet<>(); // <-- THÊM MỚI
            ownedBalls.add(Constants.BALL_SKIN_EARTH); // Thêm vật phẩm mặc định
            ownedTrails.add(Constants.TRAIL_SKIN_LGBT); // Thêm vật phẩm mặc định
            ownedPaddles.add(Constants.PADDLE_SKIN_DEFAULT); // <-- THÊM MỚI

            // Lưu người chơi mới vào database và lưu file
            userDatabase.put(currentPlayerName, new UserData(
                    maxLevelUnlocked, currentCoins, totalScore,
                    equippedBall, equippedTrail, equippedPaddle, // <-- SỬA
                    ownedBalls, ownedTrails, ownedPaddles // <-- SỬA
            ));
            saveUserDatabase(); // Ghi đè file với người chơi mới
        }
        System.out.println("Đã đăng nhập: Level=" + maxLevelUnlocked + ", Coins=" + currentCoins + ", Score=" + totalScore);
        System.out.println("Trang bị: " + equippedBall + ", " + equippedTrail + ", " + equippedPaddle); // <-- SỬA
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
            currentData = new UserData(maxLevelUnlocked, currentCoins, totalScore,
                    equippedBall, equippedTrail, equippedPaddle, // <-- SỬA
                    ownedBalls, ownedTrails, ownedPaddles); // <-- SỬA
        }

        // Cập nhật dữ liệu
        currentData.level = maxLevelUnlocked;
        currentData.coins = currentCoins;
        currentData.score = totalScore;
        currentData.equippedBall = equippedBall;
        currentData.equippedTrail = equippedTrail;
        currentData.equippedPaddle = equippedPaddle; // <-- THÊM MỚI
        currentData.ownedBalls = new HashSet<>(ownedBalls); // Lưu bản sao
        currentData.ownedTrails = new HashSet<>(ownedTrails); // Lưu bản sao
        currentData.ownedPaddles = new HashSet<>(ownedPaddles); // <-- THÊM MỚI

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

        if (levelJustBeaten >= maxLevelUnlocked && nextLevel <= 12) {
            System.out.println("Lần đầu vượt qua màn " + levelJustBeaten + ". Cộng " + scoreEarned + " điểm!");
            maxLevelUnlocked = nextLevel; // Mở khóa màn tiếp theo
            totalScore += scoreEarned; // Chỉ cộng điểm khi là màn mới
            saveCurrentProfile(); // Lưu cả level mới và điểm mới
        } else if (nextLevel > 12 && levelJustBeaten == 12 && maxLevelUnlocked <= 12) {
            if (maxLevelUnlocked <= 12) {
                System.out.println("Lần đầu vượt qua màn 12. Cộng " + scoreEarned + " điểm!");
                totalScore += scoreEarned;
                maxLevelUnlocked = 13; // Đặt giá trị đặc biệt để biết đã hoàn thành
                saveCurrentProfile();
            }
        } else {
            System.out.println("Đã chơi lại màn " + levelJustBeaten + ". Không cộng điểm.");
        }
    }

    /**
     * Cộng điểm nếu người chơi thua ở màn chơi mới (chưa qua).
     * KHÔNG tăng maxLevelUnlocked.
     */
    public static void addScoreFromFailedLevel(int levelPlayed, int scoreEarned) {
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


    // ===================================================================
    // CÁC PHƯƠNG THỨC MỚI CHO SHOP
    // ===================================================================

    public static int getCurrentCoins_Static() {
        return currentCoins;
    }

    public static boolean isBallOwned(String ballId) {
        return ownedBalls.contains(ballId);
    }

    public static boolean isTrailOwned(String trailId) {
        return ownedTrails.contains(trailId);
    }

    // <-- THÊM MỚI -->
    public static boolean isPaddleOwned(String paddleId) {
        return ownedPaddles.contains(paddleId);
    }

    public static String getEquippedBall() {
        return equippedBall;
    }

    public static String getEquippedTrail() {
        return equippedTrail;
    }

    // <-- THÊM MỚI -->
    public static String getEquippedPaddle() {
        return equippedPaddle;
    }

    /**
     * Trang bị một vật phẩm (chỉ thành công nếu đã sở hữu)
     */
    public static void equipItem(String itemId, String itemType) {
        if ("ball".equalsIgnoreCase(itemType)) {
            if (ownedBalls.contains(itemId)) {
                equippedBall = itemId;
                System.out.println("Đã trang bị Ball: " + itemId);
                saveCurrentProfile();
            }
        } else if ("trail".equalsIgnoreCase(itemType)) {
            if (ownedTrails.contains(itemId)) {
                equippedTrail = itemId;
                System.out.println("Đã trang bị Trail: " + itemId);
                saveCurrentProfile();
            }
            // <-- THÊM MỚI -->
        } else if ("paddle".equalsIgnoreCase(itemType)) {
            if (ownedPaddles.contains(itemId)) {
                equippedPaddle = itemId;
                System.out.println("Đã trang bị Paddle: " + itemId);
                saveCurrentProfile();
            }
        }
    }

    /**
     * Mua và tự động trang bị vật phẩm
     * @return true nếu mua thành công, false nếu không đủ tiền
     */
    public static boolean purchaseAndEquipItem(String itemId, String itemType) {
        Integer cost = Constants.ITEM_COSTS.get(itemId);
        if (cost == null) {
            System.err.println("Lỗi: Vật phẩm không có giá: " + itemId);
            return false;
        }

        if (currentCoins >= cost) {
            // Trừ tiền
            currentCoins -= cost;
            System.out.println("Đã mua " + itemId + " với giá " + cost + ". Coins còn lại: " + currentCoins);

            // Thêm vào danh sách sở hữu và trang bị
            if ("ball".equalsIgnoreCase(itemType)) {
                ownedBalls.add(itemId);
                equippedBall = itemId; // Tự động trang bị
            } else if ("trail".equalsIgnoreCase(itemType)) {
                ownedTrails.add(itemId);
                equippedTrail = itemId; // Tự động trang bị
                // <-- THÊM MỚI -->
            } else if ("paddle".equalsIgnoreCase(itemType)) {
                ownedPaddles.add(itemId);
                equippedPaddle = itemId; // Tự động trang bị
            }

            // Lưu lại
            saveCurrentProfile();
            return true;
        } else {
            // Không đủ tiền
            System.out.println("Không đủ tiền! Cần " + cost + " coins.");
            return false;
        }
    }

    /**
     * Lấy đường dẫn ảnh của Ball đang trang bị
     */
    public static String getEquippedBallPath() {
        return Constants.BALL_SKIN_PATHS.getOrDefault(equippedBall, Constants.PATH_TO_BALL_PANCAKE);
    }

    /**
     * Lấy đường dẫn ảnh của Trail đang trang bị
     */
    public static String getEquippedTrailPath() {
        return Constants.TRAIL_SKIN_PATHS.getOrDefault(equippedTrail, Constants.PATH_TO_TRAIL_LGBT);
    }

    /**
     * Lấy đường dẫn ảnh của Paddle đang trang bị
     */
    public static String getEquippedPaddlePath() {
        // Cần thêm PADDLE_SKIN_PATHS vào Constants.java
        return Constants.PADDLE_SKIN_PATHS.getOrDefault(equippedPaddle, Constants.PATH_TO_PADDLE_0);
    }
}