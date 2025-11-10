package org.example.arkanoid.game;

import org.example.arkanoid.config.Constants;

import java.io.*;
import java.util.*;
// Import lớp PlayerScore (giả định là static) từ ScoreManager (từ Bản 1)
import org.example.arkanoid.game.ScoreManager.PlayerScore;

public class ProgressManager {

    // --- HẰNG SỐ ĐỘ KHÓ (TỪ BẢN 1) ---
    /** 0 = Easy */
    public static final int DIFFICULTY_EASY = 0;
    /** 1 = Normal */
    public static final int DIFFICULTY_NORMAL = 1;
    /** 2 = Hard */
    public static final int DIFFICULTY_HARD = 2;

    /** 0 = Chưa hoàn thành Easy */
    public static final int STATUS_LOCKED = 0;
    /** 1 = Đã hoàn thành Easy */
    public static final int STATUS_EASY_COMPLETED = 1;
    /** 2 = Đã hoàn thành Normal */
    public static final int STATUS_NORMAL_COMPLETED = 2;
    /** 3 = Đã hoàn thành Hard */
    public static final int STATUS_HARD_COMPLETED = 3;


    // --- BIẾN TĨNH LƯU TRỮ (GỘP TỪ 2 BẢN) ---
    public static String currentPlayerName;
    public static int maxLevelUnlocked;
    public static int currentCoins;
    public static int totalScore;

    // (Từ Bản 1)
    /** Mảng lưu trạng thái hoàn thành độ khó (index 1-12) */
    public static int[] currentDifficultyCompleted = new int[13]; // 0=Locked, 1=EasyDone, 2=NormalDone, 3=HardDone

    // (Từ Bản 2)
    public static String equippedBall;
    public static String equippedTrail;
    public static String equippedPaddle;
    public static Set<String> ownedBalls = new HashSet<>();
    public static Set<String> ownedTrails = new HashSet<>();
    public static Set<String> ownedPaddles = new HashSet<>();


    // --- QUẢN LÝ FILE ---
    private static final String PROFILE_DIR_PATH = "src/main/resources/Profiles/";
    private static final String USER_FILE_PATH = PROFILE_DIR_PATH + "user.txt";

    // Database trong bộ nhớ
    private static Map<String, UserData> userDatabase = new HashMap<>();

    /**
     * Lớp nội bộ chứa TẤT CẢ dữ liệu người chơi (Gộp từ 2 bản)
     */
    private static class UserData {
        int maxLevel;
        int coins;
        int score;

        // Từ Bản 1
        int[] difficultyCompleted;

        // Từ Bản 2
        String equippedBall;
        String equippedTrail;
        String equippedPaddle;
        Set<String> ownedBalls;
        Set<String> ownedTrails;
        Set<String> ownedPaddles;

        /**
         * Constructor gộp
         */
        UserData(int maxLevel, int coins, int score, int[] difficultyCompleted,
                 String eqBall, String eqTrail, String eqPaddle,
                 Set<String> ownBalls, Set<String> ownTrails, Set<String> ownPaddles) {

            this.maxLevel = maxLevel;
            this.coins = coins;
            this.score = score;

            // (Từ Bản 1) Đảm bảo mảng độ khó hợp lệ
            if (difficultyCompleted == null || difficultyCompleted.length != 13) {
                this.difficultyCompleted = new int[13];
            } else {
                this.difficultyCompleted = difficultyCompleted;
            }

            // (Từ Bản 2) Dữ liệu shop
            this.equippedBall = eqBall;
            this.equippedTrail = eqTrail;
            this.equippedPaddle = eqPaddle;
            this.ownedBalls = (ownBalls != null) ? ownBalls : new HashSet<>();
            this.ownedTrails = (ownTrails != null) ? ownTrails : new HashSet<>();
            this.ownedPaddles = (ownPaddles != null) ? ownPaddles : new HashSet<>();
        }

        // Getter cho seeCurrentData() (Từ Bản 2)
        public int getScore() {
            return score;
        }

        /**
         * Chuyển đổi UserData thành chuỗi (Format 6 phần)
         * Format mới: progress|equipped|ownedBalls|ownedTrails|ownedPaddles|difficulty
         */
        @Override
        public String toString() {
            // 1. Progress: maxLevel,coins,score
            String progress = maxLevel + "," + coins + "," + score;

            // 2. Equipped: eqBall,eqTrail,eqPaddle
            String equipped = equippedBall + "," + equippedTrail + "," + equippedPaddle;

            // 3. Owned Balls (CSV)
            String ownedBallsStr = String.join(",", ownedBalls);

            // 4. Owned Trails (CSV)
            String ownedTrailsStr = String.join(",", ownedTrails);

            // 5. Owned Paddles (CSV)
            String ownedPaddlesStr = String.join(",", ownedPaddles);

            // 6. Difficulty (CSV): diff1,diff2,...,diff12
            StringBuilder sbDiff = new StringBuilder();
            for (int i = 1; i <= 12; i++) {
                sbDiff.append(difficultyCompleted[i]);
                if (i < 12) sbDiff.append(",");
            }
            String difficultyStr = sbDiff.toString();

            // Kết hợp 6 phần bằng dấu |
            return String.join("|", progress, equipped, ownedBallsStr, ownedTrailsStr, ownedPaddlesStr, difficultyStr);
        }
    }

