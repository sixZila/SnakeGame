package snakegame;

import com.golden.gamedev.object.Sprite;
import java.awt.image.BufferedImage;

public class Block extends Sprite {
    
    public Block(){
        super();
    }

    public Block(BufferedImage image, double x, double y) {
        super(image, x, y);
    }
}
