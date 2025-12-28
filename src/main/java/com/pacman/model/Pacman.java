package com.pacman.model;

public class Pacman extends Entity {
    private int lives;

    public Pacman(int row, int col) {
        super(row, col, "pacman.png");
        this.lives = 3;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public void reset(int row, int col) {
        setPosition(row, col);
    }
}