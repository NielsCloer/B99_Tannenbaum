import java.util.Scanner;

public class Tannenbaum {
    public static void main(String[] args) {
        System.out.println("Tannenbaumprogramm");
        System.out.println("==================");
        System.out.print("Anzahl Äste? ");
        Scanner in = new Scanner(System.in);
        int dimensions = in.nextInt();
        in.close();
        final int overhang = 2;
        int arrayHeight = (((dimensions + overhang) * dimensions) + dimensions);
        int arrayWidth = ((dimensions * dimensions) + 2);
        char[][] branch = generateBranch(dimensions, overhang);
        char[][] spacer = generateSpacer(dimensions, overhang);
        char[][] log = generateLog(dimensions);
        char[][] arrayRight = new char[arrayHeight][arrayWidth];
        populateArray(dimensions, overhang, arrayHeight, branch, spacer, log, arrayRight);
        char[][] arrayLeft = arrayInvert(arrayRight);
        outputArrays(arrayHeight, arrayWidth, arrayRight, arrayLeft);
    }

    private static char[][] generateBranch(int dimensions, int overhang) {
        int branchDimensions = dimensions + overhang;
        char[][] branch = new char[branchDimensions][branchDimensions];
        for (int x = 0; x < branchDimensions; x++) {
            for (int y = 0; y < branchDimensions; y++) {
                if (y <= x) {
                    branch[x][y] = '\\';
                }
            }
        }
        // used for debugging: System.out.println(Arrays.deepToString(branch));
        return branch;
    }

    private static char[][] generateSpacer(int dimensions, int overhang) {
        int spacerHeight = dimensions + overhang;
        int spacerWidth = dimensions;
        char[][] spacer = new char[spacerHeight][spacerWidth];
        for (int x = 0; x < spacerHeight; x++) {
            for (int y = 0; y < spacerWidth; y++) {
                spacer[x][y] = '\\';
            }
        }
        // used for debugging: System.out.println(Arrays.deepToString(spacer));
        return spacer;
    }

    private static char[][] generateLog(int dimensions) {
        int logSize = dimensions;
        char[][] log = new char[logSize][logSize];
        for (int x = 0; x < logSize; x++) {
            for (int y = 0; y < logSize; y++) {
                log[x][y] = '|';
            }
        }
        // used for debugging: System.out.println(Arrays.deepToString(log));
        return log;
    }

    private static void insertArray(char[][] smallArray, char[][] bigArray, int startingPointY, int startingPointX) {
        for (int y = 0; y < smallArray.length; y++) {
            System.arraycopy(smallArray[y], 0, bigArray[startingPointY + y], startingPointX, smallArray[0].length);
        }
    }

    private static char[][] arrayInvert(char[][] inputArray) {
        int height = inputArray.length;
        int width = inputArray[0].length;
        char[][] outputArray = new char[height][width];
        for (int y = 0; y < inputArray.length; y++) {
            for (int x = 0; x < inputArray[0].length; x++) {
                outputArray[y][(width - x) - 1] = inputArray[y][x];
            }
        }
        outputArray = arraySymbolInvert(outputArray);
        return outputArray;
    }

    private static char[][] arraySymbolInvert(char[][] inputArray) {
        int height = inputArray.length;
        int width = inputArray[0].length;
        char[][] outputArray = new char[height][width];
        for (int y = 0; y < inputArray.length; y++) {
            for (int x = 0; x < inputArray[0].length; x++) {
                if (inputArray[y][x] == '\0' || inputArray[y][x] == '|') {
                    if (inputArray[y][x] != '|') {
                        outputArray[y][x] = ' ';
                    } else {
                        outputArray[y][x] = '|';
                    }
                } else {
                    outputArray[y][x] = '/';
                }
            }
        }
        return outputArray;
    }

    private static void populateArray(int dimensions, int overhang, int arrayHeight, char[][] branch,
            char[][] spacer, char[][] log, char[][] arrayRight) {
        int arrayHeightStep = dimensions + overhang;
        for (int y = 0; (y * arrayHeightStep) < (arrayHeight - dimensions); y++) {
            for (int x = 0; x < y; x++) {
                insertArray(spacer, arrayRight, y * arrayHeightStep, x * dimensions);
            }
            insertArray(branch, arrayRight, y * arrayHeightStep, y * dimensions);
            insertArray(log, arrayRight, dimensions * arrayHeightStep, 0);
        }
    }

    private static void outputArrays(int arrayHeight, int arrayWidth, char[][] arrayRight, char[][] arrayLeft) {
        for (int y = 0; y < arrayHeight; y++) {
            for (int x = 0; x < ((arrayWidth) * 2); x++) {
                if (x < arrayWidth) {
                    System.out.print(arrayLeft[y][x]);
                } else if (arrayRight[y][x - arrayWidth] != '\0') {
                    System.out.print(arrayRight[y][x - arrayWidth]);
                }
            }
            System.out.println();
        }
    }
}