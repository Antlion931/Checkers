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

public class GameController
{
    private Scanner in;
    private PrintWriter out;
    private final CheckerBoard viewBoard;
    private Player player;
    private List<List<Cords>> moves;
    private List<List<Cords>> attacks;
    public GameController(CheckerBoard board, Scanner in, PrintWriter out)
    {
        viewBoard = board;
        this.in = in;
        this.out = out;
    }

    private void firstClick()
    {
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

        attacks =  new ArrayList<>();
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

        turnOffFields();

        if(attacks.isEmpty())
        {
            for(List<Cords> start : moves)
            {
                viewBoard.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
            }
        }
        else
        {
            for(List<Cords> start : attacks)
            {
                if(start.size() == attacks.get(0).size())
                {
                    viewBoard.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
                }
            }
        }

        viewBoard.setOnMouseClicked(mouseEvent -> {
            int xPosition = (int) (mouseEvent.getSceneX()/80);
            int yPosition = (int) (mouseEvent.getSceneY()/80);
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                if(viewBoard.getTiles()[xPosition][yPosition].getPiece() != null && viewBoard.getTiles()[xPosition][yPosition].isHighlighted())
                {
                    turnOffFields();

                    if(attacks.isEmpty())
                    {
                        for(List<Cords> move : moves)
                        {
                            if(xPosition == move.get(0).x && yPosition == move.get(0).y)
                            {
                                viewBoard.getTiles()[move.get(1).x][move.get(1).y].highlightTile(true);
                            }
                        }
                    }
                    else
                    {
                        for(List<Cords> attack : attacks)
                        {
                            if(attack.size() == attacks.get(0).size())
                            {
                                if(xPosition == attack.get(0).x && yPosition == attack.get(0).y)
                                {
                                    viewBoard.getTiles()[attack.get(1).x][attack.get(1).y].highlightTile(true);
                                }
                            }
                        }
                    }

                    secondClick(xPosition, yPosition);
                }
            }
        });
    }
    private void secondClick(int xPos, int yPos)
    {
        viewBoard.setOnMouseClicked(mouseEvent1 -> {
            int dirXPosition = (int) (mouseEvent1.getSceneX()/80);
            int dirYPosition = (int) (mouseEvent1.getSceneY()/80);
            if(mouseEvent1.getButton().equals(MouseButton.PRIMARY))
            {
                if(viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece() == null && viewBoard.getTiles()[dirXPosition][dirYPosition].isHighlighted())
                {
                    viewBoard.getTiles()[dirXPosition][dirYPosition].setPiece(viewBoard.getTiles()[xPos][yPos].getPiece());
                    viewBoard.getTiles()[xPos][yPos].setPiece(null);
                    viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece().placeChecker(dirXPosition, dirYPosition);
                    viewBoard.setOnMouseClicked(null);

                    if(attacks.isEmpty())
                    {
                        for(List<Cords> move : moves)
                        {
                            if(xPos == move.get(0).x && yPos == move.get(0).y)
                            {
                                if(dirXPosition == move.get(1).x && dirYPosition == move.get(1).y)
                                {
                                    out.println("MOVE " + ListFunction.string(move));
                                }
                            }
                        }
                    }
                    else
                    {
                        for(List<Cords> attack : attacks)
                        {
                            if(attack.size() == 2)
                            {
                                if(xPos == attack.get(0).x && yPos == attack.get(0).y)
                                {
                                    if(dirXPosition == attack.get(1).x && dirYPosition == attack.get(1).y)
                                    {
                                        int currentX = xPos;
                                        int currnetY = yPos;
                                        int changeOnX = dirXPosition - xPos != 0 ? (dirXPosition - xPos) / Math.abs((dirXPosition - xPos)) : 0;
                                        int changeOnY = dirYPosition - yPos != 0 ? (dirYPosition - yPos) / Math.abs((dirYPosition - yPos)) : 0;
                                        while(true)
                                        {
                                            currentX += changeOnX;
                                            currnetY += changeOnY;
                                            if(viewBoard.getTiles()[currentX][currnetY].getPiece() != null)
                                            {
                                                viewBoard.removePiece(currentX, currnetY);
                                                break;
                                            }
                                        }

                                        out.println("MOVE " + ListFunction.string(attack));
                                        if(attacks.get(0).size() > 2)
                                        {
                                            firstClick();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    turnOffFields();
                }
            }
        });
    }
    private void turnOffFields()
    {
        for(GridTile[] column : viewBoard.getTiles())
        {
            for(GridTile tile : column)
            {
                if(tile.isHighlighted())
                {
                    tile.highlightTile(false);
                }
            }
        }
    }

    public class ServerCommunicator implements Runnable {
        @Override
        public void run() {
            while (in.hasNextLine()) {
                var command = in.nextLine();

                System.out.println(command);
                if (command.startsWith("WELCOME")) {
                    if(command.split(" ")[1] == "w") {
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
