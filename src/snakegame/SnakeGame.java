package snakegame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;
import java.awt.Dimension;

public class SnakeGame extends GameEngine {

    private final GameSettings settings;
    private final HighScore highScores;

    SnakeGame() {
        settings = new GameSettings();
        highScores = new HighScore();
        highScores.initializeHighScores();
    }

    @Override
    public GameObject getGame(int GameID) {
        switch (GameID) {
            case 0:
                return new MainMenu(this, settings, highScores);
            case 1:
                return new MakeGame(this, settings, highScores);
        }

        return null;
    }

    public static void main(String[] args) {
        // GameEngine class creation is equal with Game class creation
        GameLoader game = new GameLoader();
        game.setup(new SnakeGame(), new Dimension(640, 670), false);
        game.start();
    }
}
