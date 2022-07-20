package scenes;

import gui.MyButton;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {
    private final ArrayList<BufferedImage> sprites = new ArrayList<>();
    private BufferedImage image;
    private MyButton buttonPlaying, buttonEditing, buttonSettings, buttonQuit;

    public Menu(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons() {
        int width = 150;
        int height = width / 3;
        int x = 640 / 2 - width / 2;
        int y = 150;
        int yOffset = 100;

        buttonPlaying = new MyButton("Play", x, y, width, height);
        buttonEditing = new MyButton("Edit", x, y + yOffset, width, height);
        buttonSettings = new MyButton("Settings", x, y + yOffset * 2, width, height);
        buttonQuit = new MyButton("Quit", x, y + yOffset * 3, width, height);
    }

    @Override
    public void render(Graphics graphics) {
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonPlaying.draw(graphics);
        buttonEditing.draw(graphics);
        buttonSettings.draw(graphics);
        buttonQuit.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonPlaying.getBounds().contains(x, y)) {
            getGame().getPlaying().resetGame();
            SetGameSate(PLAYING);
        } else if(buttonEditing.getBounds().contains(x, y)) {
            SetGameSate(EDITING);
        } else if(buttonSettings.getBounds().contains(x, y)) {
            SetGameSate(SETTINGS);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonPlaying.setHover(false);
        buttonEditing.setHover(false);
        buttonSettings.setHover(false);
        buttonQuit.setHover(false);

        if(buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setHover(true);
        } else if(buttonEditing.getBounds().contains(x, y)) {
            buttonEditing.setHover(true);
        } else if(buttonSettings.getBounds().contains(x, y)) {
            buttonSettings.setHover(true);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setHover(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setPressed(true);
        } else if(buttonEditing.getBounds().contains(x, y)) {
            buttonEditing.setPressed(true);
        } else if(buttonSettings.getBounds().contains(x, y)) {
            buttonSettings.setPressed(true);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setPressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        reset();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void reset() {
        buttonPlaying.resetBooleans();
        buttonEditing.resetBooleans();
        buttonSettings.resetBooleans();
        buttonQuit.resetBooleans();
    }
}
