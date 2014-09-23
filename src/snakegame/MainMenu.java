package snakegame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainMenu extends GameObject {

    private Text text;
    private int menuStatus;
    private final GameSettings settings;

    public MainMenu(GameEngine ge, GameSettings settings) {
        super(ge);
        this.settings = settings;
    }

    @Override
    public void update(long l) {
        if (click()) {
            //0 = main menu | 1 = customize game
            switch (menuStatus) {
                case 0:
                    //change this to checkPosMouse([SPRITE NAME], true);
                    if (checkPosMouse(245, 123, 295, 153)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(0);
                        finish();
                    } else if (checkPosMouse(245, 223, 295, 253)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(1);
                        finish();
                    } else if (checkPosMouse(245, 323, 295, 353)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(2);
                        finish();
                    } else if (checkPosMouse(245, 423, 295, 453)) {
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

        switch (menuStatus) {
            case 0:
                //Change this to the sprites (the text.drawString)
                text.drawString(gd, "EASY", 245, 123);
                text.drawString(gd, "MEDIUM", 245, 223);
                text.drawString(gd, "HARD", 245, 323);
                text.drawString(gd, "CUSTOMIZE GAME", 245, 423);
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

    @Override
    public void initResources() {
        menuStatus = 0;
        showCursor();
        text = new Text(new Font("Courier", Font.PLAIN, 20), Color.white);
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
