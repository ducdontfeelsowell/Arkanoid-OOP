package org.example.arkanoid.input;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Brick.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {

    public static Brick[][] loadMap(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<Brick[]> brickRows = new ArrayList<>();

        String line;
        int rowIndex = 0;

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) continue;
            String[] tokens = line.split(" ");

            Brick[] row = new Brick[tokens.length];

            for (int colIndex = 0; colIndex < tokens.length; colIndex++) {
                int type = Integer.parseInt(tokens[colIndex]);

                double x = Constants.PLAY_AREA_LEFT + colIndex * Constants.BRICK_WIDTH;
                double y = rowIndex * Constants.BRICK_HEIGHT;

                Brick brick = switch (type) {
                    case 1 -> new NormalBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT,
                            Constants.HIT_POINTS_NORMAL_BRICK, type);
                    case 2 -> new StrongBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT,
                            Constants.HIT_POINTS_STRONG_BRICK, type);
                    case 3 -> new InfBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT,
                            Constants.HIT_POINTS_INF_BRICK, type);
                    case 4 -> new ExplodeBrick(x, y, Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT,
                            Constants.HIT_POINTS_NORMAL_BRICK, type);
                    default -> null;
                };

                row[colIndex] = brick;
            }

            brickRows.add(row);
            rowIndex++;
        }

        reader.close();

        Brick[][] bricks = new Brick[brickRows.size()][];
        for (int i = 0; i < brickRows.size(); i++) {
            bricks[i] = brickRows.get(i);
        }

        return bricks;
    }
}
