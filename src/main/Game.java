package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {
    private GameScreen gameScreen;
    private BufferedImage image;

    public Game() {
        importImage();

        setSize(640, 640);
        setResizable(false);
        setTitle("Tower Defense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gameScreen = new GameScreen(image);
        add(gameScreen);
        setVisible(true);
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

    public static void main(String[] args) {
        new Game();
    }
}
