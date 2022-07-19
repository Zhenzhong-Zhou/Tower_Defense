package objects;

import static helperMethods.Constants.Towers.*;

public class Tower {
    private int x, y, id, towerType, damage, cdTick;
    private float range, CD;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCD();
    }

    public void update() {
        cdTick++;
    }

    public boolean isCD() {
        return cdTick >= CD;
    }

    public void restCD() {
        cdTick = 0;
    }

    private void setDefaultCD() {
        CD = GetDefaultCD(towerType);
    }

    private void setDefaultRange() {
        range = GetDefaultRange(towerType);
    }

    private void setDefaultDamage() {
        damage = GetStartDamage(towerType);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getCD() {
        return CD;
    }
}
