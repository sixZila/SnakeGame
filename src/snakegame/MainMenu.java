package snakegame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MainMenu extends GameObject {

    private Text text;
    private int menuStatus;
    private GameSettings settings;

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
                    if (checkPosMouse(245, 123, 295, 153)) {
                        settings.setNitroOn(!settings.isNitroOn());
                    } else if (checkPosMouse(245, 223, 295, 253)) {
                        settings.setSlowOn(!settings.isSlowOn());
                    } else if (checkPosMouse(245, 323, 295, 353)) {
                        settings.setMazeOn(!settings.isMazeOn());
                    } else if (checkPosMouse(245, 423, 295, 453)) {
                        settings.setPoison(!settings.isPoisonOn());
                    } else if (checkPosMouse(245, 423, 295, 553)) {
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
                if (settings.isNitroOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 350, 75);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 350, 75);
                }
                text.setColor(Color.WHITE);

                text.drawString(gd, "SLOW:", 245, 150);
                if (settings.isSlowOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 350, 150);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 350, 150);
                }
                text.setColor(Color.WHITE);

                text.drawString(gd, "MAZE:", 245, 225);
                if (settings.isMazeOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 350, 225);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 350, 225);
                }

                text.setColor(Color.WHITE);
                text.drawString(gd, "POISON:", 245, 300);
                if (settings.isPoisonOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 350, 300);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 350, 300);
                }

                text.setColor(Color.WHITE);
                text.drawString(gd, "LIVES:", 245, 375);
                text.drawString(gd, "3", 350, 375);
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
}
