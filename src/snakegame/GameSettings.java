package snakegame;

public class GameSettings {

    private boolean slowOn;
    private boolean nitroOn;
    private boolean mazeOn;
    private boolean poisonOn;
    private int difficulty;

    GameSettings() {
        slowOn = nitroOn = mazeOn = poisonOn = true;
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
