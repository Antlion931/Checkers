package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.scene.input.MouseButton;

import java.util.List;

public class GameController
{
    private final CheckerBoard viewBoard;
    private final Board judge;
    private Player player;
    private List<List<Cords>> moves;
    private List<List<Cords>> attacks;
    public GameController(CheckerBoard board)
    {
        viewBoard = board;
        judge = board.getJudgeBoard();
    }

    public void startGame()
    {
        player = Player.WHITE;
        firstClick();
    }
    private void firstClick()
    {
        moves = judge.showAllPossibleMovesOfPlayer(player);
        attacks = judge.showAllPossibleAttacksOfPlayer(player);

        for(List<Cords> start : moves)
        {
            //System.out.println(start.get(0).x + ", " + start.get(0).y);
            viewBoard.getTiles()[start.get(0).x][start.get(0).y].highlightTile(true);
        }
        viewBoard.setOnMouseClicked(mouseEvent -> {

            for(List<Cords> lc : moves) {
                for(Cords c : lc) {
                    System.out.print("(");
                    System.out.print(c.x);
                    System.out.print(", ");
                    System.out.print(c.y);
                    System.out.print(") ");
                }
                System.out.println();
            }

            int xPosition = (int) (mouseEvent.getSceneX()/80);
            int yPosition = (int) (mouseEvent.getSceneY()/80);
            if(mouseEvent.getButton().equals(MouseButton.SECONDARY))
            {
                viewBoard.removePiece(xPosition, yPosition);
            }
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
            {
                if(viewBoard.getTiles()[xPosition][yPosition].getPiece() != null && !viewBoard.getTiles()[xPosition][yPosition].isAtMovement())
                {
                    viewBoard.getTiles()[xPosition][yPosition].setAtMovement(true);
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
                if(viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece() == null && !viewBoard.getTiles()[dirXPosition][dirYPosition].isAtMovement())
                {
                    viewBoard.getTiles()[dirXPosition][dirYPosition].setPiece(viewBoard.getTiles()[xPos][yPos].getPiece());
                    viewBoard.getTiles()[xPos][yPos].setPiece(null);
                    viewBoard.getTiles()[dirXPosition][dirYPosition].getPiece().placeChecker(dirXPosition, dirYPosition);
                    viewBoard.getTiles()[xPos][yPos].setAtMovement(false);
                    viewBoard.setOnMouseClicked(null);
                    for(int i = 0; i < moves.size(); i++)
                    {
                        //System.out.println(moves.get(i).get(1).x);
                        if(xPos == moves.get(i).get(0).x && yPos == moves.get(i).get(0).y)
                        {
                            if(dirXPosition == moves.get(i).get(1).x && dirYPosition == moves.get(i).get(1).y)
                            {
                                judge.update(moves.get(i));
                            }
                        }
                    }
                    firstClick();
                }
            }
        });
    }
}
