package enemies;

import java.awt.*;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Enemies.GetStartHealth;

public abstract class Enemy {
    protected final Rectangle bounds;
    protected final int ID;
    protected final int enemyType;
    protected float x, y;
    protected int health;
    protected int maxHealth;
    protected int lastDirection;
    protected boolean alive = true;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;

    public Enemy(float x, float y, int ID, int enemyType) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDirection = - 1;
        setStartHealth();
    }

    public void hurt(int damage) {
        this.health -= damage;
        if(health <= 0) {
            alive = false;
        }
    }

    public void kill() {
        // it is for killing enemies, when it reached the end
        alive = false;
        health = 0;
    }

    public void slow() {
        slowTick = 0;
    }

    public void move(float speed, int direction) {
        lastDirection = direction;
        if(slowTick < slowTickLimit) {
            slowTick++;
            speed*= 0.5f;
        }
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
        updateHitBox();
    }

    private void updateHitBox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
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

    public boolean isAlive() {
        return alive;
    }

    public boolean isSlowed() {
        return slowTick < slowTickLimit;
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
