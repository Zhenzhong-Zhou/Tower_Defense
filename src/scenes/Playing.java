package scenes;

import gui.BottomBar;
import helperMethods.LevelBuild;
import helperMethods.LoadSave;
import main.Game;
import managers.TileManager;
import objects.Tile;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {
    private int[][] level;
    private final TileManager tileManager;
    private final BottomBar bottomBar;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelected;

    public Playing(Game game) {
        super(game);

        // Level => tileManager
        level = LevelBuild.getLevelData();
        tileManager = new TileManager();

        bottomBar = new BottomBar(0, 640, 640, 100, this);

//        LoadSave.CreateFile();
//        LoadSave.WriteToFile();
//        LoadSave.ReadFromFile();
        createDefaultLevel();
        loadDefaultLevel();
    }

    private void createDefaultLevel() {
        int[] array = new int[400];
        for(int i =0; i < array.length; i++) {
            array[i] = 0;
        }
        LoadSave.CreateLevel("default_level", array);
    }

    private void loadDefaultLevel() {
        level = LoadSave.GetLevelData("default_level");
    }

    public void saveLevel() {
        LoadSave.SaveLevel("default_level", level);
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
        drawSelectedTile(graphics);
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null && drawSelected) {
            graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelected = true;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    private void changeTile(int mouseX, int mouseY) {
        if(selectedTile != null) {
            int tileX = mouseX / 32;
            int tileY = mouseY / 32;

            if(lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId()) {
                return;
            }
            lastTileX = tileX;
            lastTileY = tileY;

            level[tileY][tileX] = selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            bottomBar.mouseMoved(x, y);
            drawSelected = false;
        } else {
            drawSelected = true;
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
        if(y >= 640) {

        } else {
            changeTile(x, y);
        }
    }
}
