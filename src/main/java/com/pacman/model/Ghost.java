package com.pacman.model;

import java.util.Random;

public class Ghost extends Entity {
  private String color;
  private Random random;

  public Ghost(int row, int col, String color) {
    super(row, col, "ghost-" + color + ".png");
    this.color = color;
    this.random = new Random();
  }

  public String getColor() {
    return color;
  }

  public void moveRandom(Maze maze) {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    int attempts = 0;
    while (attempts < 10) {
      int dir = random.nextInt(4);
      int newRow = position.getRow() + directions[dir][0];
      int newCol = position.getCol() + directions[dir][1];

      if (maze.canMoveTo(newRow, newCol)) {
        setPosition(newRow, newCol);
        return;
      }
      attempts++;
    }
  }
}
