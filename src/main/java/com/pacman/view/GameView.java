package com.pacman.view;

import com.pacman.controller.Controller;
import com.pacman.model.Game;
import com.pacman.model.Ghost;
import com.pacman.model.Maze;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameView implements FXComponent {
  private final Controller controller;
  private final Game game;

  public GameView(Controller controller, Game game) {
    this.controller = controller;
    this.game = game;
  }

  @Override
  public Parent render() {
    VBox root = new VBox();
    root.getStyleClass().add("game-view");
    root.setAlignment(Pos.CENTER);

    VBox topSection = new VBox();
    topSection.getStyleClass().add("top-section");

    HBox stats = new HBox();
    stats.getStyleClass().add("stats-box");

    Label levelLabel = new Label("Level: " + game.getLevel());
    levelLabel.getStyleClass().add("stat-label");

    Label scoreLabel = new Label("Score: " + game.getScore());
    scoreLabel.getStyleClass().add("stat-label");

    Label livesLabel = new Label("Lives: " + game.getPacman().getLives());
    livesLabel.getStyleClass().add("stat-label");

    Label dotsLabel = new Label("Dots: " + game.getMaze().getDotsRemaining());
    dotsLabel.getStyleClass().add("stat-label");

    stats.getChildren().addAll(levelLabel, scoreLabel, livesLabel, dotsLabel);
    topSection.getChildren().add(stats);

    GridPane board = createBoard();
    board.getStyleClass().add("board");

    VBox bottomSection = new VBox();
    bottomSection.getStyleClass().add("bottom-section");

    if (game.isGameOver()) {
      Label gameOverLabel = new Label("GAME OVER!");
      gameOverLabel.getStyleClass().add("game-over-label");

      Label finalLevelLabel = new Label("Reached Level: " + game.getLevel());
      finalLevelLabel.getStyleClass().add("final-score-label");

      Label finalScoreLabel = new Label("Final Score: " + game.getScore());
      finalScoreLabel.getStyleClass().add("final-score-label");

      bottomSection.getChildren().addAll(gameOverLabel, finalLevelLabel, finalScoreLabel);
    } else if (game.isLevelComplete()) {
      Label levelCompleteLabel = new Label("LEVEL " + game.getLevel() + " COMPLETE!");
      levelCompleteLabel.getStyleClass().add("level-complete-label");

      Label scoreLabel2 = new Label("Score: " + game.getScore());
      scoreLabel2.getStyleClass().add("final-score-label");

      Button nextLevelButton = new Button("Next Level");
      nextLevelButton.getStyleClass().add("next-level-button");
      nextLevelButton.setOnAction(e -> controller.nextLevel());

      bottomSection.getChildren().addAll(levelCompleteLabel, scoreLabel2, nextLevelButton);
    }

    Button newGameButton = new Button("New Game");
    newGameButton.getStyleClass().add("new-game-button");
    newGameButton.setOnAction(e -> controller.startGame());

    Button returnToMenuButton = new Button("Return to Menu");
    returnToMenuButton.getStyleClass().add("return-menu-button");
    returnToMenuButton.setOnAction(e -> controller.returnToMenu());

    bottomSection.getChildren().addAll(newGameButton, returnToMenuButton);

    root.getChildren().addAll(topSection, board, bottomSection);

    root.setOnKeyPressed(
        e -> {
          if (e.getCode() == KeyCode.W) {
            controller.moveUp();
          } else if (e.getCode() == KeyCode.S) {
            controller.moveDown();
          } else if (e.getCode() == KeyCode.A) {
            controller.moveLeft();
          } else if (e.getCode() == KeyCode.D) {
            controller.moveRight();
          }
        });

    root.setFocusTraversable(true);
    root.requestFocus();

    return root;
  }

  private GridPane createBoard() {
    GridPane grid = new GridPane();
    Maze maze = game.getMaze();

    for (int row = 0; row < maze.getRows(); row++) {
      for (int col = 0; col < maze.getCols(); col++) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");

        if (maze.isWall(row, col)) {
          Rectangle wall = new Rectangle(40, 40);
          wall.getStyleClass().add("wall");
          cell.getChildren().add(wall);
        } else if (maze.hasDot(row, col)) {
          Circle dot = new Circle(5);
          dot.getStyleClass().add("dot");
          cell.getChildren().add(dot);
        }

        grid.add(cell, col, row);
      }
    }

    int pacRow = game.getPacman().getPosition().getRow();
    int pacCol = game.getPacman().getPosition().getCol();
    ImageView pacmanView = loadImage(game.getPacman().getImagePath(), 35);
    StackPane pacmanCell = (StackPane) getNodeFromGridPane(grid, pacCol, pacRow);
    if (pacmanCell != null) {
      pacmanCell.getChildren().add(pacmanView);
    }

    for (Ghost ghost : game.getGhosts()) {
      int gRow = ghost.getPosition().getRow();
      int gCol = ghost.getPosition().getCol();
      ImageView ghostView = loadImage(ghost.getImagePath(), 35);
      StackPane ghostCell = (StackPane) getNodeFromGridPane(grid, gCol, gRow);
      if (ghostCell != null) {
        ghostCell.getChildren().add(ghostView);
      }
    }

    return grid;
  }

  private ImageView loadImage(String path, int size) {
    try {
      Image image = new Image("/images/" + path);
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(size);
      imageView.setFitHeight(size);
      return imageView;
    } catch (Exception e) {
      Rectangle placeholder = new Rectangle(size, size, Color.YELLOW);
      ImageView fallback = new ImageView();
      return fallback;
    }
  }

  private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (javafx.scene.Node node : gridPane.getChildren()) {
      Integer nodeCol = GridPane.getColumnIndex(node);
      Integer nodeRow = GridPane.getRowIndex(node);
      if (nodeCol != null && nodeRow != null && nodeCol == col && nodeRow == row) {
        return node;
      }
    }
    return null;
  }
}
