package scenes;

import gui.MyButton;
import main.Game;

import java.awt.*;

import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods {
    private MyButton buttonReplay, buttonMenu, buttonQuit;
    public GameOver(Game game) {
        super(game);

        initButtons();
    }

    private void initButtons() {
        int width = 150;
        int height = width / 3;
        int x = 640 / 2 - width / 2;
        int y = 300;
        int yOffset = 100;

        buttonMenu = new MyButton("Menu", x, y, width, height);
        buttonReplay = new MyButton("Replay", x, y + yOffset, width, height);
        buttonQuit = new MyButton("Quit", x, y + yOffset * 2, width, height);
    }

    @Override
    public void render(Graphics graphics) {
        // Game Over Text
        graphics.setFont(new Font("LucidaSans", Font.BOLD, 50));
        graphics.setColor(Color.RED);
        graphics.drawString("GAME OVER!", 160, 180);

        // Buttons
        graphics.setFont(new Font("LucidaSans", Font.BOLD, 20));
        buttonMenu.draw(graphics);
        buttonReplay.draw(graphics);
        buttonQuit.draw(graphics);
    }

    private void replayGame() {
        // Reset everything
        resetAll();

        // Change state to playing
        SetGameSate(PLAYING);
    }

    private void resetAll() {
        getGame().getPlaying().resetGame();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            SetGameSate(MENU);
            resetAll();
        } else if(buttonReplay.getBounds().contains(x, y)) {
            replayGame();
        } else if(buttonQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);
        buttonReplay.setHover(false);
        buttonQuit.setHover(false);

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        } else if(buttonReplay.getBounds().contains(x, y)) {
            buttonReplay.setHover(true);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setHover(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        } else if(buttonReplay.getBounds().contains(x, y)) {
            buttonReplay.setPressed(true);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            buttonQuit.setPressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
        buttonReplay.resetBooleans();
        buttonQuit.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
