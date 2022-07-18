package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage[] sprite;
    private final int id;
    private final String name;

    public Tile(BufferedImage sprite, int id, String name) {
        this.sprite = new BufferedImage[1];
        this.sprite[0] = sprite;
        this.id = id;
        this.name = name;
    }

    public Tile(BufferedImage[] sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public BufferedImage getSprite() {
        return sprite[0];
    }

    public BufferedImage getSprite(int animationIndex) {
        return sprite[animationIndex];
    }

    public boolean isAnimation() {
        return sprite.length > 1;
     }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
