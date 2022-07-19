package managers;

import enemies.Enemy;
import helperMethods.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Projectiles.*;
import static helperMethods.Constants.Towers.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private BufferedImage[] projectile_images, explosion_images;
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
        importExplosion(atlas);
    }

    private void importExplosion(BufferedImage atlas) {
        explosion_images = new BufferedImage[7];

        for(int i =0; i < explosion_images.length; i++) {
            explosion_images[i] = atlas.getSubimage(i*32, 32*2, 32, 23);
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

        float rotation = 0;
        if(projectileType == ARROW) {
            float arcValue = (float) Math.atan(yDistance / (float) xDistance);
            rotation = (float) Math.toDegrees(arcValue);
            if(xDistance < 0) {
                rotation += 180;
            }
        }
        projectiles.add(new Projectile(tower.getX()+16, tower.getY()+16, xSpeed, ySpeed, tower.getDamage(), rotation, project_id++, projectileType));
    }

    public void update() {
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                projectile.move();
                if(isProjHittingEnemy(projectile)) {
                    projectile.setActive(false);
                    if(projectile.getProjectileType() == BOMB) {
                       explosions.add(new Explosion(projectile.getPosition()));
                       explodeOnEnemies(projectile);
                    }
                } else {
                    // we do nothing
                }
            }
        }

        for(Explosion explosion : explosions) {
            if(explosion.explosionIndex < explosion_images.length) {
                explosion.update();
            }
        }
    }

    private void explodeOnEnemies(Projectile projectile) {
        for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
            if(enemy.isAlive()) {
                float radius = 40.0f;

                float xDistance = Math.abs(projectile.getPosition().x -enemy.getX());
                float yDistance = Math.abs(projectile.getPosition().y -enemy.getY());
                float realDistance = (float) Math.hypot(xDistance, yDistance);

                if(realDistance <= radius) {
                    enemy.hurt(projectile.getDamage());
                }
            }
        }
    }

    private boolean isProjHittingEnemy(Projectile projectile) {
        for(Enemy enemy : playing.getEnemyManager().getEnemies()) {
            if(enemy.isAlive()) {
                if(enemy.getBounds().contains(projectile.getPosition())) {
                    enemy.hurt(projectile.getDamage());
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        for(Projectile projectile : projectiles) {
            if(projectile.isActive()) {
                if(projectile.getProjectileType() == ARROW) {
                    graphics2D.translate(projectile.getPosition().x, projectile.getPosition().y);
                    graphics2D.rotate(Math.toRadians(projectile.getRotation()));
                    graphics2D.drawImage(projectile_images[projectile.getProjectileType()], -16,-16, null);
                    graphics2D.rotate(-Math.toRadians(projectile.getRotation()));
                    graphics2D.translate(-projectile.getPosition().x, -projectile.getPosition().y);
                } else {
                    graphics2D.drawImage(projectile_images[projectile.getProjectileType()],
                            (int) projectile.getPosition().x-16, (int) projectile.getPosition().y-16, null);
                }
            }
        }
        drawExplosions(graphics2D);
    }

    private void drawExplosions(Graphics2D graphics2D) {
       for(Explosion explosion : explosions) {
           if(explosion.getExplosionIndex() < explosion_images.length) {
               graphics2D.drawImage(explosion_images[explosion.getExplosionIndex()],
                       (int) explosion.getPosition().x-16, (int) explosion.getPosition().y-16, null);
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

    public class Explosion{
        private  Point2D.Float position;
        private int explosionTick=0, explosionIndex =0;
        public Explosion(Point2D.Float position) {
            this.position = position;
        }

        public void update() {
            explosionTick++;
            if(explosionTick >= 12) {
                explosionTick = 0;
                explosionIndex++;
            }
        }

        public int getExplosionIndex() {
            return explosionIndex;
        }

        public Point2D.Float getPosition() {
            return position;
        }
    }
}
