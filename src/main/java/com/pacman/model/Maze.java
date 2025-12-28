package com.pacman.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maze {
    private int[][] grid;
    private int rows;
    private int cols;
    private int totalDots;
    private int dotsRemaining;
    private Random random;

    public Maze(int level) {
        rows = 15;
        cols = 15;
        grid = new int[rows][cols];
        random = new Random();
        generateRandomMaze(level);
    }

    private void generateRandomMaze(int level) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = 0;
            }
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                    grid[row][col] = 1;
                }
            }
        }

        int wallDensity = Math.min(20 + (level * 5), 40);

        for (int i = 0; i < wallDensity; i++) {
            int row = 1 + random.nextInt(rows - 2);
            int col = 1 + random.nextInt(cols - 2);

            if ((row == 1 && col == 1) || (row == rows - 2 && col == cols - 2)) {
                continue;
            }

            grid[row][col] = 1;

            if (random.nextDouble() < 0.4) {
                int length = 1 + random.nextInt(3);
                boolean horizontal = random.nextBoolean();

                for (int j = 1; j <= length; j++) {
                    int newRow = horizontal ? row : row + j;
                    int newCol = horizontal ? col + j : col;

                    if (newRow > 0 && newRow < rows - 1 && newCol > 0 && newCol < cols - 1) {
                        if (!((newRow == 1 && newCol == 1) || (newRow == rows - 2 && newCol == cols - 2))) {
                            grid[newRow][newCol] = 1;
                        }
                    }
                }
            }
        }

        if (!isPathExists(1, 1, rows - 2, cols - 2)) {
            generateRandomMaze(level);
            return;
        }

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (grid[row][col] == 0) {
                    grid[row][col] = 2;
                }
            }
        }

        grid[1][1] = 0;
        grid[rows - 2][cols - 2] = 0;

        countDots();
    }

    private boolean isPathExists(int startRow, int startCol, int endRow, int endCol) {
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> queue = new ArrayList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.remove(0);
            int row = current[0];
            int col = current[1];

            if (row == endRow && col == endCol) {
                return true;
            }

            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        !visited[newRow][newCol] && grid[newRow][newCol] != 1) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return false;
    }

    private void countDots() {
        totalDots = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 2) {
                    totalDots++;
                }
            }
        }
        dotsRemaining = totalDots;
    }

    public boolean canMoveTo(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return grid[row][col] != 1;
    }

    public boolean hasDot(int row, int col) {
        return grid[row][col] == 2;
    }

    public void eatDot(int row, int col) {
        if (grid[row][col] == 2) {
            grid[row][col] = 0;
            dotsRemaining--;
        }
    }

    public boolean isWall(int row, int col) {
        return grid[row][col] == 1;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getDotsRemaining() {
        return dotsRemaining;
    }

    public boolean allDotsEaten() {
        return dotsRemaining == 0;
    }
}