package com.example.checkersgrid.server;

import com.example.checkersgrid.ListFunction.ListFunction;
import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import com.example.checkersgrid.judge.PlayerInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    Board board;

    ServerPlayer currentPlayer;

    public synchronized void move(List<Cords> m, ServerPlayer player) {
        if (player != currentPlayer) {
            throw new IllegalStateException("Not your turn");
        } else if (player.opponent == null) {
            throw new IllegalStateException("You don't have an opponent yet");
        }

        if (board.showAllPossibleAttacksOfPlayer(player.playerId).size() > 0) {

            boolean error = true;
            outer_for:
            for (List<Cords> lc : board.showAllPossibleAttacksOfPlayer(player.playerId)) {
                if (lc.size() == m.size()) {
                    for (int i = 0; i < m.size(); i++) {
                        if (!lc.get(i).cordsEquals(m.get(i))) {
                            continue outer_for;
                        }
                    }
                    error = false;
                } else if (lc.size() > m.size()) {
                    throw new IllegalStateException("Too short attack");
                }
            }

            if (error) {
                throw new IllegalStateException("Not valid attack");
            }
        } else {
            boolean error = true;
            outer_for:
            for (List<Cords> lc : board.showAllPossibleMovesOfPlayer(player.playerId)) {
                if (lc.size() == m.size()) {
                    for (int i = 0; i < m.size(); i++) {
                        if (!lc.get(i).cordsEquals(m.get(i))) {
                            continue outer_for;
                        }
                    }
                    error = false;
                }
            }

            if (error) {
                throw new IllegalStateException("invalid move");
            }
        }

        board.update(m);
        currentPlayer = currentPlayer.opponent;
    }

    public synchronized String print() {
        return board.draw();
    }

    public synchronized String showPossibleAttacksOfPlayer(PlayerInterface player) {
        String result = new String();
        result += board.showAllPossibleAttacksOfPlayer(player).size();
        result += "\n";
        for(List<Cords> lc : board.showAllPossibleAttacksOfPlayer(player)) {
            for(Cords c : lc) {
                result += c.x;
                result += ",";
                result += c.y;
                result += " ";
            }
            result += "\n";
        }
        return result;
    }

    public synchronized String showPossibleMovesOfPlayer(PlayerInterface player) {
        String result = new String();
        result += board.showAllPossibleMovesOfPlayer(player).size();
        result += "\n";
        for(List<Cords> lc : board.showAllPossibleMovesOfPlayer(player)) {
            for(Cords c : lc) {
                result += c.x;
                result += ",";
                result += c.y;
                result += " ";
            }
            result += "\n";
        }
        return result;
    }

    public class ServerPlayer implements Runnable{
        PlayerInterface playerId;
        ServerPlayer opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public ServerPlayer(Socket socket , PlayerInterface playerId) {
            this.socket = socket;
            this.playerId = playerId;
        }

        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + playerId.getShortcut());
            if (playerId == Player.WHITE) {
                currentPlayer = this;
                board = new Board(8, 3);
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("YOUR TURN");
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVES")) {
                    output.println(showPossibleMovesOfPlayer(playerId));
                } else if (command.startsWith("ATTACKS")) {
                    output.println(showPossibleAttacksOfPlayer(playerId));
                } else if (command.startsWith("MOVE")) {
                    List<Cords> m = ListFunction.fromResponse(command.replace("MOVE ", ""));

                    processMoveCommand(m);
                } else if (command.startsWith("PRINT")) {
                    output.println("9\n");
                    System.out.println(print());
                    output.println(print());
                }
            }
        }

        private void processMoveCommand(List<Cords> m) {
            try {
                move(m, this);
                output.println("VALID_MOVE");
                opponent.output.println("OPPONENT_MOVED");
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }


    }

}
