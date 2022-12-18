package com.example.checkersgrid;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RunCheckers extends Application
{
    private final GridPane grid = new GridPane();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //hard coded number of tiles, for testing purposes
        int numberOfTiles = 8;

        Pane board = new CheckerBoard(numberOfTiles);

        Scene scene = new Scene(board, (80 * numberOfTiles + 80), (80 * numberOfTiles + 80));
        scene.setFill(Color.SEAGREEN);
        stage.setScene(scene);
        stage.show();
    }
}
