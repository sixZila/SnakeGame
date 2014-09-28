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
    private Sprite noBorderButton;
    private Sprite borderButton;
    private Sprite cornerBorderButton;
    private Sprite innerBorderButton;
    private Sprite innerCornerBorderButton;
    private Sprite optionsButton;
    private Sprite backButton;
    private Sprite poisonButton;
    private Sprite slowButton;
    private Sprite nitroButton;
    private Sprite mazeButton;
    private int menuStatus;
    private int modifier;
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
        modifier = -1;
        showCursor();
        text = new Text(new Font("Courier", Font.PLAIN, 20), Color.white);
        easyButton = new Sprite(getImage("easyButton.png"), 128, 100);
        mediumButton = new Sprite(getImage("mediumButton.png"), 128, 200);
        hardButton = new Sprite(getImage("hardButton.png"), 128, 300);
        backButton = new Sprite(getImage("backButton.png"), 128, 400);

        noBorderButton = new Sprite(getImage("1.png"), 128, 50);
        borderButton = new Sprite(getImage("2.png"), 128, 150);
        cornerBorderButton = new Sprite(getImage("3.png"), 128, 250);
        innerBorderButton = new Sprite(getImage("4.png"), 128, 350);
        innerCornerBorderButton = new Sprite(getImage("5.png"), 128, 450);
        optionsButton = new Sprite(getImage("optionsButton.png"), 128, 550);

        slowButton = new Sprite(getImage("slow.png"), 128, 50);
        nitroButton = new Sprite(getImage("nitro.png"), 128, 150);
        mazeButton = new Sprite(getImage("maze.png"), 128, 250);
        poisonButton = new Sprite(getImage("poison.png"), 128, 350);
    }

    @Override
    public void update(long l) {
        if (click()) {
            //0 = main menu | 1 = customize game
            switch (menuStatus) {
                case 0:
                    noBorderButton.update(l);
                    borderButton.update(l);
                    cornerBorderButton.update(l);
                    innerBorderButton.update(l);
                    innerCornerBorderButton.update(l);

                    //change this to checkPosMouse([SPRITE NAME], true);
                    if (checkPosMouse(borderButton, true)) {
                        menuStatus = 1;
                        settings.setLevel(1);
                        modifier = 0;
                    } else if (checkPosMouse(noBorderButton, true)) {
                        menuStatus = 1;
                        settings.setLevel(0);
                        modifier = 1;
                    } else if (checkPosMouse(cornerBorderButton, true)) {
                        menuStatus = 1;
                        settings.setLevel(2);
                        modifier = 2;
                    } else if (checkPosMouse(innerBorderButton, true)) {
                        menuStatus = 1;
                        settings.setLevel(3);
                        modifier = 3;
                    } else if (checkPosMouse(innerCornerBorderButton, true)) {
                        menuStatus = 1;
                        settings.setLevel(4);
                        modifier = 4;
                    } else if (checkPosMouse(optionsButton, true)) {
                        menuStatus = 2;
                        backButton.setY(550);
                    }
                    break;
                case 1:
                    easyButton.update(l);
                    mediumButton.update(l);
                    hardButton.update(l);
                    if (checkPosMouse(easyButton, true)) {
                        parent.nextGameID = 1;
                        settings.setLevelMod(modifier * 3);
                        settings.setDifficulty(0);
                        finish();
                    } else if (checkPosMouse(mediumButton, true)) {
                        parent.nextGameID = 1;
                        settings.setLevelMod(modifier * 3 + 1);
                        settings.setDifficulty(1);
                        finish();
                    } else if (checkPosMouse(hardButton, true)) {
                        parent.nextGameID = 1;
                        settings.setDifficulty(2);
                        settings.setLevelMod(modifier * 3 + 2);
                        finish();
                    } else if (checkPosMouse(backButton, true)) {
                        menuStatus = 0;
                    }
                case 2:
                    slowButton.update(l);
                    nitroButton.update(l);
                    mazeButton.update(l);
                    poisonButton.update(l);
                    //change this to checkPosMouse([SPRITE NAME], true);
                    if (checkPosMouse(nitroButton, true)) {
                        settings.setNitroOn(!settings.isNitroOn());
                        
                        if (settings.isNitroOn()) {
                            nitroButton.setImage(getImage("nitro.png"));
                        } else {
                            nitroButton.setImage(getImage("nitro_off.png"));
                        }
                    } else if (checkPosMouse(slowButton, true)) {
                        settings.setSlowOn(!settings.isSlowOn());
                        
                        if (settings.isSlowOn()) {
                            slowButton.setImage(getImage("slow.png"));
                        } else {
                            slowButton.setImage(getImage("slow_off.png"));
                        }
                    } else if (checkPosMouse(mazeButton, true)) {
                        settings.setMazeOn(!settings.isMazeOn());
                        
                        if (settings.isMazeOn()) {
                            mazeButton.setImage(getImage("maze.png"));
                        } else {
                            mazeButton.setImage(getImage("maze_off.png"));
                        }
                    } else if (checkPosMouse(poisonButton, true)) {
                        settings.setPoison(!settings.isPoisonOn());
                        
                        if (settings.isPoisonOn()) {
                            poisonButton.setImage(getImage("poison.png"));
                        } else {
                            poisonButton.setImage(getImage("poison_off.png"));
                        }
                    } else if (checkPosMouse(330, 450, 340, 470)) {
                        settings.setLives((settings.getLives() - 1) < 1 ? 9 : (settings.getLives() - 1));
                    } else if (checkPosMouse(370, 450, 380, 470)) {
                        settings.setLives((settings.getLives()) % 9 + 1);
                    } else if (checkPosMouse(backButton, true)) {
                        menuStatus = 0;
                        backButton.setY(400);
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
                noBorderButton.render(gd);
                borderButton.render(gd);
                cornerBorderButton.render(gd);
                innerBorderButton.render(gd);
                innerCornerBorderButton.render(gd);
                optionsButton.render(gd);
                break;
            case 1:
                easyButton.render(gd);
                mediumButton.render(gd);
                hardButton.render(gd);
                backButton.render(gd);
                break;
            case 2:
                nitroButton.render(gd);
                slowButton.render(gd);
                mazeButton.render(gd);
                poisonButton.render(gd);

                text.setColor(Color.WHITE);
                text.drawString(gd, "LIVES:", 245, 450);
                text.drawString(gd, settings.getLives() + "", 350, 450);
                text.drawString(gd, "<", 330, 450);
                text.drawString(gd, ">", 370, 450);

                backButton.render(gd);
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
