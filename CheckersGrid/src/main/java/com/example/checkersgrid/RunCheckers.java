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
        GameMenu board = new GameMenu();
        Scene scene = new Scene(board, 500, 500);
        scene.setFill(Color.SEAGREEN);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
