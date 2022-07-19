package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Projectiles.*;
import static helperMethods.Constants.Towers.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projectile_images;
    private int project_id = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;

        importImages();
    }

    private void importImages() {
        // x 7 8 9 y 1
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        projectile_images = new BufferedImage[3];

        for(int i=0; i< projectile_images.length; i++) {
            projectile_images[i] = atlas.getSubimage((7 +i)*32, 32, 32, 32);
        }
    }

    public void newProjectile(Tower tower, Enemy enemy) {
        int projectileType = getProjectileType(tower);

        int xDistance = (int) (tower.getX() - enemy.getX());
        int yDistance = (int) (tower.getY() - enemy.getY());
        int totalDistance = Math.abs(xDistance)  + Math.abs(yDistance);

        float xPercentage = (float) Math.abs(xDistance) / totalDistance;

        float xSpeed = xPercentage * GetSpeed(projectileType);
        float ySpeed = GetSpeed(projectileType) - xSpeed;

        if(tower.getX() > enemy.getX()) {
            xSpeed *= -1;
        }
        if(tower.getY() > enemy.getY()) {
            ySpeed *= -1;
        }

        float arcValue = (float) Math.atan(yDistance / (float) xDistance);
        float rotation = (float) Math.toDegrees(arcValue);
        if(xDistance < 0) {
            rotation += 180;
        }
        projectiles.add(new Projectile(tower.getX()+16, tower.getY()+16, xSpeed, ySpeed, tower.getDamage(), rotation, project_id++, projectileType));
    }

    public void update() {
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                projectile.move();
                if(isProjHittingEnemy(projectile)) {
                    projectile.setActive(false);
                } else {
                    // we do nothing
                }
            }
        }
    }

    private boolean isProjHittingEnemy(Projectile projectile) {
        for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
            if(enemy.getBounds().contains(projectile.getPosition())) {
                enemy.hurt(projectile.getDamage());
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                graphics2D.translate(projectile.getPosition().x, projectile.getPosition().y);
                graphics2D.rotate(Math.toRadians(projectile.getRotation()));
                graphics2D.drawImage(projectile_images[projectile.getProjectileType()], -16,-16, null);
                graphics2D.rotate(-Math.toRadians(projectile.getRotation()));
                graphics2D.translate(-projectile.getPosition().x, -projectile.getPosition().y);
            }
        }
    }

    private int getProjectileType(Tower tower) {
        switch(tower.getTowerType()) {
            case CANNON:
                return BOMB;
            case ARCHER:
                return ARROW;
            case WIZARD:
                return CHAINS;
        }
        return 0;
    }
}
