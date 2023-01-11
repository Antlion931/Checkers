package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class RunCheckers extends Application
{
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        socket = new Socket("localhost", 58902);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);

        //hard coded number of tiles, for testing purposes
        int numberOfTiles = 8;

        GameMenu board = new GameMenu(in, out);
        Scene scene = new Scene(board, 500, 500);

        scene.setFill(Color.SEAGREEN);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
