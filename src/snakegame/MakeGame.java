package snakegame;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MakeGame extends GameObject {

    //private Block snake;
    private final double blockSizeX = 16;
    private final double blockSizeY = 16;
    private final GameSettings settings;
    private final HighScore highScores;
    private int DEFAULT_SPEED;
    private int a;
    private Block foodBlock;
    private Block poison;
    private Block pwrUp;
    private ArrayList<Block> snake;
    private ArrayList<Block> border;
    private ArrayList<Block> maze;
    private Text text;
    private int speed;
    private int counter;
    private int snakeDirection;
    private int score;
    private int life;
    private int pwrStatus;
    private int duration;
    private boolean changedDirection;
    private boolean highScoreChecked;
    private double snakeX;
    private double snakeY;
    private double poisonX;
    private double poisonY;
    private double pwrupX;
    private double pwrupY;
    private double foodX;
    private double foodY;
    private int delay;
    //Game status 0 = in game | 1 = gane over | 2 = paused
    private int gameStatus;

    //Initial Instantiation
    public MakeGame(GameEngine ge, GameSettings settings, HighScore highScores) {
        super(ge);
        this.settings = settings;
        this.highScores = highScores;
    }
    
    @Override
    public void initResources() {
        //Instantiate the snake, border and maze (maze power up)
        snake = new ArrayList<>();
        border = new ArrayList<>();
        maze = new ArrayList<>();
        
        gameStatus = delay = 0;
        text = new Text(new Font("Courier", Font.PLAIN, 20), Color.white);
        setDifficulty();
        
        foodBlock = new Block(getImage("foodBlock.png"), -1, -1);
        if (settings.isPoisonOn()) {
            poison = new Block(getImage("poisonBlock.png"), -1, -1);
        }
        pwrUp = new Block();
        setBorder(settings.getLevel());
        resetGame();
    }
    
    @Override
    public void update(long l) {
        switch (gameStatus) {
            case 0:
                //Checks for user input
                if (changedDirection) {
                    listenInput();
                }

                //Delay
                delay = (delay + 1) % speed;
                
                counter = (counter + 1) % 1000;
                duration++;
                
                if (counter == 500 || counter == 0) {
                    if (settings.isPoisonOn()) {
                        resetPoison();
                    }
                }
                
                if (duration == 350) {
                    speed = DEFAULT_SPEED;
                    if (pwrStatus != 3) {
                        pwrupX = pwrupY = -1;
                        pwrUp.setX(-1);
                        pwrUp.setY(-1);
                    }
                }
                
                if (counter == 0 && settings.isPwrUpOn()) {
                    spawnPowerup();
                }
                
                if (delay == 0) {
                    //Moke the snake
                    moveSnake();

                    //Check collision with bounderies.
                    if (snakeX == 0 || snakeX == 39 || snakeY == 0 || snakeY == 39 || checkSnake() || checkMaze()) {
                        //Game Over
                        gameStatus = 1;
                        checkHighScore();
                    }

                    //Check collision with food
                    if (snakeX == foodX && snakeY == foodY) {
                        resetFood();
                        a = 1;
                        score++;
                        System.out.println("Score: " + score);
                    }

                    //Check collision with power up
                    if (snakeX == pwrupX && snakeY == pwrupY) {
                        //Remove power up from the field
                        pwrupX = pwrupY = -1;
                        pwrUp.setX(-1);
                        pwrUp.setY(-1);

                        //Check which power up.
                        switch (pwrStatus) {
                            case 0:
                                speed += 4;
                                break;
                            case 1:
                                speed -= 2;
                                break;
                            case 2:
                                createMaze();
                                break;
                        }
                        
                        pwrStatus = 3;
                        duration = 0;
                    }

                    //Check collision with poison.
                    if (snakeX == poisonX && snakeY == poisonY) {
                        resetPoison();
                        removeLife();
                    }

                    //Update Sprites
                    for (Block b : snake) {
                        b.update(l);
                    }
                    for (Block b : maze) {
                        b.update(l);
                    }
                    if (settings.isPoisonOn()) {
                        poison.update(l);
                    }
                    foodBlock.update(l);
                    pwrUp.update(l);
                }
                break;
            case 1:
            case 2:
                listenInput();
                break;
        }
    }
    
    @Override
    public void render(Graphics2D gd) {
        //Display Background
        gd.setColor(Color.LIGHT_GRAY);
        gd.fillRect(0, 0, getWidth(), 640);
        gd.setColor(Color.BLACK);
        gd.fillRect(0, 640, getWidth(), 30);
        
        for (Block b : snake) {
            b.render(gd);
        }
        
        for (Block b : maze) {
            b.render(gd);
        }
        
        foodBlock.render(gd);
        if (settings.isPoisonOn()) {
            poison.render(gd);
        }
        pwrUp.render(gd);
        
        for (Block b : border) {
            b.render(gd);
        }
        text.drawString(gd, "Score: " + score, 200, 640);
        text.drawString(gd, "Life: " + life, 350, 640);
        text.drawString(gd, "High Score: " + highScores.getHighscore(settings.getDifficulty()), 450, 640);
        
        if (gameStatus == 1) {
            //If the game is over display game over screen.
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 60);
            text.drawString(gd, "GAME OVER", 245, 323);
            text.drawString(gd, "Press [Enter] to restart. Press [Escape] to Exit", 100, 353);
        } else if (gameStatus == 2) {
            //If game is paused display pause screen
            gd.setColor(Color.BLACK);
            gd.fillRect(0, 320, getWidth(), 60);
            text.drawString(gd, "  PAUSED  ", 245, 323);
            text.drawString(gd, "Press [Escape] to resume.", 200, 353);
        }
    }

    //Set the difficulty (speed) of the snake.
    private void setDifficulty() {
        switch (settings.getDifficulty()) {
            case 0:
                DEFAULT_SPEED = 12;
                break;
            case 1:
                DEFAULT_SPEED = 9;
                break;
            case 2:
                DEFAULT_SPEED = 6;
                break;
        }
    }

    //CHECKERS
    //CHECK IF THERE IS A NEW HIGHSCORE
    private void checkHighScore() {
        if (!highScoreChecked) {
            if (score > highScores.getHighscore(settings.getDifficulty())) {
                highScores.setHighScore(score, settings.getDifficulty());
            }
            highScoreChecked = true;
        }
    }

    //CHECK IF COORDINATE IS OVERLAPPING WITH SNAKE
    private boolean checkCoordinate(double x, double y, int mod) {
        for (Block b : snake) {
            if (b.getX() == x * blockSizeX && b.getY() == y * blockSizeY) {
                return true;
            }
        }
        for (Block b : maze) {
            if (b.getX() == x * blockSizeX && b.getY() == y * blockSizeY) {
                return true;
            }
        }
        if (mod != 0) {
            if (foodX == x && foodY == y) {
                return true;
            }
        }
        if (mod != 1) {
            if (poisonX == x && poisonY == y) {
                return true;
            }
        }
        if (mod != 2) {
            if (pwrupX == x && pwrupY == y) {
                return true;
            }
        }
        return false;
    }

    //Checks which power ups are enabled.
    private boolean checkPowerUp() {
        switch (pwrStatus) {
            case 0:
                return !settings.isSlowOn();
            case 1:
                return !settings.isNitroOn();
            case 2:
                return !settings.isMazeOn();
        }
        return false;
    }

    //Change the position of the poison block.
    private void resetPoison() {
        do {
            poisonX = (((int) (Math.random() * 100)) % 38) + 1;
            poisonY = (((int) (Math.random() * 100)) % 38) + 1;
        } while (checkCoordinate(poisonX, poisonY, 1));
        
        poison.setX(poisonX * blockSizeX);
        poison.setY(poisonY * blockSizeY);
    }

    //Change the position of the food block.
    private void resetFood() {
        do {
            foodX = (((int) (Math.random() * 100)) % 38) + 1;
            foodY = (((int) (Math.random() * 100)) % 38) + 1;
        } while (checkCoordinate(foodX, foodY, 0));
        
        foodBlock.setX(foodX * blockSizeX);
        foodBlock.setY(foodY * blockSizeY);
    }

    //Spawn a power up in the map.
    private void spawnPowerup() {
        do {
            pwrupX = (((int) (Math.random() * 100)) % 38) + 1;
            pwrupY = (((int) (Math.random() * 100)) % 38) + 1;
        } while (checkCoordinate(pwrupX, pwrupY, 2));
        
        do {
            pwrStatus = (int) (Math.random() * 100) % 3;

            //Check which power up is chosen.
            switch (pwrStatus) {
                case 0:
                    pwrUp.setImage(getImage("slowBlock.png"));
                    break;
                case 1:
                    pwrUp.setImage(getImage("nitroBlock.png"));
                    break;
                case 2:
                    pwrUp.setImage(getImage("mazeBlock.png"));
            }
            duration = 0;
            pwrUp.setX(pwrupX * blockSizeX);
            pwrUp.setY(pwrupY * blockSizeY);
        } while (checkPowerUp());
    }

    //When a maze power up is created, reduce the snake to 3/4 size and change that into a boundery
    private void createMaze() {
        int size = snake.size() - 1;
        
        for (int i = size; i > size * 3 / 4; i--) {
            maze.add(new Block(getImage("brickBlock.png"), snake.get(i).getX(), snake.get(i).getY()));
            snake.remove(i);
        }
    }

    //Reset the game into its default parameters.
    private void resetGame() {
        changedDirection = highScoreChecked = false;
        pwrupX = pwrupY = poisonX = poisonY = -1;
        score = 0;
        counter = 1;
        snakeDirection = 2;
        speed = DEFAULT_SPEED;
        life = settings.getLives();
        snakeX = 10;
        snakeY = 12;
        
        snake.clear();
        maze.clear();
        
        snake.add(new Block(getImage("snakeBlock.png"), snakeX * blockSizeX, snakeY * blockSizeY));
        snake.add(new Block(getImage("snakeBlock.png"), (snakeX - 1) * blockSizeX, snakeY * blockSizeY));
        
        pwrUp.setX(-1);
        pwrUp.setY(-1);
        if (settings.isPoisonOn()) {
            resetPoison();
        }
        resetFood();
    }

    //Subtract a life to the snake when it eats a poison block.
    private void removeLife() {
        life--;
        if (life == 0) {
            gameStatus = 1;
            checkHighScore();
        }
    }

    //Move the snake.
    private void moveSnake() {
        double oldX, oldY, temp;
        oldX = oldY = temp = -1;
        switch (snakeDirection) {
            case 1:
                snakeY--;
                break;
            case 2:
                snakeX++;
                break;
            case 3:
                snakeY++;
                break;
            case 4:
                snakeX--;
                break;
        }
        
        for (int i = 0; i < snake.size(); i++) {
            if (i == 0) {
                oldX = snake.get(i).getX();
                oldY = snake.get(i).getY();
                snake.get(i).setX(snakeX * blockSizeX);
                snake.get(i).setY(snakeY * blockSizeY);
            } else {
                temp = snake.get(i).getX();
                snake.get(i).setX(oldX);
                oldX = temp;
                temp = snake.get(i).getY();
                snake.get(i).setY(oldY);
                oldY = temp;
            }
        }
        if (a != 0) {
            snake.add(new Block(getImage("snakeBlock.png"), oldX, oldY));
            //hasEaten = false;
            a--;
        }
        changedDirection = true;
    }

    //Listen for an input.
    private void listenInput() {
        switch (gameStatus) {
            case 0:
                if ((keyPressed(KeyEvent.VK_W) || keyPressed(KeyEvent.VK_UP)) && snakeDirection != 3) {
                    snakeDirection = 1;
                    changedDirection = false;
                } else if ((keyPressed(KeyEvent.VK_A) || keyPressed(KeyEvent.VK_LEFT)) && snakeDirection != 2) {
                    snakeDirection = 4;
                    changedDirection = false;
                } else if ((keyPressed(KeyEvent.VK_S) || keyPressed(KeyEvent.VK_DOWN)) && snakeDirection != 1) {
                    snakeDirection = 3;
                    changedDirection = false;
                } else if ((keyPressed(KeyEvent.VK_D) || keyPressed(KeyEvent.VK_RIGHT)) && snakeDirection != 4) {
                    snakeDirection = 2;
                    changedDirection = false;
                } else if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    gameStatus = 2;
                }
                break;
            case 1:
                if (keyPressed(KeyEvent.VK_ENTER)) {
                    System.out.println("New Game");
                    resetGame();
                    gameStatus = 0;
                } else if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    parent.nextGameID = 0;
                    finish();
                }
                break;
            case 2:
                if (keyPressed(KeyEvent.VK_ESCAPE)) {
                    gameStatus = 0;
                }
                break;
        }
    }

    //Check collision with the snake if it has hit any part of itself.
    private boolean checkSnake() {
        double x = 0, y = 0;
        for (int i = 0; i < snake.size(); i++) {
            if (i == 0) {
                x = snake.get(i).getX();
                y = snake.get(i).getY();
            } else {
                if (x == snake.get(i).getX() && y == snake.get(i).getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    //Check if the snake has collided with the maze.
    private boolean checkMaze() {
        double x = snake.get(0).getX(), y = snake.get(0).getY();
        for (Block b : maze) {
            if (b.getX() == x && b.getY() == y) {
                return true;
            }
        }
        return false;
    }

    //Instantiate the border.
    private void setBorder(int level) {
        switch (level) {
            case 1:
                for (int i = 0; i < 40; i++) {
                    for (int j = 0; j < 40; j++) {
                        if ((i == 0 || i == 39) || (j == 0 || j == 39)) {
                            border.add(new Block(getImage("brickBlock.png"), j * blockSizeX, i * blockSizeY));
                        }
                    }
                }
                break;
        }
    }
}
