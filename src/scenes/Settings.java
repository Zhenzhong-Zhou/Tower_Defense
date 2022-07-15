package scenes;

import main.Game;

import java.awt.*;

public class Settings extends GameScene implements SceneMethods {
    public Settings(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.red);
        graphics.fillRect(0, 0, 640, 640);
    }

    @Override
    public void mouseClicked(int x, int y) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }

    @Override
    public void mousePressed(int x, int y) {

    }

    @Override
    public void mouseReleased(int x, int y) {

    }
}
