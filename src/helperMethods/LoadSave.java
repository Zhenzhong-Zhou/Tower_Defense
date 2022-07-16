package helperMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
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
        } else {
            try {
                newLevel.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
            WriteToFile(newLevel, id_Array);
        }
    }

    // Save 2D int array to the file
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

    public static void SaveLevel(String name, int[][] id_Array) {
        File levelFile = new File("res/" + name + ".txt");
        if(levelFile.exists()) {
            WriteToFile(levelFile, Utilities.TwoDtoIntArray(id_Array));
        } else {
            System.out.println("File: " + name + " does not exist.");
        }
    }

    // Load 2D int array to the file
    private static ArrayList<Integer> ReadFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return list;
    }

    public static int[][] GetLevelData(String name) {
        File levelFile = new File("res/" + name + ".txt");

        if(levelFile.exists()) {
            ArrayList<Integer> list = ReadFromFile(levelFile);
            return Utilities.ArrayListTo2dInt(list, 20, 20);
        } else {
            System.out.println("File: " + name + " does not exist!");
            return null;
        }
    }
}
