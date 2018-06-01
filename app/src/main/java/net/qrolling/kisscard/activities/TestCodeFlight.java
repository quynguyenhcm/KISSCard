package net.qrolling.kisscard.activities;

/**
 * Created by Quy Nguyen (nguyenledinhquy@gmail.com | https://github.com/quynguyenhcm) on 31/05/18.
 */
public class TestCodeFlight {
    public static void main(String a[]) {

        char[][] grid = new char[][]{
                {'.', '.', '.', '1', '4', '.', '.', '2', '.'},
                {'.', '.', '6', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '1', '.', '.', '.', '.', '.', '.'},
                {'.', '6', '7', '.', '.', '.', '.', '.', '9'},
                {'.', '.', '.', '.', '.', '.', '8', '1', '.'},
                {'.', '3', '.', '.', '.', '.', '.', '.', '6'},
                {'.', '.', '.', '.', '.', '7', '.', '.', '.'},
                {'.', '.', '.', '5', '.', '.', '.', '7', '.'}};

        char[][] grid2 = new char[][]{
                {'.', '.', '.', '.', '2', '.', '.', '9', '.'},
                {'.', '.', '.', '.', '6', '.', '.', '.', '.'},
                {'7', '1', '.', '.', '7', '5', '.', '.', '.'},
                {'.', '7', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '8', '3', '.', '.', '.'},
                {'.', '.', '8', '.', '.', '7', '.', '6', '.'},
                {'.', '.', '.', '.', '.', '2', '.', '.', '.'},
                {'.', '1', '.', '2', '.', '.', '.', '.', '.'},
                {'.', '2', '.', '.', '3', '.', '.', '.', '.'}};

        char[][] grid3 = new char[][]{
                {'.', '9', '.', '.', '4', '.', '.', '.', '.'},
                {'1', '.', '.', '.', '.', '.', '6', '.', '.'},
                {'.', '.', '3', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '7', '.', '.', '.', '.', '.'},
                {'3', '.', '.', '.', '5', '.', '.', '.', '.'},
                {'.', '.', '7', '.', '.', '4', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '7', '.', '.', '.', '.'}};

        char[][] grid4 = new char[][]{
                {'.', '.', '5', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '8', '.', '.', '.', '3', '.'},
                {'.', '5', '.', '.', '2', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '9'},
                {'.', '.', '.', '.', '.', '.', '4', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.', '7'},
                {'.', '1', '.', '.', '.', '.', '.', '.', '.'},
                {'2', '4', '.', '.', '.', '.', '9', '.', '.'}};
        TestCodeFlight tester = new TestCodeFlight();
        System.out.printf("result " + tester.sudoku2(grid4));

    }


    boolean sudoku2(char[][] grid) {

        boolean[] duplicatedCol = new boolean[9];
        boolean[] duplicatedRow = new boolean[9];
        boolean[] duplicatedBlock = new boolean[9];

        //check by row
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[j][i] != '.') {
                    if (duplicatedRow[grid[j][i] - 49]) {
                        return false;
                    }
                    duplicatedRow[grid[j][i] - 49] = true;
                }
                if (grid[i][j] != '.') {
                    if (duplicatedCol[grid[i][j] - 49]) {
                        return false;
                    }
                    duplicatedCol[grid[i][j] - 49] = true;
                }
            }
            duplicatedRow = new boolean[9];
            duplicatedCol = new boolean[9];
        }

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 7; j++) {
                System.out.println("i: " + i + ", j: " + j);

                for (int n = i; n < i + 2; n++) {
                    if (grid[n][j + n] != '.') {
                        if (duplicatedBlock[grid[n][j + n] - 49]) {
                            return false;
                        }
                        duplicatedBlock[grid[n][j + n] - 49] = true;
                    }
                    if (grid[i + n][j] != '.') {
                        if (duplicatedBlock[grid[i + n][j] - 49]) {
                            return false;
                        }
                        duplicatedBlock[grid[i + n][j] - 49] = true;
                    }
                }
                duplicatedBlock = new boolean[9];
            }
        }
        return true;
    }
}
