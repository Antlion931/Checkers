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
                    //TODO: update
                    firstClick();
                } else if (command.startsWith("NO_MOVES")) {
                    //TODO:
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
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);
            Stage stage = (Stage) viewBoard.getScene().getWindow();
            if(button == ButtonType.OK)
            {
                board.createNew();
                startGame();
            }
            else
            {
                GameMenu board = new GameMenu();
                Scene scene = new Scene(board, 500, 500);
                scene.setFill(Color.SEAGREEN);
                stage.setResizable(false);
                stage.setScene(scene);
                //stage.close();
            }
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
}
