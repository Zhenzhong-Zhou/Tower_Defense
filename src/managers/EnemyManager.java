package managers;

import enemies.*;
import helperMethods.LoadSave;
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
    private final float speed = 0.5f;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        enemyImages = new BufferedImage[4];
        addEnemy(0, 19 * 32, ORC);
        addEnemy(2 * 32, 19 * 32, BAT);
        addEnemy(5 * 32, 8 * 32, KNIGHT);
        addEnemy(8 * 32, 14 * 32, WOLF);
        loadEnemyImages();
    }

    private void loadEnemyImages() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for(int i = 0; i < 4; i++) {
            enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
        }
    }

    public void update() {
        for(Enemy enemy : enemies) {
            updateEnemyMove(enemy);
        }
    }

    public void updateEnemyMove(Enemy enemy) {
        if(enemy.getLastDirection() == -1) {
            setNewDirectionAndMove(enemy);
        }

        int newX = (int) (enemy.getX() + getSpeedAndWidth(enemy.getLastDirection()));
        int newY = (int) (enemy.getY() + getSpeedAndHeight(enemy.getLastDirection()));

        if(getTileType(newX, newY) == ROAD_TILE) {
            // keep moving in the same direction
            enemy.move(speed, enemy.getLastDirection());
        } else if(isAtEnd(enemy)) {
            // reached the end
        } else {
            // find new direction
            setNewDirectionAndMove(enemy);
        }
    }

    public void addEnemy(int x, int y, int enemyType) {
        switch(enemyType) {
            case ORC:
                enemies.add(new Orc(x, y, 0));
                break;
            case BAT:
                enemies.add(new Bat(x, y, 0));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y, 0));
                break;
            case WOLF:
                enemies.add(new Wolf(x, y, 0));
                break;
        }
    }

    public void draw(Graphics graphics) {
        for(Enemy enemy : enemies) {
            drawEnemy(enemy, graphics);
        }
    }

    private void drawEnemy(Enemy enemy, Graphics graphics) {
        graphics.drawImage(enemyImages[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(), null);
    }

    private float getSpeedAndHeight(int direction) {
        if(direction == UP) {
            return - speed;
        } else if(direction == DOWN) {
            return speed + 32;
        } else {
            return 0;
        }
    }

    private float getSpeedAndWidth(int direction) {
        if(direction == LEFT) {
            return - speed;
        } else if(direction == RIGHT) {
            return speed + 32;
        } else {
            return 0;
        }
    }

    private boolean isAtEnd(Enemy enemy) {
        return false;
    }

    private void setNewDirectionAndMove(Enemy enemy) {
        int direction = enemy.getLastDirection();

        // move into the current till 100%
        int xCord = (int) (enemy.getX() / 32);
        int yCord = (int) (enemy.getY() / 32);

        fixEnemyOffsetTile(enemy, direction, xCord, yCord);

        if(direction == LEFT || direction == RIGHT) {
            int newY = (int) (enemy.getY() + getSpeedAndHeight(UP));
            if(getTileType((int) enemy.getX(), newY) == ROAD_TILE) {
                enemy.move(speed, UP);
            } else {
                enemy.move(speed, DOWN);
            }
        } else {
            int newX = (int) (enemy.getX() + getSpeedAndWidth(RIGHT));
            if(getTileType(newX, (int) enemy.getY()) == ROAD_TILE) {
                enemy.move(speed, RIGHT);
            } else {
                enemy.move(speed, LEFT);
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
}
