package scenes;

import main.Game;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods {
    public Playing(Game game) {
        super(game);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.cyan);
        graphics.fillRect(0, 0, 640, 640);
    }
}
