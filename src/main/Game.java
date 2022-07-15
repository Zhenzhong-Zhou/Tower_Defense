package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame implements Runnable {
    private GameScreen gameScreen;
    private BufferedImage image;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    public Game() {
        importImage();

        gameScreen = new GameScreen(image);
        add(gameScreen);
        pack();

        setResizable(false);
        setTitle("Tower Defense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

    private void start() {
        gameThread = new Thread(this) {};
        gameThread.start();;
    }

    private void updateGame() {}

    public static void main(String[] args) {
       Game game = new Game();
       game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;;
        double timePerUpdate = 1000000000.0 / UPS_SET;;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();
        long now;

        int frames = 0;
        int updates = 0;

        while(true) {
            now = System.nanoTime();
            // Render
            if(now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Update
            if(now - lastUpdate >= timePerUpdate) {
                updateGame();
                lastUpdate = now;
                updates++;
            }

            // Check FPS and UPS
            if(System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
}
