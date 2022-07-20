package helperMethods;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static helperMethods.Utilities.ArrayListTo2dInt;
import static helperMethods.Utilities.TwoDtoIntArray;

public class LoadSave {
    public static String homePath = System.getProperty("user.home");
    public static String saveFolder = "TowerDefense";
    public static String levelFile = "default_level.txt";
    public static String filePath = homePath + File.separator +saveFolder +File.separator + levelFile;
    private static File dataFile = new File(filePath);

    public static void CreatedFolder() {
        File folder = new File(homePath+File.separator+saveFolder);
        if(!folder.exists()) {
            folder.mkdir();
        }
    }

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
    public static void CreateLevel(int[] id_Array) {
        if(dataFile.exists()) {
            System.out.println("File: " + dataFile + " is already exists.");
        } else {
            try {
                dataFile.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }

            // Add start and end points into the data
            WriteToFile(id_Array, new PathPoint(0, 0), new PathPoint(0, 0));
        }
    }

    // Save 2D int array to the file
    private static void WriteToFile(int[] id_Array, PathPoint start, PathPoint end) {
        try {
            PrintWriter printWriter = new PrintWriter(dataFile);
            for(Integer id : id_Array) {
                printWriter.println(id);
            }
            printWriter.println(start.getxCord());
            printWriter.println(start.getyCord());
            printWriter.println(end.getxCord());
            printWriter.println(end.getyCord());

            printWriter.close();
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static void SaveLevel(int[][] id_Array, PathPoint start, PathPoint end) {
        if(dataFile.exists()) {
            WriteToFile(TwoDtoIntArray(id_Array), start, end);
        } else {
            System.out.println("File: " + dataFile + " does not exist.");
        }
    }

    // Load 2D int array to the file
    private static ArrayList<Integer> ReadFromFile() {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(dataFile);
            while(scanner.hasNextLine()) {
                list.add(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return list;
    }

    public static ArrayList<PathPoint> GetLevelPathPoints() {
        if(dataFile.exists()) {
            ArrayList<Integer> list = ReadFromFile();
            ArrayList<PathPoint> points = new ArrayList<>();
            points.add(new PathPoint(list.get(400), list.get(401)));
            points.add(new PathPoint(list.get(402), list.get(403)));

            return points;
        } else {
            System.out.println("File: " + dataFile + " does not exist!");
            return null;
        }
    }

    public static int[][] GetLevelData() {
        if(dataFile.exists()) {
            ArrayList<Integer> list = ReadFromFile();
            return ArrayListTo2dInt(list, 20, 20);
        } else {
            System.out.println("File: " + dataFile + " does not exist!");
            return null;
        }
    }
}
