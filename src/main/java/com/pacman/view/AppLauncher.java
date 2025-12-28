package com.pacman.view;

import com.pacman.controller.Controller;
import com.pacman.controller.ControllerImpl;
import com.pacman.model.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {

  @Override
  public void start(Stage stage) {
    stage.setTitle("Pac-Man");

    Game game = new Game();
    Controller controller = new ControllerImpl(game);
    View view = new View(controller, game, stage);
    game.addObserver(view);

    Scene scene = new Scene(view.render(), 700, 850);
    scene.getStylesheets().add("pacman.css");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }
}
