package snakegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HighScore {

    Properties prop;
    private int easyScore;
    private int medScore;
    private int hardScore;

    //Read highsores from file
    private void readHighScores() {
        easyScore = Integer.parseInt(prop.getProperty("easy"));
        medScore = Integer.parseInt(prop.getProperty("medium"));
        hardScore = Integer.parseInt(prop.getProperty("hard"));
    }

    public int getHighscore(int level) {
        switch (level) {
            case 0:
                return easyScore;
            case 1:
                return medScore;
            case 2:
                return hardScore;
        }
        return 0;
    }

    public void setHighScore(int score, int level) {
        String s = "";
        OutputStream output = null;
        switch (level) {
            case 0:
                s = "easy";
                easyScore = score;
                break;
            case 1:
                s = "medium";
                medScore = score;
                break;
            case 2:
                s = "hard";
                hardScore = score;
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
                prop.setProperty("easy", "0");
                prop.setProperty("medium", "0");
                prop.setProperty("hard", "0");

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
