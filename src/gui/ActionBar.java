package gui;

import objects.Tower;
import scenes.Playing;

import java.awt.*;

import static helperMethods.Constants.Towers.GetName;
import static main.GameStates.MENU;
import static main.GameStates.SetGameSate;

public class ActionBar extends Bar {
    private final Playing playing;
    private MyButton buttonMenu;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 0, 642, 100, 30);

        towerButtons = new MyButton[3];
        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);

        for(int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, width, height, i);
        }
    }

    public void draw(Graphics graphics) {
        // Background
        graphics.setColor(new Color(220, 123, 15));
        graphics.fillRect(x, y, width, height);

        // Buttons
        drawButtons(graphics);

        // Displayed Tower
        drawDisplayedTower(graphics);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);

        for(MyButton button : towerButtons) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(button.x, button.y, button.width, button.height);
            graphics.drawImage(playing.getTowerManager().getTowerImages()[button.getId()], button.x, button.y, button.width, button.height, null);
            drawButtonFeedback(graphics, button);
        }
    }

    private void drawDisplayedTower(Graphics graphics) {
        if(displayedTower != null) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(410, 645, 220, 85);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(410, 645, 220, 85);
            graphics.drawRect(420, 650, 50, 50);
            graphics.drawImage(playing.getTowerManager().getTowerImages()[displayedTower.getTowerType()], 420, 650, 50, 50, null);
            graphics.setFont(new Font("LucidaSans", Font.BOLD, 15));
            graphics.drawString("" + GetName(displayedTower.getTowerType()), 490, 660);
            graphics.drawString("ID: " + displayedTower.getId(), 490, 675);

            drawSelectedTowerBorder(graphics);
        }
    }

    public void displayTower(Tower tower) {
        displayedTower = tower;
    }

    private void drawSelectedTowerBorder(Graphics graphics) {
        graphics.setColor(Color.cyan);
        graphics.drawRect(displayedTower.getX(), displayedTower.getY(),32, 32);
    }

    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            SetGameSate(MENU);
        } else {
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0, - 1, button.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);
        for(MyButton button : towerButtons) {
            button.setHover(false);
        }

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        } else {
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    button.setHover(true);
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        } else {
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    button.setPressed(true);
                }
            }
        }
    }

    public void mouseReleased() {
        buttonMenu.resetBooleans();
        for(MyButton button : towerButtons) {
            button.resetBooleans();
        }
    }
}
