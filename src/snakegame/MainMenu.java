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
            switch (menuStatus) {
                case 0:
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
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 640);
        gd.setColor(Color.BLACK);
        gd.fillRect(0, 640, getWidth(), 30);
        switch (menuStatus) {
            case 0:
                text.drawString(gd, "EASY", 245, 123);
                text.drawString(gd, "MEDIUM", 245, 223);
                text.drawString(gd, "HARD", 245, 323);
                text.drawString(gd, "CUSTOMIZE GAME", 245, 423);
                break;
            case 1:
                text.drawString(gd, "NITRO:", 245, 123);
                if (settings.isNitroOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 330, 123);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 330, 123);
                }
                text.setColor(Color.WHITE);

                text.drawString(gd, "SLOW:", 245, 223);
                if (settings.isSlowOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 330, 223);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 330, 223);
                }
                text.setColor(Color.WHITE);

                text.drawString(gd, "MAZE:", 245, 323);
                if (settings.isMazeOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 330, 323);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 330, 323);
                }
                
                text.setColor(Color.WHITE);
                text.drawString(gd, "POISON:", 245, 423);
                if (settings.isPoisonOn()) {
                    text.setColor(Color.GREEN);
                    text.drawString(gd, "ON", 330, 423);
                } else {
                    text.setColor(Color.RED);
                    text.drawString(gd, "OFF", 330, 423);
                }

                text.setColor(Color.WHITE);

                text.drawString(gd, "BACK", 245, 523);
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
