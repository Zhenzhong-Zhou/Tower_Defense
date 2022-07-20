package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Utilities.GetHypoDistance;

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

    public void removeTower(Tower displayedTower) {
        for(int i = 0; i < towers.size(); i++) {
            if(towers.get(i).getId() == displayedTower.getId()) {
                towers.remove(i);
            }
        }
    }

    public void upgradeTower(Tower displayedTower) {
        for(Tower tower : towers) {
            if(tower.getId() == displayedTower.getId()) {
                tower.upgrade();
            }
        }
    }

    public void update() {
        for(Tower tower : towers) {
            tower.update();
            attackEnemyIfClose(tower);
        }
    }

    private void attackEnemyIfClose(Tower tower) {
        for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
            if(enemy.isAlive()) {
                if(isEnemyInRange(tower, enemy)) {
                    if(tower.isCD()) {
                        // tower shoot enemy
                        playing.shootEnemy(tower, enemy);
                        tower.restCD();
                    }
                } else {
                    // we do nothing
                }
            }
        }
    }

    private boolean isEnemyInRange(Tower tower, Enemy enemy) {
        int range = GetHypoDistance(tower.getX(), tower.getY(), enemy.getX(), enemy.getY());
        return range < tower.getRange();
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
