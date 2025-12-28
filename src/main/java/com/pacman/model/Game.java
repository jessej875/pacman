package com.pacman.model;

import java.util.ArrayList;
import java.util.List;

public class Game implements Subject {
  private Maze maze;
  private Pacman pacman;
  private List<Ghost> ghosts;
  private int score;
  private int highScore;
  private int level;
  private boolean isPlaying;
  private boolean isGameOver;
  private boolean isLevelComplete;
  private List<Observer> observers;

  public Game() {
    maze = new Maze(1);
    pacman = new Pacman(1, 1);
    ghosts = new ArrayList<>();
    score = 0;
    highScore = 0;
    level = 1;
    isPlaying = false;
    isGameOver = false;
    isLevelComplete = false;
    observers = new ArrayList<>();
  }

  public void startGame() {
    level = 1;
    score = 0;
    startLevel();
  }

  public void nextLevel() {
    level++;
    startLevel();
  }

  public void returnToMenu() {
    isPlaying = false;
    isGameOver = false;
    isLevelComplete = false;
    notifyObservers();
  }

  private void startLevel() {
    maze = new Maze(level);
    pacman = new Pacman(1, 1);

    ghosts.clear();
    int numGhosts = Math.min(3 + level, 8);
    String[] colors = {"red", "pink", "blue", "orange", "cyan", "purple", "green", "yellow"};

    for (int i = 0; i < numGhosts; i++) {
      int row, col;
      do {
        row = 1 + (int) (Math.random() * (maze.getRows() - 2));
        col = 1 + (int) (Math.random() * (maze.getCols() - 2));
      } while ((row == 1 && col == 1) || maze.isWall(row, col));

      ghosts.add(new Ghost(row, col, colors[i % colors.length]));
    }

    isPlaying = true;
    isGameOver = false;
    isLevelComplete = false;
    notifyObservers();
  }

  public void movePacman(int dRow, int dCol) {
    if (!isPlaying || isGameOver || isLevelComplete) {
      return;
    }

    int newRow = pacman.getPosition().getRow() + dRow;
    int newCol = pacman.getPosition().getCol() + dCol;

    if (maze.canMoveTo(newRow, newCol)) {
      pacman.setPosition(newRow, newCol);

      if (maze.hasDot(newRow, newCol)) {
        maze.eatDot(newRow, newCol);
        score += 10;

        if (maze.getDotsEatenPercentage() >= 60.0) {
          isLevelComplete = true;
          isPlaying = false;
          if (score > highScore) {
            highScore = score;
          }
        }
      }

      checkGhostCollision();
      moveGhosts();
      checkGhostCollision();

      notifyObservers();
    }
  }

  private void moveGhosts() {
    for (Ghost ghost : ghosts) {
      ghost.moveRandom(maze);
    }
  }

  private void checkGhostCollision() {
    for (Ghost ghost : ghosts) {
      if (pacman.getPosition().equals(ghost.getPosition())) {
        pacman.loseLife();

        if (pacman.getLives() <= 0) {
          isGameOver = true;
          isPlaying = false;
          if (score > highScore) {
            highScore = score;
          }
        } else {
          pacman.reset(1, 1);
          resetGhosts();
        }
        return;
      }
    }
  }

  private void resetGhosts() {
    ghosts.clear();
    int numGhosts = Math.min(3 + level, 8);
    String[] colors = {"red", "pink", "blue", "orange", "cyan", "purple", "green", "yellow"};

    for (int i = 0; i < numGhosts; i++) {
      int row, col;
      do {
        row = 1 + (int) (Math.random() * (maze.getRows() - 2));
        col = 1 + (int) (Math.random() * (maze.getCols() - 2));
      } while ((row == 1 && col == 1) || maze.isWall(row, col));

      ghosts.add(new Ghost(row, col, colors[i % colors.length]));
    }
  }

  public Maze getMaze() {
    return maze;
  }

  public Pacman getPacman() {
    return pacman;
  }

  public List<Ghost> getGhosts() {
    return ghosts;
  }

  public int getScore() {
    return score;
  }

  public int getHighScore() {
    return highScore;
  }

  public int getLevel() {
    return level;
  }

  public boolean isPlaying() {
    return isPlaying;
  }

  public boolean isGameOver() {
    return isGameOver;
  }

  public boolean isLevelComplete() {
    return isLevelComplete;
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  private void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
