package snakegame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class HighScore {

    Properties prop;
    private int level1Easy;
    private int level1Medium;
    private int level1Hard;
    private int level2Easy;
    private int level2Medium;
    private int level2Hard;
    private int level3Easy;
    private int level3Medium;
    private int level3Hard;
    private int level4Easy;
    private int level4Medium;
    private int level4Hard;
    private int level5Easy;
    private int level5Medium;
    private int level5Hard;

    //Read highsores from file
    private void readHighScores() {
        level1Easy = Integer.parseInt(prop.getProperty("level1Easy"));
        level1Medium = Integer.parseInt(prop.getProperty("level1Medium"));
        level1Hard = Integer.parseInt(prop.getProperty("level1Hard"));
    }

    public int getHighscore(int level) {
        switch (level) {
            case 0:
                return level1Easy;
            case 1:
                return level1Medium;
            case 2:
                return level1Hard;
            case 3:
                return level2Easy;
            case 4:
                return level2Medium;
            case 5:
                return level2Hard;
            case 6:
                return level3Easy;
            case 7:
                return level3Medium;
            case 8:
                return level3Hard;
            case 9:
                return level4Easy;
            case 10:
                return level4Medium;
            case 11:
                return level4Hard;
            case 12:
                return level5Easy;
            case 13:
                return level5Medium;
            case 14:
                return level5Hard;
        }
        return 0;
    }

    public void setHighScore(int score, int level) {
        String s = "";
        OutputStream output = null;
        switch (level) {
            case 0:
                s = "level1Easy";
                level1Easy = score;
                break;
            case 1:
                s = "level1Medium";
                level1Medium = score;
                break;
            case 2:
                s = "level1Hard";
                level1Hard = score;
                break;
            case 3:
                s = "level2Easy";
                level2Easy = score;
                break;
            case 4:
                s = "level2Medium";
                level2Medium = score;
                break;
            case 5:
                s = "level2Hard";
                level2Hard = score;
                break;
            case 6:
                s = "level3Easy";
                level3Easy = score;
                break;
            case 7:
                s = "level3Medium";
                level3Medium = score;
                break;
            case 8:
                s = "level3Hard";
                level3Hard = score;
                break;
            case 9:
                s = "level4Easy";
                level4Easy = score;
                break;
            case 10:
                s = "level4Medium";
                level4Medium = score;
                break;
            case 11:
                s = "level4Hard";
                level4Hard = score;
                break;
            case 12:
                s = "level2Easy";
                level2Easy = score;
                break;
            case 13:
                s = "level5Medium";
                level5Medium = score;
                break;
            case 14:
                s = "level5Hard";
                level5Hard = score;
                break;
        }
        try {
            output = new FileOutputStream("highscores.properties");
            // set the properties value
            prop.setProperty(s, score + "");
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initializeHighScores() {
        prop = new Properties();
        InputStream in;
        try {
            in = new FileInputStream("highscores.properties");

            try {
                prop.load(in);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
            OutputStream output = null;
            try {
                output = new FileOutputStream("highscores.properties");
                // set the properties value
                prop.setProperty("level1Easy", "0");
                prop.setProperty("level1Medium", "0");
                prop.setProperty("level1Hard", "0");
                prop.setProperty("level2Easy", "0");
                prop.setProperty("level2Medium", "0");
                prop.setProperty("level2Hard", "0");
                prop.setProperty("level3Easy", "0");
                prop.setProperty("level3Medium", "0");
                prop.setProperty("level3Hard", "0");
                prop.setProperty("level4Easy", "0");
                prop.setProperty("level4Medium", "0");
                prop.setProperty("level4Hard", "0");
                prop.setProperty("level5Easy", "0");
                prop.setProperty("level5Medium", "0");
                prop.setProperty("level5Hard", "0");

                // save properties to project root folder
                prop.store(output, null);

            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        readHighScores();
    }
}
