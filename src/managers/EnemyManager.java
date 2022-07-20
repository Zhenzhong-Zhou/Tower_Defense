package managers;

import enemies.*;
import helperMethods.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.*;
import static helperMethods.Constants.Tiles.ROAD_TILE;

public class EnemyManager {
    private final Playing playing;
    private final BufferedImage[] enemyImages;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final PathPoint start;
    private final PathPoint end;
    private final int HpBarWidth = 20;
    private BufferedImage slowEffect;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        this.start = start;
        this.end = end;
        loadEffectImage();
        loadEnemyImages();
    }

    private void loadEffectImage() {
        slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for(int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }
    }

    public void update() {
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
                updateEnemyMove(enemy);
            }
        }
    }


    public void updateEnemyMove(Enemy enemy) {
        if(enemy.getLastDirection() == - 1) {
            setNewDirectionAndMove(enemy);
        }

        int newX = (int) (enemy.getX() + getSpeedAndWidth(enemy.getLastDirection(), enemy.getEnemyType()));
        int newY = (int) (enemy.getY() + getSpeedAndHeight(enemy.getLastDirection(), enemy.getEnemyType()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            // keep moving in the same direction
            enemy.move(GetSpeed(enemy.getEnemyType()), enemy.getLastDirection());
        } else if(isAtEnd(enemy)) {
            // reached the end
            enemy.kill();
            playing.removeOneLife();
        } else {
            // find new direction
            setNewDirectionAndMove(enemy);
        }
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void addEnemy(int enemyType) {
        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;
        switch(enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0, this));
                break;
            case BAT:
                enemies.add(new Bat(x, y, 0, this));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y, 0, this));
                break;
            case WOLF:
                enemies.add(new Wolf(x, y, 0, this));
                break;
        }
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
                drawEnemy(enemy, graphics);
                drawHealthBar(enemy, graphics);
                drawEffect(enemy, graphics);
            }
        }
    }

    private void drawEffect(Enemy enemy, Graphics graphics) {
        if(enemy.isSlowed()) {
            graphics.drawImage(slowEffect, (int) enemy.getX(), (int) enemy.getY(), null);
        }
    }

    private void drawHealthBar(Enemy enemy, Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect((int) enemy.getX() + 16 - (getNewBarWidth(enemy) / 2), (int) enemy.getY() - 10,
                getNewBarWidth(enemy), 3);
    }

    private int getNewBarWidth(Enemy enemy) {
        return (int) (HpBarWidth * enemy.getHealthBarFloat());
    }

    private void drawEnemy(Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(), null);
    }

    private float getSpeedAndHeight(int direction, int enemyType) {
        if(direction == UP) {
            return - GetSpeed(enemyType);
        } else if(direction == DOWN) {
            return GetSpeed(enemyType) + 32;
        } else {
            return 0;
        }
    }

    private float getSpeedAndWidth(int direction, int enemyType) {
        if(direction == LEFT) {
            return - GetSpeed(enemyType);
        } else if(direction == RIGHT) {
            return GetSpeed(enemyType) + 32;
        } else {
            return 0;
        }
    }

    private boolean isAtEnd(Enemy enemy) {
        if(enemy.getX() == end.getxCord() * 32) {
            return enemy.getY() == end.getyCord() * 32;
        }
        return false;
    }

    private void setNewDirectionAndMove(Enemy enemy) {
        int direction = enemy.getLastDirection();

        // move into the current till 100%
        int xCord = (int) (enemy.getX() / 32);
        int yCord = (int) (enemy.getY() / 32);

        fixEnemyOffsetTile(enemy, direction, xCord, yCord);

        if(isAtEnd(enemy)) {
            return;
        }

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int) (enemy.getY() + getSpeedAndHeight(UP, enemy.getEnemyType()));
            if(getTileType((int) enemy.getX(), newY) == ROAD_TILE) {
                enemy.move(GetSpeed(enemy.getEnemyType()), UP);
            } else {
                enemy.move(GetSpeed(enemy.getEnemyType()), DOWN);
            }
        } else {
            int newX = (int) (enemy.getX() + getSpeedAndWidth(RIGHT, enemy.getEnemyType()));
            if(getTileType(newX, (int) enemy.getY()) == ROAD_TILE) {
                enemy.move(GetSpeed(enemy.getEnemyType()), RIGHT);
            } else {
                enemy.move(GetSpeed(enemy.getEnemyType()), LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(Enemy enemy, int direction, int xCord, int yCord) {
        switch(direction) {
            case RIGHT:
                if(xCord < 19) {
                    xCord++;
                }
                break;
            case DOWN:
                if(yCord < 19) {
                    yCord++;
                }
                break;
        }
        enemy.setPosition(xCord * 32, yCord * 32);
    }

    private int getTileType(int newX, int newY) {
        return playing.getTileType(newX, newY);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;
        for(Enemy enemy : enemies) {
            if(enemy.isAlive()) {
                size++;
            }
        }
        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }
}
