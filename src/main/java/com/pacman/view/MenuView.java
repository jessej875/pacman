package com.pacman.view;

import com.pacman.controller.Controller;
import com.pacman.model.Game;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MenuView implements FXComponent {
  private final Controller controller;
  private final Game game;

  public MenuView(Controller controller, Game game) {
    this.controller = controller;
    this.game = game;
  }

  @Override
  public Parent render() {
    StackPane root = new StackPane();
    root.getStyleClass().add("menu-view");

    VBox vBox = new VBox();
    vBox.getStyleClass().add("menu-container");

    Label title = new Label("PAC-MAN");
    title.getStyleClass().add("title");

    Label highScoreLabel = new Label("High Score: " + game.getHighScore());
    highScoreLabel.getStyleClass().add("high-score-label");

    Button startButton = new Button("START GAME");
    startButton.getStyleClass().add("start-button");
    startButton.setOnAction(e -> controller.startGame());

    Label instructions = new Label("Use WASD to Move");
    instructions.getStyleClass().add("instructions");

    Label goal = new Label("Eat 60% of the white dots and avoid the ghosts!");
    goal.getStyleClass().add("goal");

    vBox.getChildren().addAll(title, highScoreLabel, startButton, instructions, goal);
    root.getChildren().add(vBox);

    return root;
  }
}
