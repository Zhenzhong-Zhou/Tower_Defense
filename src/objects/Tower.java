package objects;

import static helperMethods.Constants.Towers.*;

public class Tower {
    private int x, y, id, towerType, damage, cdTick;
    private float range, CD;
    private int tier;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier = 1;
        setDefaultDamage();
        setDefaultRange();
        setDefaultCD();
    }

    public void update() {
        cdTick++;
    }

    public void upgrade() {
        this.tier++;

        switch(towerType) {
            case CANNON:
                damage += 5;
                range += 20;
                CD -= 15;
                break;
            case ARCHER:
                damage += 2;
                range += 20;
                CD -= 5;
                break;
            case WIZARD:
                range += 20;
                CD -= 10;
                break;
        }
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

    public int getTier() {
        return tier;
    }
}
