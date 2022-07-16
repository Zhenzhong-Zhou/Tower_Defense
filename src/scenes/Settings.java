package scenes;

import gui.MyButton;
import main.Game;

import java.awt.*;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods {
    private MyButton buttonMenu;

    public Settings(Game game) {
        super(game);

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 0, 0, 100, 30);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.fillRect(0, 0, 640, 640);
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
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
