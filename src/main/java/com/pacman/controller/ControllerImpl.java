package com.pacman.controller;

import com.pacman.model.Game;

public class ControllerImpl implements Controller {
  private final Game game;

  public ControllerImpl(Game game) {
    this.game = game;
  }

  @Override
  public void moveUp() {
    game.movePacman(-1, 0);
  }

  @Override
  public void moveDown() {
    game.movePacman(1, 0);
  }

  @Override
  public void moveLeft() {
    game.movePacman(0, -1);
  }

  @Override
  public void moveRight() {
    game.movePacman(0, 1);
  }

  @Override
  public void startGame() {
    game.startGame();
  }

  @Override
  public void nextLevel() {
    game.nextLevel();
  }
}
