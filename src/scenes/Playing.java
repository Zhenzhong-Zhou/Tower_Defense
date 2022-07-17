package scenes;

import gui.ActionBar;
import helperMethods.LoadSave;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods {
    private final ActionBar bottomBar;
    private int[][] level;
    private int mouseX, mouseY;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        bottomBar = new ActionBar(0, 640, 640, 100, this);
    }

    private void loadDefaultLevel() {
        level = LoadSave.GetLevelData("default_level");
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        bottomBar.draw(graphics);
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
}
