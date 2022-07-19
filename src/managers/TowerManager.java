package managers;

import helperMethods.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TowerManager {
    private final Playing playing;
    private final ArrayList<Tower> towers = new ArrayList<>();
    private BufferedImage[] towerImages;
    private int towerAmount = 0;

    public TowerManager(Playing playing) {
        this.playing = playing;

        loadTowerImages();
    }

    private void loadTowerImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImages = new BufferedImage[3];
        for(int i = 0; i < 3; i++) {
            towerImages[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
        }
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    public void update() {

    }

    public void draw(Graphics graphics) {
        for(Tower tower : towers) {
            graphics.drawImage(towerImages[tower.getTowerType()], tower.getX(), tower.getY(), null);
        }
    }

    public BufferedImage[] getTowerImages() {
        return towerImages;
    }

    public Tower getTowerAt(int x, int y) {
        for(Tower tower : towers) {
            if(tower.getX() == x) {
                if(tower.getY() == y) {
                    return tower;
                }
            }
        }
        return null;
    }
}