    /**
     * Tải toàn bộ file user.txt vào bộ nhớ (HashMap)
     * Parser này đã được gộp để xử lý TẤT CẢ các định dạng cũ và định dạng mới.
     */
    private static void loadUserDatabase() {
        userDatabase.clear();
        File profileDir = new File(PROFILE_DIR_PATH);
        if (!profileDir.exists()) profileDir.mkdirs();

        File userFile = new File(USER_FILE_PATH);
        if (!userFile.exists()) {
            System.out.println("Không tìm thấy user.txt. Sẽ tạo file mới khi lưu.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("="); // Tách Tên = Dữ liệu
                if (parts.length != 2) continue;

                String name = parts[0];
                String dataStr = parts[1];

                // --- Dữ liệu mặc định ---
                int level = 1, coins = 0, score = 0;
                int[] difficulties = new int[13]; // Default: 0
                String eqBall = Constants.BALL_SKIN_EARTH;
                String eqTrail = Constants.TRAIL_SKIN_LGBT;
                String eqPaddle = Constants.PADDLE_SKIN_DEFAULT;
                Set<String> ownBalls = new HashSet<>();
                Set<String> ownTrails = new HashSet<>();
                Set<String> ownPaddles = new HashSet<>();

                try {
                    if (dataStr.contains("|")) {
                        // --- ĐỊNH DẠNG V2 (Shop) HOẶC ĐỊNH DẠNG GỘP (6 phần) ---
                        String[] mainParts = dataStr.split("\\|");

                        // 1. Progress (Bắt buộc)
                        String[] progressData = mainParts[0].split(",");
                        level = Integer.parseInt(progressData[0]);
                        coins = Integer.parseInt(progressData[1]);
                        score = Integer.parseInt(progressData[2]);

                        // 2. Equipped
                        if (mainParts.length > 1) {
                            String[] equipped = mainParts[1].split(",");
                            if (equipped.length >= 2) { // Định dạng V2 cũ (ko có paddle)
                                eqBall = equipped[0];
                                eqTrail = equipped[1];
                            }
                            if (equipped.length >= 3) { // Định dạng V2 mới (có paddle)
                                eqPaddle = equipped[2];
                            }
                        }

                        // 3. Owned Balls
                        if (mainParts.length > 2 && !mainParts[2].isEmpty()) {
                            ownBalls.addAll(Arrays.asList(mainParts[2].split(",")));
                        }
                        // 4. Owned Trails
                        if (mainParts.length > 3 && !mainParts[3].isEmpty()) {
                            ownTrails.addAll(Arrays.asList(mainParts[3].split(",")));
                        }
                        // 5. Owned Paddles
                        if (mainParts.length > 4 && !mainParts[4].isEmpty()) {
                            ownPaddles.addAll(Arrays.asList(mainParts[4].split(",")));
                        }
                        // 6. Difficulty (Phần mới gộp)
                        if (mainParts.length > 5 && !mainParts[5].isEmpty()) {
                            String[] diffData = mainParts[5].split(",");
                            if (diffData.length == 12) {
                                for (int i = 0; i < 12; i++) {
                                    difficulties[i + 1] = Integer.parseInt(diffData[i]);
                                }
                            }
                        }

                    } else {
                        // --- ĐỊNH DẠNG V1 (Chỉ có Difficulty, không có |) ---
                        String[] data = dataStr.split(",");
                        if (data.length == 3) { // V1-Legacy (level,coins,score)
                            level = Integer.parseInt(data[0]);
                            coins = Integer.parseInt(data[1]);
                            score = Integer.parseInt(data[2]);
                            // Dùng shop và difficulty mặc định
                        } else if (data.length == 15) { // V1-Mới (level,coins,score,diff1...diff12)
                            level = Integer.parseInt(data[0]);
                            coins = Integer.parseInt(data[1]);
                            score = Integer.parseInt(data[2]);
                            // Dùng shop mặc định
                            for (int i = 0; i < 12; i++) {
                                difficulties[i + 1] = Integer.parseInt(data[i + 3]);
                            }
                        }
                    }

                    // (Từ V2) Luôn đảm bảo vật phẩm mặc định được sở hữu
                    ownBalls.add(Constants.BALL_SKIN_PANCAKE);
                    ownBalls.add(Constants.BALL_SKIN_EARTH);
                    ownTrails.add(Constants.TRAIL_SKIN_LGBT);
                    ownPaddles.add(Constants.PADDLE_SKIN_DEFAULT);

                    // Thêm vào DB
                    userDatabase.put(name, new UserData(level, coins, score, difficulties, eqBall, eqTrail, eqPaddle, ownBalls, ownTrails, ownPaddles));

                } catch (Exception e) {
                    System.err.println("Lỗi parse dữ liệu cho người chơi: " + name);
                    e.printStackTrace();
                }
            }
            System.out.println("Đã tải " + userDatabase.size() + " người chơi từ user.txt.");
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file user.txt!");
            e.printStackTrace();
        }
    }

