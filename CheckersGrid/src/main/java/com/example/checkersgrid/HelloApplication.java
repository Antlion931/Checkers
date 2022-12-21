package com.example.checkersgrid;

import com.example.checkersgrid.judge.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

    }

    public static void main(String[] args) {
        Board board = new Board(8, 0);
        board.body[1][1] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.BLACK);
        board.body[4][1] = CheckersFactory.getInstance().makeQueenForPlayer(Player.BLACK);
        board.body[2][2] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);
        board.body[4][4] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);
        board.body[6][6] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);
        board.body[2][4] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);
        board.body[4][6] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);


        System.out.println(board.draw());
        System.out.println("Black possible moves: ");
        List<List<Cords>> movesBLACK10 = board.showAllPossibleMovesOfPlayer(Player.BLACK);
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

        System.out.println("Black possible attacks: ");
        List<List<Cords>> attacksBLACK10 = board.showAllPossibleAttacksOfPlayer(Player.BLACK);
        for(List<Cords> lc : attacksBLACK10) {
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
        List<List<Cords>> movesWHITE10 = board.showAllPossibleMovesOfPlayer(Player.WHITE);
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

        System.out.println("White possible attacks: ");
        List<List<Cords>> attacksWHITE10 = board.showAllPossibleAttacksOfPlayer(Player.WHITE);
        for(List<Cords> lc : attacksWHITE10) {
            for(Cords c : lc) {
                System.out.print("(");
                System.out.print(c.x);
                System.out.print(", ");
                System.out.print(c.y);
                System.out.print(") ");
            }
            System.out.println();
        }

        board.update(attacksBLACK10.get(0));

        System.out.println(board.draw());
        System.out.println("Black possible moves: ");
        movesBLACK10 = board.showAllPossibleMovesOfPlayer(Player.BLACK);
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

        System.out.println("Black possible attacks: ");
        attacksBLACK10 = board.showAllPossibleAttacksOfPlayer(Player.BLACK);
        for(List<Cords> lc : attacksBLACK10) {
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
        movesWHITE10 = board.showAllPossibleMovesOfPlayer(Player.WHITE);
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

        System.out.println("White possible attacks: ");
        attacksWHITE10 = board.showAllPossibleAttacksOfPlayer(Player.WHITE);
        for(List<Cords> lc : attacksWHITE10) {
            for(Cords c : lc) {
                System.out.print("(");
                System.out.print(c.x);
                System.out.print(", ");
                System.out.print(c.y);
                System.out.print(") ");
            }
            System.out.println();
        }

        board.update(movesBLACK10.get(8));

        System.out.println(board.draw());
        System.out.println("Black possible moves: ");
        movesBLACK10 = board.showAllPossibleMovesOfPlayer(Player.BLACK);
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

        System.out.println("Black possible attacks: ");
        attacksBLACK10 = board.showAllPossibleAttacksOfPlayer(Player.BLACK);
        for(List<Cords> lc : attacksBLACK10) {
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
        movesWHITE10 = board.showAllPossibleMovesOfPlayer(Player.WHITE);
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

        System.out.println("White possible attacks: ");
        attacksWHITE10 = board.showAllPossibleAttacksOfPlayer(Player.WHITE);
        for(List<Cords> lc : attacksWHITE10) {
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