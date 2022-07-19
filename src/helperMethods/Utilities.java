package helperMethods;

import java.util.ArrayList;

public class Utilities {
    public static int[][] ArrayListTo2dInt(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] new_Array = new int[ySize][xSize];

        for(int j = 0; j < new_Array.length; j++) {
            for(int i = 0; i < new_Array.length; i++) {
                int index = j * xSize + i;
                new_Array[j][i] = list.get(index);
            }
        }
        return new_Array;
    }

    public static int[] TwoDtoIntArray(int[][] two_Array) {
        int[] one_Array = new int[two_Array.length * two_Array[0].length];

        for(int j = 0; j < two_Array.length; j++) {
            for(int i = 0; i < two_Array.length; i++) {
                int index = j * two_Array.length + i;
                one_Array[index] = two_Array[j][i];
            }
        }
        return one_Array;
    }

    public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);

        return (int) Math.hypot(xDiff, yDiff);
    }
}
