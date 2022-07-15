package scenes;

import helperMethods.LevelBuild;
import main.Game;
import managers.TileManager;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {
    private int[][] level;
    private TileManager tileManager;

    public Playing(Game game) {
        super(game);

        // Level => tileManager
        level = LevelBuild.getLevelData();
        tileManager = new TileManager();
    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                graphics.drawImage(tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
    }
}
