package scenes;

import main.Game;

import java.awt.image.BufferedImage;

public class GameScene {
    private final Game game;
    protected int animationIndex;
    protected int tick;
    protected static final int ANIMATION_SPEED = 25;

    public GameScene(Game game) {
        this.game = game;
    }

    protected boolean isAnimation(int spriteID) {
        return getGame().getTileManager().isSpriteAnimation(spriteID);
    }

    protected void updateTick() {
        tick++;
        if(tick >= ANIMATION_SPEED) {
            tick = 0;
            animationIndex++;
            if(animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }

    protected BufferedImage getSprite(int spriteID) {
        return getGame().getTileManager().getSprite(spriteID);
    }

    protected BufferedImage getSprite(int spriteID, int animationIndex) {
        return getGame().getTileManager().getAniSprite(spriteID, animationIndex);
    }

    public Game getGame() {
        return game;
    }
}
