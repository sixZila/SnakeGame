package snakegame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import com.golden.gamedev.object.Sprite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainMenu extends GameObject {

    private Text text;
    private Sprite easyButton;
    private Sprite mediumButton;
    private Sprite hardButton;
    private Sprite optionsButton;
    private int menuStatus;
    private final GameSettings settings;
    private final HighScore highScores;

    public MainMenu(GameEngine ge, GameSettings settings, HighScore highScores) {
        super(ge);
        this.settings = settings;
        this.highScores = highScores;
    }

    @Override
    public void initResources() {
        menuStatus = 0;
        showCursor();
        text = new Text(new Font("Courier", Font.PLAIN, 20), Color.white);
        easyButton = new Sprite(getImage("easyButton.png"), 128, 100);
        mediumButton = new Sprite(getImage("mediumButton.png"), 128, 200);
        hardButton = new Sprite(getImage("hardButton.png"), 128, 300);
        optionsButton = new Sprite(getImage("optionsButton.png"), 128, 400);
    }

    @Override
    public void update(long l) {
        if (click()) {
            //0 = main menu | 1 = customize game
            switch (menuStatus) {
                case 0:
                    easyButton.update(l);
                    mediumButton.update(l);
                    hardButton.update(l);
                    optionsButton.update(l);

                    //change this to checkPosMouse([SPRITE NAME], true);
                    if (checkPosMouse(easyButton, true)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(0);
                        finish();
                    } else if (checkPosMouse(mediumButton, true)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(1);
                        finish();
                    } else if (checkPosMouse(hardButton, true)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(2);
                        finish();
                    } else if (checkPosMouse(optionsButton, true)) {
                        menuStatus = 1;
                    }
                    break;
                case 1:
                    //change this to checkPosMouse([SPRITE NAME], true);
                    if (checkPosMouse(245, 75, 370, 95)) {
                        settings.setNitroOn(!settings.isNitroOn());
                    } else if (checkPosMouse(245, 150, 370, 170)) {
                        settings.setSlowOn(!settings.isSlowOn());
                    } else if (checkPosMouse(245, 225, 370, 240)) {
                        settings.setMazeOn(!settings.isMazeOn());
                    } else if (checkPosMouse(245, 300, 370, 320)) {
                        settings.setPoison(!settings.isPoisonOn());
                    } else if (checkPosMouse(330, 375, 340, 395)) {
                        settings.setLives((settings.getLives() - 1) < 1 ? 9 : (settings.getLives() - 1));
                    } else if (checkPosMouse(370, 375, 380, 395)) {
                        settings.setLives((settings.getLives()) % 9 + 1);
                    } else if (checkPosMouse(245, 450, 370, 470)) {
                        menuStatus = 0;
                    }
                    break;
            }
        }
    }

    @Override
    public void render(Graphics2D gd) {
        //Display the background.
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 670);

        gd.setColor(new Color((float) 0.5019, (float) 0.2509, (float) 0.0));
        gd.fillRect(25, 25, 590, 620);

        switch (menuStatus) {
            case 0:
                //Change this to the sprites (the text.drawString)
                easyButton.render(gd);
                mediumButton.render(gd);
                hardButton.render(gd);
                optionsButton.render(gd);
                break;
            case 1:
                text.drawString(gd, "NITRO:", 245, 75);
                writeOnOff(settings.isNitroOn(), 350, 75, gd);

                text.drawString(gd, "SLOW:", 245, 150);
                writeOnOff(settings.isSlowOn(), 350, 150, gd);

                text.drawString(gd, "MAZE:", 245, 225);
                writeOnOff(settings.isMazeOn(), 350, 225, gd);

                text.setColor(Color.WHITE);
                text.drawString(gd, "POISON:", 245, 300);
                writeOnOff(settings.isPoisonOn(), 350, 300, gd);

                text.setColor(Color.WHITE);
                text.drawString(gd, "LIVES:", 245, 375);
                text.drawString(gd, settings.getLives() + "", 350, 375);
                text.drawString(gd, "<", 330, 375);
                text.drawString(gd, ">", 370, 375);

                text.drawString(gd, "BACK", 245, 450);
                break;
        }

    }

    private void writeOnOff(boolean b, int x, int y, Graphics2D gd) {
        if (b) {
            text.setColor(Color.GREEN);
            text.drawString(gd, "ON", x, y);
        } else {
            text.setColor(Color.RED);
            text.drawString(gd, "OFF", x, y);
        }
        text.setColor(Color.WHITE);
    }
}
