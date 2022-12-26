import java.util.Scanner;

public class Tannenbaum {
    public static void main(String[] args) {
        // call for user input on Size of Christmas Tree
        System.out.println("Tannenbaumprogramm");
        System.out.println("==================");
        System.out.print("Anzahl Äste? ");
        Scanner in = new Scanner(System.in);
        int dimensions = in.nextInt();
        // use input to inform segment and primary Array Sizes
        in.close();
        final int overhang = 2;
        int dimensionsOverhangVar = dimensions + overhang;
        int arrayHeight = (((dimensionsOverhangVar) * dimensions) + dimensions);
        int arrayWidth = ((dimensions * dimensions) + 2);
        // call methods to populate array Segments
        char[][] branch = generateBranch(dimensionsOverhangVar);
        char[][] spacer = generateSpacer(dimensionsOverhangVar, dimensions);
        char[][] log = generateLog(dimensions);
        // define size of primary Array
        char[][] arrayRight = new char[arrayHeight][arrayWidth];
        // fill in primary Array
        populateArray(dimensions, dimensionsOverhangVar, arrayHeight, branch, spacer, log, arrayRight);
        // creates an inverted array to fill in the left side of the tree
        char[][] arrayLeft = arrayInvert(arrayRight);

        outputArrays(arrayHeight, arrayWidth, arrayRight, arrayLeft);
    }

    // simple for loop to ouput both arrays
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

    // populate primary Array using class functions, using for loops to jump along x
    // and y axis according to segment-
    // sizes informed by input
    // for loop variable names informed by naming conventions of arms of a Graph,
    // here and hereafter
    private static void populateArray(int dimensions, int dimensionsOverhangVar, int arrayHeight, char[][] branch,
                                      char[][] spacer, char[][] log, char[][] arrayRight) {
        for (int y = 0; (y * dimensionsOverhangVar) < (arrayHeight - dimensions); y++) {
            for (int x = 0; x < y; x++) {
                // as the for loop counts up in steps of one, to vary the position of the
                // inserted array,
                // it is adjusted for the sizes of arrays inserted before it
                insertArray(spacer, arrayRight, y * dimensionsOverhangVar, x * dimensions);
            }
            // same procedure as for the spacers
            insertArray(branch, arrayRight, y * dimensionsOverhangVar, y * dimensions);
            // log insert is only used once at the bottom of the array
            insertArray(log, arrayRight, dimensions * dimensionsOverhangVar, 0);
        }
    }

    private static char[][] generateBranch(int branchDimensions) {
        // fills the given array according to the variables of branchDimensions
        char[][] branch = new char[branchDimensions][branchDimensions];
        for (int x = 0; x < branchDimensions; x++) {
            for (int y = 0; y < branchDimensions; y++) {
                // if statement to account for the fact that only half of the array is filled
                // for branches
                if (y <= x) {
                    branch[x][y] = '\\';
                }
            }
        }
        // used for debugging: System.out.println(Arrays.deepToString(branch));
        return branch;
    }

    private static char[][] generateSpacer(int spacerHeight, int spacerWidth) {
        // fills the entire array according to the dimensions of spacers given
        char[][] spacer = new char[spacerHeight][spacerWidth];
        for (int x = 0; x < spacerHeight; x++) {
            for (int y = 0; y < spacerWidth; y++) {
                spacer[x][y] = '\\';
            }
        }
        // used for debugging: System.out.println(Arrays.deepToString(spacer));
        return spacer;
    }

    private static char[][] generateLog(int logSize) {
        // fills the entire array according to the dimensions of log given
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
        // simple for loop used for array insertion
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
}