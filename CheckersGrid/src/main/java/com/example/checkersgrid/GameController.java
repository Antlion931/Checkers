package com.example.checkersgrid;

import com.example.checkersgrid.ListFunction.ListFunction;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private Scanner in;
    private PrintWriter out;
    private final SimpleBoard viewBoard;
    private Player player;
    private List<List<Cords>> moves;
    private List<List<Cords>> attacks;

    public GameController(SimpleBoard board, Scanner in, PrintWriter out) {
        viewBoard = board;
        this.in = in;
        this.out = out;
    }

    private void firstClick() {
        moves = new ArrayList<>();
        out.println("MOVES");
        if (in.hasNextInt()) {
            int t = in.nextInt();
            in.nextLine();
            for (int i = 0; i < t; i++) {
                if (in.hasNextLine()) {
                    var x = in.nextLine();
                    System.out.println(x);
                    moves.add(ListFunction.fromResponse(x));
                }
            }
        }

        attacks = new ArrayList<>();
        out.println("ATTACKS");
        if (in.hasNextInt()) {
            int t = in.nextInt();
            in.nextLine();
            for (int i = 0; i < t; i++) {
                if (in.hasNextLine()) {
                    var x = in.nextLine();
                    System.out.println(x);
                    attacks.add(ListFunction.fromResponse(x));
                }
            }
        }

        Collections.sort(attacks, (o1, o2) -> o2.size() - o1.size());
    }

    public class ServerCommunicator implements Runnable {
        @Override
        public void run() {
            while (in.hasNextLine()) {
                var command = in.nextLine();

                System.out.println(command);
                if (command.startsWith("WELCOME")) {
                    if (command.split(" ")[1] == "w") {
                        player = Player.WHITE;
                    } else {
                        player = Player.BLACK;
                    }
                } else if (command.startsWith("YOUR TURN")) {
                    firstClick();
                } else if (command.startsWith("OPPONENT_MOVED")) {
                    out.println("PRINT");

                    String board_drawing = new String();
                    if (in.hasNextInt()) {
                        int t = in.nextInt();
                        in.nextLine();
                        for (int i = 0; i < t; i++) {
                            if (in.hasNextLine()) {
                                var x = in.nextLine();
                                System.out.println(x);
                                board_drawing += x + "\n";
                            }
                        }
                    }
                    System.out.println("Updating...");
                    Platform.runLater(new UpdatingViewBoard(board_drawing));
                    firstClick();
                }
            }
        }

        public class UpdatingViewBoard implements Runnable {
            private String board;

            UpdatingViewBoard(String board) {
                this.board = board;
            }

            @Override
            public void run() {
                viewBoard.update(board);
            }
        }
    }
}