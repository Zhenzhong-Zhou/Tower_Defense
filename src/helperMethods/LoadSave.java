package helperMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
        try {
            assert is != null;
            image = ImageIO.read(is);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        return image;
    }
}
