package org.example.arkanoid.logic;

import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.GameManager; // <-- THÊM IMPORT NÀY

public class UpdatePhysics {

    // SỬA ĐỔI: Thêm tham số GameManager gm
    public static void update(BallManager ballManager, GameManager gm) {
        // SỬA ĐỔI: Truyền gm vào ballManager.update
        ballManager.update(gm);
    }
}