package snakegame;

public class GameSettings {

    private boolean slowOn;
    private boolean nitroOn;
    private boolean mazeOn;
    private boolean poisonOn;
    private int lives;
    private int level;
    private int difficulty;
    private int levelMod;

    GameSettings() {
        slowOn = nitroOn = mazeOn = poisonOn = true;
        level = 0;
        lives = 3;
    }

    public int getLevelMod() {
        return levelMod;
    }

    public void setLevelMod(int levelMod) {
        this.levelMod = levelMod;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPoisonOn() {
        return poisonOn;
    }

    public void setPoison(boolean poisonOn) {
        this.poisonOn = poisonOn;
    }

    public boolean isPwrUpOn() {
        return (slowOn || nitroOn || mazeOn);
    }

    public boolean isSlowOn() {
        return slowOn;
    }

    public void setSlowOn(boolean slowOn) {
        this.slowOn = slowOn;
    }

    public boolean isNitroOn() {
        return nitroOn;
    }

    public void setNitroOn(boolean nitroOn) {
        this.nitroOn = nitroOn;
    }

    public boolean isMazeOn() {
        return mazeOn;
    }

    public void setMazeOn(boolean mazeOn) {
        this.mazeOn = mazeOn;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
