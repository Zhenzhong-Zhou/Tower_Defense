package main;

public enum GameStates {
    PLAYING, EDITING, MENU, SETTINGS, GAME_OVER;

    public static GameStates gameState = MENU;

    public static void SetGameSate(GameStates state) {
        gameState = state;
    }
}
