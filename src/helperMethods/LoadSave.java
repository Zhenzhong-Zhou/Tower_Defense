package helperMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

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

    // Create a new level with default value
    public static void CreateLevel(String name, int[] id_Array) {
        File newLevel = new File("res/" + name + ".txt");
        if(newLevel.exists()) {
            System.out.println("File: " + name + " is already exists.");
            return;
        } else {
            try {
                newLevel.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
            WriteToFile(newLevel, id_Array);
        }
    }

    private static void WriteToFile(File file, int[] id_Array) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for(Integer id : id_Array) {
                printWriter.println(id);
            }
            printWriter.close();
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static void ReadFromFile() {
        File txtFile = new File("res/readLevel.txt");
        try {
            Scanner scanner = new Scanner(txtFile);
            while(scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    // Save 2D int array to the file
    // Load 2D int array to the file

}
