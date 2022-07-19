package enemies;

import java.awt.*;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.GetStartHealth;

public abstract class Enemy {
    private final Rectangle bounds;
    private final int ID;
    private final int enemyType;
    private float x, y;
    private int health;
    private int maxHealth;
    private int lastDirection;

    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDirection = - 1;
        setStartHealth();
    }

    public void move(float speed, int direction) {
        lastDirection = direction;
        switch(direction) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public float getHealthBarFloat() {
        return (float) health / maxHealth;
    }

    public int getID() {
        return ID;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDirection() {
        return lastDirection;
    }

    public void setPosition(int x, int y) {
        // Don't use this one for move, this is for position fix
        this.x = x;
        this.y = y;
    }

    private void setStartHealth() {
        health = GetStartHealth(enemyType);
        maxHealth = health;
    }
}
