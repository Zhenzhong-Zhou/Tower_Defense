package scenes;

import gui.MyButton;
import helperMethods.LevelBuild;
import main.Game;
import managers.TileManager;

import java.awt.*;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods {
    private int[][] level;
    private TileManager tileManager;

    private MyButton buttonMenu;

    public Playing(Game game) {
        super(game);

        // Level => tileManager
        level = LevelBuild.getLevelData();
        tileManager = new TileManager();
        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 0, 0, 100, 30);
    }

    @Override
    public void render(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                graphics.drawImage(tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            System.out.println("pressed!");
            SetGameSate(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
    }
}
