package main;

import helperMethods.LoadSave;
import managers.TileManager;
import scenes.Editing;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

import javax.swing.*;

public class Game extends JFrame implements Runnable {
    private GameScreen gameScreen;
    private TileManager tileManager;
    // Classes
    private Render render;
    private Menu menu;
    private Playing playing;
    private Editing editing;
    private Settings settings;

    public Game() {
        initClasses();
        createDefaultLevel();
        add(gameScreen);
        pack();

        setResizable(false);
        setTitle("Tower Defense");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        editing = new Editing(this);
        settings = new Settings(this);
    }

    private void createDefaultLevel() {
        int[] array = new int[400];
        LoadSave.CreateLevel("default_level", array);
    }

    private void start() {
        Thread gameThread = new Thread(this) {
        };
        gameThread.start();
    }

    private void updateGame() {
        switch(GameStates.gameState) {
            case PLAYING:
                playing.update();
                break;
            case EDITING:
                break;
            case SETTINGS:
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        double FPS_SET = 120.0;
        double timePerFrame = 1000000000.0 / FPS_SET;
        double UPS_SET = 60.0;
        double timePerUpdate = 1000000000.0 / UPS_SET;

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

    // Getters
    public TileManager getTileManager() {
        return tileManager;
    }

    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Editing getEditor() {
        return editing;
    }

    public Settings getSettings() {
        return settings;
    }
}
