package gui;

import helperMethods.LoadSave;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.MENU;
import static main.GameStates.SetGameSate;

public class Toolbar extends Bar {
    private final Editing editing;
    private final Map<MyButton, ArrayList<Tile>> arrayListMap = new HashMap<MyButton, ArrayList<Tile>>();
    private MyButton buttonGrass, buttonWater, buttonRoadStraight, buttonRoadCorner, buttonCorner, buttonBeaches, buttonIsland;
    private MyButton currentButton;
    private int currentIndex = 0;
    private MyButton buttonMenu, buttonSave;
    private MyButton buttonPathStart, buttonPathEnd;
    private BufferedImage pathStart, pathEnd;
    private Tile selectedTile;

    public Toolbar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        initPathImages();
        initButtons();
    }

    private void initPathImages() {
        pathStart = LoadSave.getSpriteAtlas().getSubimage(7 * 32, 2 * 32, 32, 32);
        pathEnd = LoadSave.getSpriteAtlas().getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 0, 642, 100, 30);
        buttonSave = new MyButton("Save", 0, 672, 100, 30);

        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);
        int i = 0;

        buttonGrass = new MyButton("Grass", xStart, yStart, width, height, i++);
        buttonWater = new MyButton("Water", xStart + xOffset, yStart, width, height, i++);

        initMapButton(buttonRoadStraight, editing.getGame().getTileManager().getRoadStraights(), xStart, yStart, xOffset, width, height, i++);
        initMapButton(buttonRoadCorner, editing.getGame().getTileManager().getRoadCorners(), xStart, yStart, xOffset, width, height, i++);
        initMapButton(buttonWater, editing.getGame().getTileManager().getWaterCorners(), xStart, yStart, xOffset, width, height, i++);
        initMapButton(buttonBeaches, editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, width, height, i++);
        initMapButton(buttonIsland, editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, width, height, i++);

        buttonPathStart = new MyButton("Path Start", xStart, yStart + xOffset, width, height, i++);
        buttonPathEnd = new MyButton("Path End", xStart + xOffset, yStart + xOffset, width, height, i++);
    }

    private void initMapButton(MyButton button, ArrayList<Tile> list, int x, int y, int xOffset, int width, int height, int id) {
        button = new MyButton("", x + xOffset * id, y, width, height, id);
        arrayListMap.put(button, list);
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void rotateSprite() {
        currentIndex++;
        if(currentIndex >= arrayListMap.get(currentButton).size()) {
            currentIndex = 0;
        }
        selectedTile = arrayListMap.get(currentButton).get(currentIndex);
        editing.setSelectedTile(selectedTile);
    }

    public void draw(Graphics graphics) {
        // Background
        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        // Buttons
        drawButtons(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonSave.draw(graphics);

        drawPathButtons(graphics, buttonPathStart, pathStart);
        drawPathButtons(graphics, buttonPathEnd, pathEnd);

        drawNormalButtons(graphics, buttonGrass);
        drawNormalButtons(graphics, buttonWater);
        drawMapButtons(graphics);
        drawSelectedTile(graphics);
    }

    private void drawPathButtons(Graphics graphics, MyButton button, BufferedImage image) {
        graphics.drawImage(image, button.x, button.y, button.width, button.height, null);
        drawButtonFeedback(graphics, button);
    }

    private void drawNormalButtons(Graphics graphics, MyButton button) {
        // Sprite Background
        graphics.drawImage(getBufferedImage(button.getId()), button.x, button.y, button.width, button.height, null);

        drawButtonFeedback(graphics, button);
    }

    private void drawMapButtons(Graphics graphics) {
        for(Map.Entry<MyButton, ArrayList<Tile>> entry : arrayListMap.entrySet()) {
            MyButton button = entry.getKey();
            BufferedImage image = entry.getValue().get(0).getSprite();

            // Sprite Background
            graphics.drawImage(image, button.x, button.y, button.width, button.height, null);

            drawButtonFeedback(graphics, button);
        }
    }

    private void drawButtonFeedback(Graphics graphics, MyButton button) {
        // Mouse Hover
        if(button.isMouseHover()) {
            graphics.setColor(Color.WHITE);
        } else {
            graphics.setColor(Color.BLACK);
        }

        // Border
        graphics.drawRect(button.x, button.y, button.width, button.height);

        // Mouse Pressed
        if(button.isMousePressed()) {
            graphics.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
            graphics.drawRect(button.x + 2, button.y + 2, button.width - 4, button.height - 4);
        }
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    public BufferedImage getBufferedImage(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            SetGameSate(MENU);
        } else if(buttonSave.getBounds().contains(x, y)) {
            saveLevel();
        } else if(buttonGrass.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTitle(buttonGrass.getId());
            editing.setSelectedTile(selectedTile);
        } else if(buttonWater.getBounds().contains(x, y)) {
            selectedTile = editing.getGame().getTileManager().getTitle(buttonWater.getId());
            editing.setSelectedTile(selectedTile);
        } else if(buttonPathStart.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathStart, - 1, - 1);
            editing.setSelectedTile(selectedTile);
        } else if(buttonPathEnd.getBounds().contains(x, y)) {
            selectedTile = new Tile(pathEnd, - 2, - 2);
            editing.setSelectedTile(selectedTile);
        } else {
            for(MyButton button : arrayListMap.keySet()) {
                if(button.getBounds().contains(x, y)) {
                    selectedTile = arrayListMap.get(button).get(0);
                    editing.setSelectedTile(selectedTile);
                    currentButton = button;
                    currentIndex = 0;
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);
        buttonSave.setHover(false);
        buttonGrass.setHover(false);
        buttonWater.setHover(false);
        buttonPathStart.setHover(false);
        buttonPathEnd.setHover(false);
        for(MyButton button : arrayListMap.keySet()) {
            button.setHover(false);
        }

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setHover(true);
        } else if(buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setHover(true);
        } else if(buttonWater.getBounds().contains(x, y)) {
            buttonWater.setHover(true);
        } else if(buttonPathStart.getBounds().contains(x, y)) {
            buttonPathStart.setHover(true);
        } else if(buttonPathEnd.getBounds().contains(x, y)) {
            buttonPathEnd.setHover(true);
        } else {
            for(MyButton button : arrayListMap.keySet()) {
                if(button.getBounds().contains(x, y)) {
                    button.setHover(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setPressed(true);
        } else if(buttonGrass.getBounds().contains(x, y)) {
            buttonGrass.setPressed(true);
        } else if(buttonWater.getBounds().contains(x, y)) {
            buttonWater.setPressed(true);
        } else if(buttonPathStart.getBounds().contains(x, y)) {
            buttonPathStart.setPressed(true);
        } else if(buttonPathEnd.getBounds().contains(x, y)) {
            buttonPathEnd.setPressed(true);
        } else {
            for(MyButton button : arrayListMap.keySet()) {
                if(button.getBounds().contains(x, y)) {
                    button.setPressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased() {
        buttonMenu.resetBooleans();
        buttonSave.resetBooleans();
        buttonGrass.resetBooleans();
        buttonWater.resetBooleans();
        buttonPathStart.resetBooleans();
        buttonPathEnd.resetBooleans();
        for(MyButton button : arrayListMap.keySet()) {
            button.resetBooleans();
        }
    }
}
