package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import java.util.List;
import java.util.Optional;

public class GameController
{
    private final CheckerBoard viewBoard;
    private Board judge;
    private Player player;
    private List<List<Cords>> moves;
    private List<List<Cords>> attacks;
    public GameController(CheckerBoard board)
    {
        viewBoard = board;
    }

    public void startGame()
    {
        judge = viewBoard.getJudgeBoard();
        player = Player.WHITE;
        firstClick();
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
            if(player == Player.WHITE)
            {
                alert.setHeaderText(Player.BLACK + " WINS");
            }
            else
            {
                alert.setHeaderText(Player.WHITE + " WINS");
            }
            alert.setContentText("PLAY AGAIN?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);
            Stage stage = (Stage) viewBoard.getScene().getWindow();
            if(button == ButtonType.OK)
            {
                viewBoard.createNew();
                startGame();
            }
            else
            {
                stage.close();
            }
        }
        else if(attacks.isEmpty())
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
                    if(viewBoard.getTiles()[start.get(0).x][start.get(0).y].getPiece().strikingState())
                    {
                        turnOffFields();
                        viewBoard.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
                        break;
                    }
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
                    viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece().duringStrike(true);
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

                                        judge.update(attack);
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

                    if(player == Player.WHITE)
                    {
                        player = Player.BLACK;
                    }
                    else
                    {
                        player = Player.WHITE;
                    }

                    viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece().duringStrike(false);
                    firstClick();
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
}
