package com.example.checkersgrid;

import com.example.checkersgrid.ListFunction.ListFunction;
import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Scanner;

public class GameController
{
    private PrintWriter out = null;
    private BufferedReader in = null;
    private final SimpleBoard board;
    private final Pane viewBoard;
    private Board judge;
    private Player player;
    private List<List<Cords>> moves;
    private List<List<Cords>> attacks;
    private List<Cords> ultraAttackSequence;
    public GameController(SimpleBoard board, BufferedReader in, PrintWriter out)
    {
        this.board = board;
        this.in = in;
        this.out = out;
        viewBoard = board.getPane();
    }

    public void startGame()
    {
        judge = board.getJudgeBoard();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> checkServer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }

    public void checkServer() {
        try {

            while (in.ready()) {
                String command = in.readLine();
                System.out.println(command);

                if (command.startsWith("START")) {
                    if (command.replace("START ", "").charAt(0) == Player.WHITE.getShortcut()) {
                        player = Player.WHITE;
                        firstClick();
                    } else {
                        player = Player.BLACK;
                    }
                } else if (command.startsWith("OPPONENT_MOVED")) {
                    List<Cords> move = ListFunction.fromResponse(command.replace("OPPONENT_MOVED ", ""));
                    judge.update(move);
                    update(move);
                    firstClick();
                } else if (command.startsWith("NO_MOVES")) {
                    //TODO:
                    System.out.println(command.replace("NO_MOVES ", ""));
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("GAME OVER");
                    if(board.getWinCondition())
                    {
                        if(command.replace("NO_MOVES ", "").equals("w"))
                        {
                            alert.setHeaderText(Player.BLACK + " WINS");
                        }
                        else
                        {
                            System.out.println("Weszlo");
                            alert.setHeaderText(Player.WHITE + " WINS");
                        }
                    }
                    else
                    {
                        if(command.replace("NO_MOVES ", "").equals("w"))
                        {
                            alert.setHeaderText(Player.WHITE + " WINS");
                        }
                        else
                        {
                            alert.setHeaderText(Player.BLACK + " WINS");
                        }
                    }
                    alert.setContentText("GOOD JOB");
                    alert.show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void firstClick()
    {
        moves = judge.showAllPossibleMovesOfPlayer(player);
        attacks = judge.showAllPossibleAttacksOfPlayer(player);
        attacks.sort((o1, o2) -> o2.size() - o1.size());

        for(int i = 0; i < board.getSize(); i++)
        {
            for(int j = 0; j < board.getSize(); j++)
            {
                if(board.getTiles()[i][j].getPiece() != null)
                {
                    if(board.getTiles()[i][j].getPiece().strikingState())
                    {
                        for(List<Cords> attack : attacks)
                        {
                            if(attack.get(0).x != i && attack.get(0).y != j)
                            {
                                attacks.remove(attack);
                            }
                        }
                    }
                }
            }
        }

        turnOffFields();

        if(attacks.isEmpty() && moves.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("GAME OVER");
            if(board.getWinCondition())
            {
                if(player == Player.WHITE)
                {
                    alert.setHeaderText(Player.BLACK + " WINS");
                }
                else
                {
                    alert.setHeaderText(Player.WHITE + " WINS");
                }
            }
            else
            {
                if(player == Player.WHITE)
                {
                    alert.setHeaderText(Player.WHITE + " WINS");
                }
                else
                {
                    alert.setHeaderText(Player.BLACK + " WINS");
                }
            }
            alert.setContentText("PLAY AGAIN?");
        }
        else if(attacks.isEmpty())
        {
            for(List<Cords> start : moves)
            {
                board.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
            }
        }
        else
        {
            for(List<Cords> start : attacks)
            {
                if(start.size() == attacks.get(0).size())
                {
                    if(board.getTiles()[start.get(0).x][start.get(0).y].getPiece().strikingState())
                    {
                        turnOffFields();
                        board.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
                        break;
                    }
                    board.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
                }
            }
        }

        viewBoard.setOnMouseClicked(mouseEvent -> {
            int xPosition = (int) (mouseEvent.getSceneX()/80);
            int yPosition = (int) (mouseEvent.getSceneY()/80);
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                if(board.getTiles()[xPosition][yPosition].getPiece() != null && board.getTiles()[xPosition][yPosition].isHighlighted())
                {
                    turnOffFields();

                    if(attacks.isEmpty())
                    {
                        for(List<Cords> move : moves)
                        {
                            if(xPosition == move.get(0).x && yPosition == move.get(0).y)
                            {
                                board.getTiles()[move.get(1).x][move.get(1).y].highlightTile(true);
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
                                    board.getTiles()[attack.get(1).x][attack.get(1).y].highlightTile(true);
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
                if(board.getTiles()[dirXPosition][dirYPosition].getPiece() == null && board.getTiles()[dirXPosition][dirYPosition].isHighlighted())
                {
                    board.getTiles()[dirXPosition][dirYPosition].setPiece(board.getTiles()[xPos][yPos].getPiece());
                    board.getTiles()[xPos][yPos].setPiece(null);
                    board.getTiles()[dirXPosition][dirYPosition].getPiece().placeChecker(dirXPosition, dirYPosition);
                    board.getTiles()[dirXPosition][dirYPosition].getPiece().duringStrike(true);
                    viewBoard.setOnMouseClicked(null);

                    if(attacks.isEmpty())
                    {
                        for(List<Cords> move : moves)
                        {
                            if(xPos == move.get(0).x && yPos == move.get(0).y)
                            {
                                if(dirXPosition == move.get(1).x && dirYPosition == move.get(1).y)
                                {
                                    judge.update(move);
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
                                            if(board.getTiles()[currentX][currnetY].getPiece() != null)
                                            {
                                                board.removePiece(currentX, currnetY);
                                                break;
                                            }
                                        }

                                        judge.update(attack);
                                        if(ultraAttackSequence == null)
                                        {
                                            ultraAttackSequence = attack;
                                        }
                                        else
                                        {
                                            ultraAttackSequence.add(attack.get(1));
                                        }

                                        if(attacks.get(0).size() > 2)
                                        {
                                            firstClick();
                                            return;
                                        }
                                        out.println("MOVE " + ListFunction.string(ultraAttackSequence));
                                        ultraAttackSequence = null;
                                    }
                                }
                            }
                        }
                    }
                    
                    turnOffFields();

                    board.getTiles()[dirXPosition][dirYPosition].getPiece().duringStrike(false);
                }
            }
        });
    }
    private void turnOffFields()
    {
        for(GridTile[] column : board.getTiles())
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
    private void update(List<Cords> move)
    {
        Cords start = move.get(0);
        Cords end = move.get(move.size() - 1);

        board.getTiles()[end.x][end.y].setPiece(board.getTiles()[start.x][start.y].getPiece());
        board.getTiles()[start.x][start.y].setPiece(null);
        board.getTiles()[end.x][end.y].getPiece().placeChecker(end.x, end.y);
        //board.getTiles()[move.get(1).x][move.get(1).y].getPiece().duringStrike(true);

        for(int i = 0; i < move.size() - 1; i ++)
        {
            int xDif = Math.abs(move.get(i + 1).x - move.get(i).x);
            int yDif = Math.abs(move.get(i + 1).y - move.get(i).y);

            if(xDif >= 2 || yDif >= 2)
            {
                int currentX = move.get(i).x;
                int currnetY = move.get(i).y;
                int changeOnX = move.get(i + 1).x - move.get(i).x != 0 ? (move.get(i + 1).x - move.get(i).x) / Math.abs((move.get(i + 1).x - move.get(i).x)) : 0;
                int changeOnY = move.get(i + 1).y - move.get(i).y != 0 ? (move.get(i + 1).y - move.get(i).y) / Math.abs((move.get(i + 1).y - move.get(i).y)) : 0;
                while(true)
                {
                    currentX += changeOnX;
                    currnetY += changeOnY;
                    if(board.getTiles()[currentX][currnetY].getPiece() != null)
                    {
                        board.removePiece(currentX, currnetY);
                        break;
                    }
                }
            }
        }
    }
}
