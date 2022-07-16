package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage sprite;
    private final int id;
    private final String name;

    public Tile(BufferedImage sprite, int id, String name) {
        this.sprite = sprite;
        this.id = id;
        this.name = name;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
