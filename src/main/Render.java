package main;

import java.awt.*;

public class Render {
    private final Game game;

    public Render(Game game) {
        this.game = game;
    }

    public void render(Graphics graphics) {
        switch(GameStates.gameState) {
            case MENU:
                game.getMenu().render(graphics);
                break;
            case PLAYING:
                game.getPlaying().render(graphics);
                break;
            case EDITING:
                game.getEditor().render(graphics);
                break;
            case SETTINGS:
                game.getSettings().render(graphics);
                break;
            case GAME_OVER:
                game.getGameOver().render(graphics);
                break;
            default:
                break;
        }
    }
}
