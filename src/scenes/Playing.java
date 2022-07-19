package scenes;

import gui.ActionBar;
import helperMethods.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;

import java.awt.*;
import java.util.ArrayList;

import static helperMethods.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {
    private final ActionBar actionBar;
    private final EnemyManager enemyManager;
    private TowerManager towerManager;
    private int[][] level;
    private int mouseX, mouseY;
    private PathPoint start, end;
    private Tower selectedTower;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
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
        towerManager.update();
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        actionBar.draw(graphics);
        enemyManager.draw(graphics);
        towerManager.draw(graphics);
        drawSelectedTower(graphics);
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

    private void drawSelectedTower(Graphics graphics) {
        if(selectedTower != null) {
            graphics.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
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
        return getGame().getTileManager().getTile(id).getTileType();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            actionBar.mouseClicked(x, y);
        }
        else {
            if(selectedTower != null) {
                if(isTileGrass(mouseX, mouseY)) {
                    towerManager.addTower(selectedTower, mouseX, mouseY);
                    selectedTower = null;
                }
            }
        }
    }

    private boolean isTileGrass(int x, int y) {
        int id = level[y/32][x/32];
        int tileType = getGame().getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640) {
            actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if(y >= 640) {
            actionBar.mouseReleased();
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }
}
