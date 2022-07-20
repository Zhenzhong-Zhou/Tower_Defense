package objects;

import java.awt.geom.Point2D;

public class Projectile {
    private final int id;
    private final int projectileType;
    private int damage;
    private float xSpeed;
    private float ySpeed;
    private float rotation;
    private Point2D.Float position;
    private boolean active = true;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int damage, float rotation, int id, int projectileType) {
        position = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
    }

    public void reuse(int x, int y, float xSpeed, float ySpeed, int damage, float rotation) {
        position = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.damage = damage;
        this.rotation = rotation;
        active = true;
    }

    public void move() {
        position.x += xSpeed;
        position.y += ySpeed;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDamage() {
        return damage;
    }

    public float getRotation() {
        return rotation;
    }
}
