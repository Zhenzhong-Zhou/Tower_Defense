package gui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.SetGameSate;

public class BottomBar {
    private final int x, y, width, height;
    private final Playing playing;
    private final ArrayList<MyButton> tileButtons = new ArrayList<>();
    private MyButton buttonMenu, buttonSave;
    private Tile selectedTile;

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;

        initButtons();
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

        for(Tile tile : playing.getTileManager().tiles) {
            tileButtons.add(new MyButton(tile.getName(), xStart + xOffset * i, yStart, width, height, i));
            i++;
        }
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

        drawTileButtons(graphics);
        drawSelectedTile(graphics);
    }

    public BufferedImage getBufferedImage(int id) {
        return playing.getTileManager().getSprite(id);
    }

    private void drawTileButtons(Graphics graphics) {
        for(MyButton button : tileButtons) {
            // Sprite Background
            graphics.drawImage(getBufferedImage(button.getId()), button.x, button.y, button.width, button.height, null);

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
    }

    private void drawSelectedTile(Graphics graphics) {
        if(selectedTile != null) {
            graphics.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(550, 650, 50, 50);
        }
    }

    private void saveLevel() {
        playing.saveLevel();
    }

    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            System.out.println("pressed!");
            SetGameSate(MENU);
        } else if(buttonSave.getBounds().contains(x, y)) {
            saveLevel();
        } else {
            for(MyButton button : tileButtons) {
                if(button.getBounds().contains(x, y)) {
                    selectedTile = playing.getTileManager().getTitle(button.getId());
                    playing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);
        buttonSave.setHover(false);
        for(MyButton button : tileButtons) {
            button.setHover(false);
        }

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        } else if(buttonSave.getBounds().contains(x, y)) {
            buttonSave.setHover(true);
        } else {
            for(MyButton button : tileButtons) {
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
        } else {
            for(MyButton button : tileButtons) {
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
        for(MyButton button : tileButtons) {
            button.resetBooleans();
        }
    }
}
