package scenes;

import gui.BottomBar;
import gui.MyButton;
import helperMethods.LevelBuild;
import main.Game;
import managers.TileManager;

import java.awt.*;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {
    private int[][] level;
    private TileManager tileManager;


    private BottomBar bottomBar;

    public Playing(Game game) {
        super(game);

        // Level => tileManager
        level = LevelBuild.getLevelData();
        tileManager = new TileManager();

        bottomBar = new BottomBar(0, 640, 640, 100, this);
    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                graphics.drawImage(tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
        bottomBar.draw(graphics);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseClicked(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseMoved(x, y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseReleased(x, y);
        }
    }
}
