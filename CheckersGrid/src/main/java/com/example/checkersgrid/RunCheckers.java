package com.example.checkersgrid;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RunCheckers extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //hard coded number of tiles, for testing purposes
        int numberOfTiles = 8;

        CheckerBoard board = new CheckerBoard(numberOfTiles);
        GameController gameController = new GameController(board);
        gameController.startGame();

        Scene scene = new Scene(board, (80 * numberOfTiles), (80 * numberOfTiles));
        scene.setFill(Color.SEAGREEN);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
