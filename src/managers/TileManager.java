package managers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {
    public Tile GRASS, WATER, ROAD, BL_WATER_CORNER, TL_WATER_CORNER;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();
    }

    private void createTiles() {
        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, "Grass"));
        tiles.add(WATER = new Tile(getSprite(0, 0), id++, "Water"));
        tiles.add(ROAD = new Tile(getSprite(8, 0), id++, "Road"));
        tiles.add(BL_WATER_CORNER = new Tile(ImageFix.buildImage(getImages(0, 0, 5, 0)), id++, "BL_Water_Corner"));
        tiles.add(TL_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 5, 0), 1, 90), id++, "TLL_Water_Corner"));
    }

    private BufferedImage[] getImages(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[] {
                getSprite(firstX, firstY),
                getSprite(secondX, secondY)
        };
    }

    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTitle(int id) {
        return tiles.get(id);
    }

    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int x, int y) {
        return atlas.getSubimage(x * 32, y * 32, 32, 32);
    }
}
