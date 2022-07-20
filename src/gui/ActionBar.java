package gui;

import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.text.DecimalFormat;

import static helperMethods.Constants.Towers.GetName;
import static helperMethods.Constants.Towers.GetTowerCost;
import static main.GameStates.MENU;
import static main.GameStates.SetGameSate;

public class ActionBar extends Bar {
    private final Playing playing;
    private final DecimalFormat format;
    private MyButton buttonMenu, buttonPause;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;
    private MyButton sell, upgrade;
    private int lives = 25;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        format = new DecimalFormat("0.0");

        initButtons();
    }

    private void initButtons() {
        buttonMenu = new MyButton("Menu", 2, 642, 100, 30);
        buttonPause = new MyButton("Pause", 2, 682, 100, 30);

        towerButtons = new MyButton[3];
        int width = 50;
        int height = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (width * 1.1f);

        for(int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, width, height, i);
        }

        sell = new MyButton("Sell", 420, 702, 80, 25);
        upgrade = new MyButton("Upgrade", 545, 702, 80, 25);
    }

    public void removeOneLife() {
        lives--;
        if(lives <= 0) {
            System.out.println("Game Over!");
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

        // Wave Info
        drawWaveInfo(graphics);

        // Gold Info
        drawGoldAmount(graphics);

        // Tower Cost Info
        if(showTowerCost) {
            drawTowerCost(graphics);
        }

        // Game Pause Text
        if(playing.isGamePaused()) {
            graphics.setColor(Color.BLUE);
            graphics.drawString("Game is Paused!", 110, 790);
        }

        // Lives
        graphics.setColor(Color.RED);
        graphics.drawString("Lives: " + lives, 110, 750);
    }

    private void drawButtons(Graphics graphics) {
        buttonMenu.draw(graphics);
        buttonPause.draw(graphics);

        for(MyButton button : towerButtons) {
            graphics.setColor(Color.GRAY);
            graphics.fillRect(button.x, button.y, button.width, button.height);
            graphics.drawImage(playing.getTowerManager().getTowerImages()[button.getId()],
                    button.x, button.y, button.width, button.height, null);
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
            graphics.drawImage(playing.getTowerManager().getTowerImages()[displayedTower.getTowerType()],
                    420, 650, 50, 50, null);
            graphics.setFont(new Font("LucidaSans", Font.BOLD, 15));
            graphics.drawString("" + GetName(displayedTower.getTowerType()), 480, 660);
            graphics.drawString("ID: " + displayedTower.getId(), 480, 675);
            graphics.drawString("Tier: " + displayedTower.getTier(), 560, 660);

            drawSelectedTowerBorder(graphics);
            drawSelectedTowerRange(graphics);

            // Sell Button
            if(!playing.isGamePaused()) {
                sell.draw(graphics);
                drawButtonFeedback(graphics,sell);
            }

            // Upgrade Button
            if(displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower) && !playing.isGamePaused()) {
                upgrade.draw(graphics);
                drawButtonFeedback(graphics,upgrade);
            }

            if(sell.isMouseHover()) {
                // Display the selling value of the tower
                graphics.setColor(Color.RED);
                graphics.drawString("Sell for: "+getSellAmount(displayedTower)+"g", 480, 695);
            } else if(upgrade.isMouseHover() && gold >= getUpgradeAmount(displayedTower)) {
                // Display the upgrade value of the tower
                graphics.setColor(Color.GREEN);
                graphics.drawString("Upgrade for: "+getUpgradeAmount(displayedTower)+"g", 480, 695);
            }
        }
    }

    private void drawWaveInfo(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("LucidaSans", Font.BOLD, 20));
        drawWaveTimerInfo(graphics);
        drawEnemiesLeftInfo(graphics);
        drawWavesLeftInfo(graphics);
    }

    private void drawWaveTimerInfo(Graphics graphics) {
        if(playing.getWaveManager().isWaveTimerStarted()) {
            float timeLeft = playing.getWaveManager().getTimeLeft();
            String formattedText = format.format(timeLeft);
            graphics.drawString("Time Left: " + formattedText, 425, 750);
        }
    }

    private void drawWavesLeftInfo(Graphics graphics) {
        int current = playing.getWaveManager().getWaveIndex() + 1;
        int size = playing.getWaveManager().getWaves().size();
        graphics.drawString("Wave " + current + " / " + size, 425, 770);
    }

    private void drawEnemiesLeftInfo(Graphics graphics) {
        int remain = playing.getEnemyManager().getAmountOfAliveEnemies();
        graphics.drawString("Enemies Left: " + remain, 425, 790);
    }

    private void drawGoldAmount(Graphics graphics) {
        graphics.drawString("Gold:" + gold, 110, 725);
    }

    private void drawSelectedTowerRange(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2,
                (int) displayedTower.getRange() * 2, (int) displayedTower.getRange() * 2);
    }

    public void displayTower(Tower tower) {
        displayedTower = tower;
    }

    private void drawSelectedTowerBorder(Graphics graphics) {
        graphics.setColor(Color.cyan);
        graphics.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
    }

    private void drawTowerCost(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(280, 650, 120, 50);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(280, 650, 120, 50);

        graphics.drawString("" + getTowerCostName(), 285, 670);
        graphics.drawString("Cost: " + getTowerCostGold() + "g", 285, 695);

        // Show not enough gold warning messages
        if(isTowerCostMoreThanCurrentGold()) {
            graphics.setColor(Color.RED);
            graphics.drawString("Can't Afford!", 270, 725);
        }
    }

    private boolean isTowerCostMoreThanCurrentGold() {
        return getTowerCostGold() > gold;
    }

    private String getTowerCostName() {
        return GetName(towerCostType);
    }

    private int getTowerCostGold() {
        return GetTowerCost(towerCostType);
    }

    public void buyTower(int towerType) {
        this.gold -= GetTowerCost(towerType);
    }

    public void addGold(int getReward) {
        this.gold += getReward;
    }

    private void sellClicked() {
        playing.removeTower(displayedTower);
        gold += GetTowerCost(displayedTower.getTowerType())/2;
        int upgradeCost =(displayedTower.getTier()-1)*getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        gold+=upgradeCost;
        displayedTower = null;
    }

    private void upgradeClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if(playing.isGamePaused()) {
            buttonPause.setText("Resume");
        } else {
            buttonPause.setText("Pause");
        }
    }

    public void mouseClicked(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            SetGameSate(MENU);
        } else if(buttonPause.getBounds().contains(x, y)) {
            togglePause();
        } else {
            if(displayedTower != null) {
                if(sell.getBounds().contains(x, y)) {
                    sellClicked();
                    return;
                } else if(upgrade.getBounds().contains(x, y)&& gold >= getUpgradeAmount(displayedTower) && displayedTower.getTier() < 3) {
                    upgradeClicked();
                    return;
                }
            }
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    if(! isGoldEnoughForTower(towerCostType)) {
                        return;
                    }
                    selectedTower = new Tower(0, 0, - 1, button.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    private boolean isGoldEnoughForTower(int towerType) {
        return gold >= GetTowerCost(towerType);
    }

    public void mouseMoved(int x, int y) {
        buttonMenu.setHover(false);
        buttonPause.setPressed(false);
        showTowerCost = false;
        sell.setHover(false);
        upgrade.setHover(false);
        for(MyButton button : towerButtons) {
            button.setHover(false);
        }

        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setHover(true);
        } else if(buttonPause.getBounds().contains(x, y)) {
            buttonPause.setHover(true);
        } else {
            if(displayedTower != null) {
                if(sell.getBounds().contains(x, y)) {
                    sell.setHover(true);
                    return;
                } else if(upgrade.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgrade.setHover(true);
                    return;
                }
            }
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    button.setHover(true);
                    showTowerCost = true;
                    towerCostType = button.getId();
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(buttonMenu.getBounds().contains(x, y)) {
            buttonMenu.setPressed(true);
        } else if(buttonPause.getBounds().contains(x, y)) {
            buttonPause.setPressed(true);
        } else {
            if(displayedTower != null) {
                if(sell.getBounds().contains(x, y)) {
                    sell.setPressed(true);
                    return;
                } else if(upgrade.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgrade.setPressed(true);
                    return;
                }
            }
            for(MyButton button : towerButtons) {
                if(button.getBounds().contains(x, y)) {
                    button.setPressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased() {
        buttonMenu.resetBooleans();
        buttonPause.resetBooleans();
        for(MyButton button : towerButtons) {
            button.resetBooleans();
        }
        sell.resetBooleans();
        upgrade.resetBooleans();
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost =(displayedTower.getTier()-1)*getUpgradeAmount(displayedTower);
        upgradeCost *= 0.5f;
        return GetTowerCost(displayedTower.getTowerType())/2 + upgradeCost;
    }

    private int getUpgradeAmount(Tower displayedTower) {
        return (int) (GetTowerCost(displayedTower.getTowerType())*0.3f);
    }

    public MyButton getButtonMenu() {
        return buttonMenu;
    }

    public MyButton getButtonPause() {
        return buttonPause;
    }

    public int getLives() {
        return lives;
    }
}
