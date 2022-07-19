package scenes;

import gui.ActionBar;
import helperMethods.LoadSave;
import main.Game;
import managers.EnemyManager;
import objects.PathPoint;

import java.awt.*;
import java.util.ArrayList;

public class Playing extends GameScene implements SceneMethods {
    private final ActionBar bottomBar;
    private final EnemyManager enemyManager;
    private int[][] level;
    private int mouseX, mouseY;
    private PathPoint start, end;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        bottomBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
    }

    private void loadDefaultLevel() {
        level = LoadSave.GetLevelData("default_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("default_level");
        assert points != null;
        start = points.get(0);
        end = points.get(1);
    }

    public void update() {
        updateTick();
        enemyManager.update();
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        bottomBar.draw(graphics);
        enemyManager.draw(graphics);
    }

    private void drawLevel(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if(isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if(xCord < 0 || xCord > 19) {
            return 0;
        }
        if(yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = level[y / 32][x / 32];
        return getGame().getTileManager().getTitle(id).getTileType();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseClicked(x, y);
        }
//        else {
//            enemyManager.addEnemy(x, y);
//        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
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
            bottomBar.mouseReleased();
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }
}
