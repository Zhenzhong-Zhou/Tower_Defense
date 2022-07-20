package helperMethods;

import objects.PathPoint;

import java.util.ArrayList;

import static helperMethods.Constants.Direction.*;
import static helperMethods.Constants.Tiles.ROAD_TILE;

public class Utilities {
    public static int[][] GetRoadDirectionArray(int [][] levelTypeArray, PathPoint start, PathPoint end) {
        int[][] roadDirectionArray = new int[levelTypeArray.length][levelTypeArray[0].length];

        PathPoint currentTile= start;
        int lastDirection = -1;

        while(!IsCurrentSameAsEnd(currentTile, end)) {
            PathPoint prevTile = currentTile;
            currentTile = GetNextRoadTile(prevTile, lastDirection, levelTypeArray);
            lastDirection = GetDirectionFromPrevToCurrent(prevTile, currentTile);

            roadDirectionArray[prevTile.getyCord()][prevTile.getxCord()] = lastDirection;
        }
        roadDirectionArray[end.getyCord()][end.getxCord()] = lastDirection;
        return roadDirectionArray;
    }

    private static int GetDirectionFromPrevToCurrent(PathPoint prevTile, PathPoint currentTile) {
        // Up or Down
        if(prevTile.getxCord() == currentTile.getxCord()) {
            if(prevTile.getyCord() > currentTile.getyCord()) {
                return UP;
            } else {
                return DOWN;
            }
        } else {
            // Right or Left
            if(prevTile.getxCord() > currentTile.getxCord()) {
                return LEFT;
            } else {
                return RIGHT;
            }
        }
    }

    private static PathPoint GetNextRoadTile(PathPoint prevTile, int lastDirection, int[][] levelTypeArray) {
        int testDirection = lastDirection;
        PathPoint testTile = GetTileInDirection(prevTile, testDirection, lastDirection);

        while(!IsTileRoad(testTile, levelTypeArray)) {
            testDirection++;
            testDirection %= 4;
            testTile= GetNextRoadTile(prevTile, lastDirection, levelTypeArray);
        }
        return testTile;
    }

    private static boolean IsTileRoad(PathPoint testTile, int[][] levelTypeArray) {
        if(testTile != null) {
            if(testTile.getyCord() >= 0) {
                if(testTile.getyCord() < levelTypeArray.length) {
                    if(testTile.getxCord() >= 0) {
                        if(testTile.getxCord() < levelTypeArray[0].length) {
                            if(levelTypeArray[testTile.getyCord()][testTile.getxCord()] == ROAD_TILE) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static PathPoint GetTileInDirection(PathPoint prevTile, int testDirection, int lastDirection) {
        switch(testDirection) {
            case LEFT:
                if(lastDirection != RIGHT) {
                    return new PathPoint(prevTile.getxCord() -1, prevTile.getyCord());
                }
            case UP:
                if(lastDirection != DOWN) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() - 1);
                }
            case RIGHT:
                if(lastDirection != LEFT) {
                    return new PathPoint(prevTile.getxCord()+1, prevTile.getyCord() );
                }
            case DOWN:
                if(lastDirection != UP) {
                    return new PathPoint(prevTile.getxCord(), prevTile.getyCord() + 1);
                }
        }
        return null;
    }

    private static boolean IsCurrentSameAsEnd(PathPoint currentTile, PathPoint end) {
        if(currentTile.getxCord() == end.getxCord()) {
            return currentTile.getyCord() == end.getyCord();
        }
        return false;
    }

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
