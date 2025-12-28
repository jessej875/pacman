package com.pacman.model;

public abstract class Entity {
  protected Position position;
  protected String imagePath;

  public Entity(int row, int col, String imagePath) {
    this.position = new Position(row, col);
    this.imagePath = imagePath;
  }

  public Position getPosition() {
    return position;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setPosition(int row, int col) {
    position.setRow(row);
    position.setCol(col);
  }
}
