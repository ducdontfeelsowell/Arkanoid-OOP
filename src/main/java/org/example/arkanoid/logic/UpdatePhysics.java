package org.example.arkanoid.logic;

import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.GameManager;

public class UpdatePhysics {


    public static void update(BallManager ballManager, GameManager gm) {
        ballManager.update(gm);
    }
}