package scenes;

import gui.MyButton;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {
    private BufferedImage image;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    private MyButton buttonPlaying, buttonSettings, buttonQuit;

    public Menu(Game game) {
        super(game);

        importImage();
        loadSprites();
        initButtons();
    }

    private void initButtons() {
        int width = 150;
        int height = width / 3;
        int x = 640 / 2 - width / 2;
        int y = 150;
        int yOffset = 100;

        buttonPlaying = new MyButton("Play", x, y, width, height);
        buttonSettings = new MyButton("Settings", x, y + yOffset, width, height);
        buttonQuit = new MyButton("Quit", x, y + yOffset * 2, width, height);
    }

    @Override
    public void render(Graphics graphics) {
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonPlaying.draw(graphics);
        buttonSettings.draw(graphics);
        buttonQuit.draw(graphics);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonPlaying.getBounds().contains(x, y)) {
            SetGameSate(PLAYING);
        } else if(buttonSettings.getBounds().contains(x, y)) {
            SetGameSate(SETTINGS);
        } else if(buttonQuit.getBounds().contains(x, y)) {
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        buttonPlaying.setHover(false);
        buttonSettings.setHover(false);
        buttonQuit.setHover(false);

        if(buttonPlaying.getBounds().contains(x, y)) {
            buttonPlaying.setHover(true);
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
        buttonSettings.resetBooleans();
        buttonQuit.resetBooleans();
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream("/spriteatlas.png");
        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void loadSprites() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }
}
