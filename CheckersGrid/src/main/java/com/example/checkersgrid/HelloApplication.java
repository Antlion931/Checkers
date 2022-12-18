package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {
        Board board8 = new Board(8, 3);
        System.out.println(board8.draw());

        Board board10 = new Board(10, 4);

        System.out.println(board10.draw());

        System.out.println("Black possible moves: ");
        List<List<Cords>> movesBLACK10 = board10.showAllPossibleMovesOfPlayer(Player.BLACK);
        for(List<Cords> lc : movesBLACK10) {
            for(Cords c : lc) {
                System.out.print("(");
                System.out.print(c.x);
                System.out.print(", ");
                System.out.print(c.y);
                System.out.print(") ");
            }
            System.out.println();
        }
        System.out.println("White possible moves: ");
        List<List<Cords>> movesWHITE10 = board10.showAllPossibleMovesOfPlayer(Player.WHITE);
        for(List<Cords> lc : movesWHITE10) {
            for(Cords c : lc) {
                System.out.print("(");
                System.out.print(c.x);
                System.out.print(", ");
                System.out.print(c.y);
                System.out.print(") ");
            }
            System.out.println();
        }
        launch();
    }
}