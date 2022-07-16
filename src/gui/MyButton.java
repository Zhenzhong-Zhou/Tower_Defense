package gui;

import java.awt.*;

public class MyButton {
    private final String text;
    public int x, y, width, height, id;
    private Rectangle bounds;
    private boolean hover, pressed;

    // For Normal Buttons
    public MyButton(String text, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = - 1;

        initBounds();
    }

    // For Tile Buttons
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = id;

        initBounds();
    }

    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics graphics) {
        // Body
        drawBody(graphics);

        // Border
        drawBorder(graphics);

        // Text
        drawText(graphics);
    }

    private void drawBody(Graphics graphics) {
        if(hover) {
            graphics.setColor(Color.GRAY);
        } else {
            graphics.setColor(Color.WHITE);
        }
        graphics.fillRect(x, y, width, height);
    }

    private void drawBorder(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, width, height);
        if(pressed) {
            graphics.drawRect(x + 1, y + 1, width - 2, height - 2);
            graphics.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    private void drawText(Graphics graphics) {
        int textWidth = graphics.getFontMetrics().stringWidth(text);
        int textHeight = graphics.getFontMetrics().getHeight();
        graphics.drawString(text, x - textWidth / 2 + width / 2, y + textHeight / 2 + height / 2);
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isMouseHover() {
        return hover;
    }

    public boolean isMousePressed() {
        return pressed;
    }

    public void resetBooleans() {
        this.hover = false;
        this.pressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getId() {
        return id;
    }
}
