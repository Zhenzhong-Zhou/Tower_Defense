package helperMethods;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageFix {
    // Rotate
    public static BufferedImage getRotateImage(BufferedImage image, int rotAngle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics2D = newImage.createGraphics();

        graphics2D.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return newImage;
    }

    // Image layer build
    public static BufferedImage buildImage(BufferedImage[] images) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
        Graphics2D graphics2D = newImage.createGraphics();

        for(BufferedImage image : images) {
            graphics2D.drawImage(image, 0, 0, null);
        }
        graphics2D.dispose();

        return newImage;
    }

    // Rotate second image only
    public static BufferedImage getBuildRotateImage(BufferedImage[] images, int index, int rotation) {
        images[index] = getRotateImage(images[index], rotation);
        return buildImage(images);
    }

    // Rotate second image and animation
    public static BufferedImage[] getBuildRotateImage(BufferedImage[] images, BufferedImage secondImage, int index, int rotation) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage[] array = new BufferedImage[images.length];
        for(int i = 0; i < images.length; i++) {
            BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
            Graphics2D graphics2D = newImage.createGraphics();

            graphics2D.drawImage(images[i], 0, 0, null);
            graphics2D.rotate(Math.toRadians(rotation), width / 2, height / 2);
            graphics2D.drawImage(secondImage, 0, 0, null);
            graphics2D.dispose();

            array[i] = newImage;
        }
        return array;
    }
}
