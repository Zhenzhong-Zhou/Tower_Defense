package managers;

import helperMethods.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;

import static helperMethods.Constants.Towers.ARCHER;
import static helperMethods.Constants.Towers.CANNON;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImages;
    private Tower tower;

    public TowerManager(Playing playing) {
        this.playing = playing;

        loadTowerImages();
        initTowers();
    }

    private void initTowers() {
        tower = new Tower(3*32, 6*32, 0, ARCHER);
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];
        for(int i = 0; i<3; i++) {
            towerImages[i] = atlas.getSubimage((4 + i)*32, 32, 32, 32);
        }
    }

    public void update() {

    }

    public void draw(Graphics graphics) {
        graphics.drawImage(towerImages[ARCHER], tower.getX(), tower.getY(), null);
    }
}
