package com.example.checkersgrid.server;

import com.example.checkersgrid.BoardFactory;
import com.example.checkersgrid.Factory;
import com.example.checkersgrid.ListFunction.ListFunction;
import com.example.checkersgrid.SimpleBoard;
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
    String type;

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

    public synchronized void startGame() {
        currentPlayer.output.println("START");
        currentPlayer.opponent.output.println("START");
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
            if (playerId == Player.WHITE) {
                currentPlayer = this;
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
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
                } else if (command.startsWith("CHOSE_GAME_TYPE")) {
                    if (board != null) {
                        if (command.replace("CHOSE_GAME_TYPE ", "").equals(type)) {
                            output.println("OK");
                            startGame();
                        } else {
                            output.println("NO");
                        }
                    } else {
                        Factory factory = new BoardFactory();
                        type = command.replace("CHOSE_GAME_TYPE ", "");
                        board = factory.getBoard(type).getJudgeBoard();
                        output.println("OK");
                        System.out.println("Game type = " + type);
                    }
                } else if (command.startsWith("PLAYER")) {
                    output.println(playerId.getShortcut());
                }
            }
        }

        private void processMoveCommand(List<Cords> m) {
            try {
                move(m, this);
                output.println("VALID_MOVE");
                opponent.output.println("OPPONENT_MOVED " + ListFunction.string(m));
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }


    }

}