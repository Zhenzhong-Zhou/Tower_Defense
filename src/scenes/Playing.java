package scenes;

import enemies.Enemy;
import gui.ActionBar;
import helperMethods.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helperMethods.Constants.Enemies.GetReward;
import static helperMethods.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods {
    private final ActionBar actionBar;
    private final EnemyManager enemyManager;
    private final TowerManager towerManager;
    private final ProjectileManager projectileManager;
    private final WaveManager waveManager;
    private int[][] level;
    private int mouseX, mouseY;
    private PathPoint start, end;
    private Tower selectedTower;
    private int goldTick;
    private boolean gamePaused;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        actionBar = new ActionBar(0, 640, 640, 160, this);
        enemyManager = new EnemyManager(this, start, end);
        towerManager = new TowerManager(this);
        projectileManager = new ProjectileManager(this);
        waveManager = new WaveManager(this);
    }

    private void loadDefaultLevel() {
        level = LoadSave.GetLevelData("default_level");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("default_level");
        assert points != null;
        start = points.get(0);
        end = points.get(1);
    }

    public void update() {
        if(! gamePaused) {
            updateTick();
            waveManager.update();
            // Gold tick
            goldTick++;
            if(goldTick % (60 * 3) == 0) {
                actionBar.addGold(1);
            }
            if(isAllEnemiesDead()) {
                if(isThereMoreWaves()) {
                    waveManager.startWaveTimer();
                    // Check timer
                    if(isWaveTimerOver()) {
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }
            }
            if(isTimeForNewEnemy()) {
                spawnEnemy();
            }
            enemyManager.update();
            towerManager.update();
            projectileManager.update();
        }
    }

    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if(waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }
        for(Enemy enemy : enemyManager.getEnemies()) {
            if(enemy.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        if(waveManager.isTimeForNewEnemy()) {
            return waveManager.isThereMoreEnemiesInWave();
        }
        return false;
    }

    @Override
    public void render(Graphics graphics) {
        drawLevel(graphics);
        actionBar.draw(graphics);
        enemyManager.draw(graphics);
        towerManager.draw(graphics);
        projectileManager.draw(graphics);

        drawSelectedTower(graphics);
        drawHighlight(graphics);
    }

    private void drawLevel(Graphics graphics) {
        for(int y = 0; y < level.length; y++) {
            for(int x = 0; x < level[y].length; x++) {
                int id = level[y][x];
                if(isAnimation(id)) {
                    graphics.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
                } else {
                    graphics.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    private void drawHighlight(Graphics graphics) {
        graphics.setColor(Color.PINK);
        graphics.drawRect(mouseX, mouseY, 32, 32);
    }

    private void drawSelectedTower(Graphics graphics) {
        if(selectedTower != null) {
            graphics.drawImage(towerManager.getTowerImages()[selectedTower.getTowerType()], mouseX, mouseY, null);
        }
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if(xCord < 0 || xCord > 19) {
            return 0;
        }
        if(yCord < 0 || yCord > 19) {
            return 0;
        }

        int id = level[y / 32][x / 32];
        return getGame().getTileManager().getTile(id).getTileType();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640 && ! gamePaused) {
            actionBar.mouseClicked(x, y);
        } else if(gamePaused && actionBar.getButtonPause().getBounds().contains(x, y)
                || actionBar.getButtonMenu().getBounds().contains(x, y)) {
            actionBar.mouseClicked(x, y);
        } else {
            if(selectedTower != null) {
                if(isTileGrass(mouseX, mouseY)) {
                    if(getTowerAt(mouseX, mouseY) == null) {
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        removeGold(selectedTower.getTowerType());
                        selectedTower = null;
                    }
                }
            } else {
                // get tower if exists on (x, y)
                Tower tower = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(tower);
            }
        }
    }

    private boolean isTileGrass(int x, int y) {
        int id = level[y / 32][x / 32];
        int tileType = getGame().getTileManager().getTile(id).getTileType();
        return tileType == GRASS_TILE;
    }

    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    private Tower getTowerAt(int x, int y) {
        return towerManager.getTowerAt(x, y);
    }

    public void shootEnemy(Tower tower, Enemy enemy) {
        projectileManager.newProjectile(tower, enemy);
    }

    private void removeGold(int towerType) {
        actionBar.buyTower(towerType);
    }

    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640 && ! gamePaused) {
            actionBar.mouseMoved(x, y);
        } else if(gamePaused && actionBar.getButtonPause().getBounds().contains(x, y)
                || actionBar.getButtonMenu().getBounds().contains(x, y)) {
            actionBar.mouseMoved(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640 && ! gamePaused) {
            actionBar.mousePressed(x, y);
        } else if(gamePaused && actionBar.getButtonPause().getBounds().contains(x, y)
                || actionBar.getButtonMenu().getBounds().contains(x, y)) {
            actionBar.mouseMoved(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if(y >= 640 && ! gamePaused) {
            actionBar.mouseReleased();
        } else if(gamePaused && actionBar.getButtonPause().getBounds().contains(x, y)
                || actionBar.getButtonMenu().getBounds().contains(x, y)) {
            actionBar.mouseMoved(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void setLevel(int[][] level) {
        this.level = level;
    }

    public void rewardPlayer(int enemyType) {
        actionBar.addGold(GetReward(enemyType));
    }

    public TowerManager getTowerManager() {
        return towerManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused = gamePaused;
    }

    public void removeOneLife() {
        actionBar.removeOneLife();
    }
}
