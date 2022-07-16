package gui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.SetGameSate;

public class BottomBar {
    private int x, y, width, height;
    private Playing playing;
    private MyButton buttonMenu;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;

        initButtons();
    }

    private void initButtons()  {
        buttonMenu = new MyButton("Menu", 0, 642, 100, 30);

        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);
        int i = 0 ;

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

        drawTileButtons(graphics);
    }

    public BufferedImage getBufferedImage(int id) {
        return playing.getTileManager().getSprite(id);
    }

    private void drawTileButtons(Graphics graphics) {
        for(MyButton button : tileButtons) {
            graphics.drawImage(getBufferedImage(button.getId()), button.x, button.y, button.width, button.height, null);
        }
    }

    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            System.out.println("pressed!");
            SetGameSate(MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        }
    }

    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        }
    }

    public void mouseReleased(int x, int y) {
        buttonMenu.resetBooleans();
    }
}