    /**
     * Lưu toàn bộ database (HashMap) trở lại file user.txt (Ghi đè)
     * Sử dụng định dạng gộp 6-phần (đã xử lý trong UserData.toString())
     */
    private static void saveUserDatabase() {
        File userFile = new File(USER_FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
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
     * (Đã gộp)
     */
    public static void login(String playerName) {
        currentPlayerName = playerName.trim();
        loadUserDatabase(); // Tải dữ liệu mới nhất từ file (sử dụng parser gộp)

        if (userDatabase.containsKey(currentPlayerName)) {
            // --- NGƯỜI CHƠI CŨ: Tải dữ liệu ---
            System.out.println("Chào mừng trở lại, " + currentPlayerName);
            UserData data = userDatabase.get(currentPlayerName);

            // Tải dữ liệu chung
            maxLevelUnlocked = data.maxLevel;
            currentCoins = data.coins;
            totalScore = data.score;

            // Tải dữ liệu V1 (Difficulty)
            currentDifficultyCompleted = data.difficultyCompleted;

            // Tải dữ liệu V2 (Shop)
            equippedBall = data.equippedBall;
            equippedTrail = data.equippedTrail;
            equippedPaddle = data.equippedPaddle;
            ownedBalls = new HashSet<>(data.ownedBalls); // Tạo bản sao
            ownedTrails = new HashSet<>(data.ownedTrails); // Tạo bản sao
            ownedPaddles = new HashSet<>(data.ownedPaddles); // Tạo bản sao

        } else {
            // --- NGƯỜi CHƠI MỚI: Tạo dữ liệu ---
            System.out.println("Tạo người chơi mới: " + currentPlayerName);

            // Dữ liệu chung
            maxLevelUnlocked = 1; // Mở khóa màn 1
            currentCoins = 0;
            totalScore = 0;

            // Dữ liệu V1 (Difficulty)
            currentDifficultyCompleted = new int[13]; // Mảng mới (toàn số 0)

            // Dữ liệu V2 (Shop - Lấy default từ V2)
            equippedBall = Constants.BALL_SKIN_EARTH;
            equippedTrail = Constants.TRAIL_SKIN_LGBT;
            equippedPaddle = Constants.PADDLE_SKIN_DEFAULT;
            ownedBalls = new HashSet<>();
            ownedTrails = new HashSet<>();
            ownedPaddles = new HashSet<>();
            ownedBalls.add(Constants.BALL_SKIN_EARTH);
            ownedBalls.add(Constants.BALL_SKIN_PANCAKE);
            ownedTrails.add(Constants.TRAIL_SKIN_LGBT);
            ownedPaddles.add(Constants.PADDLE_SKIN_DEFAULT);

            // Lưu người chơi mới vào database và lưu file
            userDatabase.put(currentPlayerName, new UserData(
                    maxLevelUnlocked, currentCoins, totalScore, currentDifficultyCompleted,
                    equippedBall, equippedTrail, equippedPaddle,
                    ownedBalls, ownedTrails, ownedPaddles
            ));
            saveUserDatabase(); // Ghi đè file với người chơi mới
        }
        System.out.println("Đã đăng nhập: Level Mở Khóa=" + maxLevelUnlocked + ", Coins=" + currentCoins + ", Score=" + totalScore);
        System.out.println("Trang bị: " + equippedBall + ", " + equippedTrail + ", " + equippedPaddle);
    }

    /**
     * Lưu trạng thái HIỆN TẠI của người chơi vào database VÀ file.
     * (Đã gộp)
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
            currentData = new UserData(
                    maxLevelUnlocked, currentCoins, totalScore, currentDifficultyCompleted,
                    equippedBall, equippedTrail, equippedPaddle,
                    ownedBalls, ownedTrails, ownedPaddles);
        }

        // Cập nhật TẤT CẢ dữ liệu
        currentData.maxLevel = maxLevelUnlocked;
        currentData.coins = currentCoins;
        currentData.score = totalScore;

        // Dữ liệu V1
        currentData.difficultyCompleted = currentDifficultyCompleted;

        // Dữ liệu V2
        currentData.equippedBall = equippedBall;
        currentData.equippedTrail = equippedTrail;
        currentData.equippedPaddle = equippedPaddle;
        currentData.ownedBalls = new HashSet<>(ownedBalls); // Lưu bản sao
        currentData.ownedTrails = new HashSet<>(ownedTrails); // Lưu bản sao
        currentData.ownedPaddles = new HashSet<>(ownedPaddles); // Lưu bản sao

        userDatabase.put(currentPlayerName, currentData);

        // Lưu toàn bộ database (bao gồm thay đổi) ra file
        saveUserDatabase();
    }


    // ===================================================================
    // LOGIC HOÀN THÀNH MÀN (LẤY TỪ BẢN 1 - CÓ ĐỘ KHÓ)
    // ===================================================================

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
        int nextLevel = levelJustBeaten + 1;

        if (levelJustBeaten == maxLevelUnlocked && nextLevel <= 12) {
            System.out.println("Mở khóa màn " + nextLevel);
            maxLevelUnlocked = nextLevel; // Mở khóa màn tiếp theo

        } else if (levelJustBeaten == 12 && maxLevelUnlocked == 12) {
            // Xử lý hoàn thành game (Màn 12)
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
     * THEO LOGIC MỚI (TỪ BẢN 1): Không cộng điểm khi thua.
     */
    public static void addScoreFromFailedLevel(int levelPlayed, int scoreEarned, int difficultyPlayed) {
        // [Suy luận] Theo yêu cầu mới (Bản 1), điểm chỉ được cộng khi HOÀN THÀNH một độ khó mới.
        // Do đó, chúng ta không cộng điểm khi thua.
        System.out.println("Thua ở màn " + levelPlayed + " (Độ khó " + difficultyPlayed + "). Không cộng điểm khi thua.");
        // Không gọi saveCurrentProfile()
    }

    /**
     * Cộng coin VÀ LƯU. (Giống hệt ở cả 2 bản)
     */
    public static void addCoins(int amount) {
        if (amount > 0) {
            currentCoins += amount;
            System.out.println("Đã nhận " + amount + " coins. Tổng: " + currentCoins);
            saveCurrentProfile(); // Lưu coin ngay lập tức
        }
    }

    // ===================================================================
    // HÀM CHO SCOREBOARD (LẤY TỪ BẢN 1 - SẠCH HƠN)
    // ===================================================================

    /**
     * Trả về danh sách tên và tổng điểm của TẤT CẢ người chơi.
     * Được sử dụng bởi ScoreManager.
     * @return List<PlayerScore>
     */
    public static List<PlayerScore> seeCurrentData() {
        // Tải lại DB để đảm bảo dữ liệu là mới nhất
        loadUserDatabase();

        List<PlayerScore> scores = new ArrayList<>();

        if (userDatabase == null || userDatabase.isEmpty()) {
            System.out.println("ProgressManager.seeCurrentData: Không có dữ liệu người chơi để xử lý.");
            return scores; // Trả về danh sách rỗng
        }

        // Duyệt qua map (database)
        for (Map.Entry<String, UserData> entry : userDatabase.entrySet()) {
            String name = entry.getKey();
            int score = entry.getValue().getScore(); // Dùng getter (từ V2)

            // Tạo đối tượng PlayerScore (đã import từ V1) và thêm vào danh sách
            scores.add(new PlayerScore(name, score));
        }

        System.out.println("ProgressManager.seeCurrentData: Đã xử lý " + scores.size() + " điểm số.");
        return scores;
    }


    // ===================================================================
    // CÁC PHƯƠNG THỨC CHO SHOP (LẤY TỪ BẢN 2 - BỔ SUNG PADDLE)
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

    public static boolean isPaddleOwned(String paddleId) {
        return ownedPaddles.contains(paddleId);
    }

    public static String getEquippedBall() {
        return equippedBall;
    }

    public static String getEquippedTrail() {
        return equippedTrail;
    }

    public static String getEquippedPaddle() {
        return equippedPaddle;
    }

    /**
     * Trang bị một vật phẩm (chỉ thành công nếu đã sở hữu)
     * (Đã cập nhật Paddle từ V2)
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
     * (Đã cập nhật Paddle từ V2)
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
     * (Từ V2)
     */
    public static String getEquippedPaddlePath() {
        // Cần thêm PADDLE_SKIN_PATHS vào Constants.java
        return Constants.PADDLE_SKIN_PATHS.getOrDefault(equippedPaddle, Constants.PATH_TO_PADDLE_0);
    }
}