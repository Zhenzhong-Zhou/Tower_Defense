package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImages;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(3, 9);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImages[0] = LoadSave.getSpriteAtlas().getSubimage(0,32,32,32);
        enemyImages[1] = LoadSave.getSpriteAtlas().getSubimage(32,32,32,32);
        enemyImages[2] = LoadSave.getSpriteAtlas().getSubimage(2*32,32,32,32);
        enemyImages[3] = LoadSave.getSpriteAtlas().getSubimage(3*32,32,32,32);
    }

    public void update() {
        for(Enemy enemy : enemies) {
            enemy.move(0.5f, 0);
        }
    }

    public void addEnemy(int x, int y) {
        enemies.add(new Enemy(x, y, 0, 0));
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy :  enemies) {
            drawEnemy(enemy, graphics);
        }
    }

    private void drawEnemy(Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[0], (int) enemy.getX(), (int) enemy.getY(), null);
    }
}
