package main;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    public GameScreen() {

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.cyan);
        graphics.drawRect(150,150, 100, 100)  ;
        graphics.fillRect(50,50, 100, 100);
    }
}
