package managers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helperMethods.Constants.Tiles.*;

public class TileManager {
    public Tile GRASS, WATER, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER,
            T_WATER, R_WATER, B_WATER, L_WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R,
            TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadStraights = new ArrayList<>();
    public ArrayList<Tile> roadCorners = new ArrayList<>();
    public ArrayList<Tile> waterCorners = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    public TileManager() {
        loadAtlas();
        createTiles();
    }

    private void createTiles() {
        int id = 0;

        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE));
        tiles.add(WATER = new Tile(getAniSprites(0, 0), id++, WATER_TILE));

        roadStraights.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE));
        roadStraights.add(ROAD_TB = new Tile(ImageFix.getRotateImage(getSprite(8, 0), 90), id++, ROAD_TILE));

        roadCorners.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
        roadCorners.add(ROAD_L_TO_B = new Tile(ImageFix.getRotateImage(getSprite(7, 0),90), id++, ROAD_TILE));
        roadCorners.add(ROAD_L_TO_T = new Tile(ImageFix.getRotateImage(getSprite(7, 0),180), id++, ROAD_TILE));
        roadCorners.add(ROAD_T_TO_R = new Tile(ImageFix.getRotateImage(getSprite(7, 0),270), id++, ROAD_TILE));

        waterCorners.add(BL_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(5, 0), 1, 0), id++, WATER_TILE));
        waterCorners.add(TL_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(5, 0), 1, 90), id++, WATER_TILE));
        waterCorners.add(BR_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(5, 0), 1, 180), id++, WATER_TILE));
        waterCorners.add(TR_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(5, 0), 1, 270), id++, WATER_TILE));

        beaches.add(T_WATER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(6, 0), 1, 0), id++, WATER_TILE));
        beaches.add(R_WATER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(6, 0), 1, 90), id++, WATER_TILE));
        beaches.add(B_WATER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(6, 0), 1, 180), id++, WATER_TILE));
        beaches.add(L_WATER = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(6, 0), 1, 270), id++, WATER_TILE));

        islands.add(TL_ISLE = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(4, 0), 1, 0), id++, WATER_TILE));
        islands.add(TR_ISLE = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(4, 0), 1, 90), id++, WATER_TILE));
        islands.add(BR_ISLE = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(4, 0), 1, 180), id++, WATER_TILE));
        islands.add(BL_ISLE = new Tile(ImageFix.getBuildRotateImage(getAniSprites(0, 0), getSprite(4, 0), 1, 270), id++, WATER_TILE));

        tiles.addAll(roadStraights);
        tiles.addAll(roadCorners);
        tiles.addAll(waterCorners);
        tiles.addAll(beaches);
        tiles.addAll(islands);
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

    public BufferedImage getAniSprite(int id, int animationIndex) {
        return tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage getSprite(int x, int y) {
        return atlas.getSubimage(x * 32, y * 32, 32, 32);
    }

    private BufferedImage[] getAniSprites(int x, int y) {
        BufferedImage[] array = new BufferedImage[4];
        for(int i = 0; i < 4; i++) {
            array[i] = getSprite(x + i, y);
        }
        return array;
    }

    public boolean isSpriteAnimation(int spriteID) {
        return tiles.get(spriteID).isAnimation();
    }

    public ArrayList<Tile> getRoadStraights() {
        return roadStraights;
    }

    public ArrayList<Tile> getRoadCorners() {
        return roadCorners;
    }

    public ArrayList<Tile> getWaterCorners() {
        return waterCorners;
    }

    public ArrayList<Tile> getBeaches() {
        return beaches;
    }

    public ArrayList<Tile> getIslands() {
        return islands;
    }
}
