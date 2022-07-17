package managers;

import helperMethods.ImageFix;
import helperMethods.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {
    public Tile GRASS, WATER, ROAD, BL_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER,
            T_WATER, R_WATER, B_WATER, L_WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R,
            TL_ISLE, TR_ISLE, BR_ISLE, BL_ISLE;
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

        tiles.add(ROAD_LR = new Tile(getSprite(8, 0), id++, "LR_Road"));
        tiles.add(ROAD_TB = new Tile(ImageFix.getRotateImage(getSprite(8, 0), 90), id++, "TB_Road"));

        tiles.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, "Road_Bottom_To_Right"));
        tiles.add(ROAD_L_TO_B = new Tile(ImageFix.getRotateImage(getSprite(7, 0),90), id++, "Road_Left_To_Bottom"));
        tiles.add(ROAD_L_TO_T = new Tile(ImageFix.getRotateImage(getSprite(7, 0),180), id++, "Road_Left_To_Top"));
        tiles.add(ROAD_T_TO_R = new Tile(ImageFix.getRotateImage(getSprite(7, 0),270), id++, "Road_Top_To_Right"));

        tiles.add(BL_WATER_CORNER = new Tile(ImageFix.buildImage(getImages(0, 0, 5, 0)), id++, "BL_Water_Corner"));
        tiles.add(TL_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 5, 0), 1, 90), id++, "TL_Water_Corner"));
        tiles.add(BR_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 5, 0), 1, 180), id++, "BR_Water_Corner"));
        tiles.add(TR_WATER_CORNER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 5, 0), 1, 270), id++, "TR_Water_Corner"));

        tiles.add(T_WATER = new Tile(ImageFix.buildImage(getImages(0, 0,6, 0)), id++, "T_Water"));
        tiles.add(R_WATER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0,6, 0), 1, 90), id++, "R_Water"));
        tiles.add(B_WATER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0,6, 0), 1, 180), id++, "B_Water"));
        tiles.add(L_WATER = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0,6, 0), 1, 270), id++, "L_Water"));

        tiles.add(TL_ISLE = new Tile(ImageFix.buildImage(getImages(0, 0, 4, 0)), id++, "TL_Isle"));
        tiles.add(TR_ISLE = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 4, 0), 1, 90), id++, "TR_Isle"));
        tiles.add(BR_ISLE = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 4, 0), 1, 180), id++, "BR_Isle"));
        tiles.add(BL_ISLE = new Tile(ImageFix.getBuildRotateImage(getImages(0, 0, 4, 0), 1, 270), id++, "BL_Isle"));
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
