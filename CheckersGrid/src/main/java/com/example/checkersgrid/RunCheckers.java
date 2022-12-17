package com.example.checkersgrid;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
        for(int i = 0; i < numberOfTiles; i++)
        {
            for(int j = 0; j < numberOfTiles; j++)
            {
                GridTile tile = new GridTile((i + j) % 2 == 0);
                grid.add(tile, i, j);
            }
        }
        grid.setAlignment(Pos.CENTER);
        Scene scene = new Scene(grid, (100 * numberOfTiles + 80), (100 * numberOfTiles + 80));
        scene.setFill(Color.GREEN);
        stage.setScene(scene);
        stage.show();
    }
}
