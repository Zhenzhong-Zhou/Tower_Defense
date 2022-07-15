package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private Game game;
    private Dimension size;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameScreen(Game game) {
        this.game = game;
        setPanelSize();
    }

    public void initInputs() {
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setPanelSize() {
        size = new Dimension(640, 640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        game.getRender().render(graphics);
    }
}
