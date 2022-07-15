package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JPanel {
    private Dimension size;
    private BufferedImage image;
    private Random random;

    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public GameScreen(BufferedImage image) {
        setPanelSize();
        this.image = image;
        loadSprites();
        random = new Random();
    }

    private void setPanelSize() {
        size = new Dimension(640, 640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void loadSprites() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                sprites.add(image.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 20; x++) {
                graphics.drawImage(sprites.get(getRandInt()), x * 32, y * 32, null);
            }
        }
    }

    private int getRandInt() {
        return random.nextInt(100);
    }

    private Color getRanColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return new Color(r, g, b);
    }
}
