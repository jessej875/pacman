package com.pacman.view;

import com.pacman.controller.Controller;
import com.pacman.model.Game;
import com.pacman.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View implements FXComponent, Observer {
  private final Controller controller;
  private final Game game;
  private final Stage stage;

  public View(Controller controller, Game game, Stage stage) {
    this.controller = controller;
    this.game = game;
    this.stage = stage;
  }

  @Override
  public Parent render() {
    if (game.isPlaying() || game.isGameOver() || game.isLevelComplete()) {
      GameView gameView = new GameView(controller, game);
      return gameView.render();
    } else {
      MenuView menuView = new MenuView(controller, game);
      return menuView.render();
    }
  }

  @Override
  public void update() {
    Scene scene = new Scene(render(), 700, 850);
    scene.getStylesheets().add("pacman.css");
    stage.setScene(scene);
  }
}
