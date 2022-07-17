package scenes;

import gui.Toolbar;
import helperMethods.LoadSave;
import main.Game;
import objects.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {
    private int[][] level;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private int lastTileX, lastTileY, lastTileId;
    private boolean drawSelected;
    private Toolbar toolbar;

    public Editing(Game game) {
        super(game);
        loadDefaultLevel();
        toolbar = new Toolbar(0, 640, 640, 100, this);
    }

    private void loadDefaultLevel() {
        level = LoadSave.GetLevelData("default_level");
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        toolbar.draw(graphics);
        drawSelectedTile(graphics);
    }

    private void drawLevel(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                graphics.drawImage(getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    private BufferedImage getSprite(int spriteID) {
        return getGame().getTileManager().getSprite(spriteID);
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null && drawSelected) {
            graphics.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void saveLevel() {
        LoadSave.SaveLevel("default_level", level);
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        drawSelected = true;
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
    public void mouseClicked(int x, int y) {
        if(y >= 640) {
            toolbar.mouseClicked(x, y);
        } else {
            changeTile(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640) {
            toolbar.mouseMoved(x, y);
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
            toolbar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if(y >= 640) {
            toolbar.mouseReleased();
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