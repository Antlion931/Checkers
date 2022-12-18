package com.example.checkersgrid;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CheckerBoard extends Pane
{
    private final GridTile[][] tiles;
    private final Group checkerTileGroup;
    private final Group checkerPieceGroup;

    public CheckerBoard(int size)
    {
        checkerTileGroup = new Group();
        checkerPieceGroup = new Group();
        tiles = new GridTile[size][size];
        getChildren().addAll(checkerTileGroup, checkerPieceGroup);

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                GridTile tile = new GridTile((i + j) % 2 == 0, j, i);
                tiles[j][i] = tile;
                checkerTileGroup.getChildren().add(tile);

                CheckerPiece piece = null;

                if(i <= 2 && (i + j) % 2 == 1)
                {
                    piece = new CheckerPiece(CheckerType.BLACK, j, i);
                }
                if(i >= size - 3 && (i + j) % 2 == 1)
                {
                    piece = new CheckerPiece(CheckerType.WHITE, j, i);
                }
                if(piece != null)
                {
                    tiles[j][i].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                }
            }
        }
        firstClick();
//        setOnMouseClicked(mouseEvent -> {
//            int xPosition = (int) (mouseEvent.getSceneX()/80);
//            int yPosition = (int) (mouseEvent.getSceneY()/80);
//            if(tiles[xPosition][yPosition].getPiece() != null && !tiles[xPosition][yPosition].atMovement)
//            {
//                tiles[xPosition][yPosition].setAtMovement(true);
//                setOnMouseClicked(mouseEvent1 -> {
//                    int dirXPosition = (int) (mouseEvent1.getSceneX()/80);
//                    int dirYPosition = (int) (mouseEvent1.getSceneY()/80);
//                    if(tiles[dirXPosition][dirYPosition].getPiece() == null && !tiles[dirXPosition][dirYPosition].atMovement)
//                    {
//                        tiles[dirXPosition][dirYPosition].setPiece(tiles[xPosition][yPosition].getPiece());
//                        tiles[xPosition][yPosition].setPiece(null);
//                        tiles[dirXPosition][dirYPosition].getPiece().placeChecker(dirXPosition, dirYPosition);
//                        //setOnMouseClicked(null);
//                    }
//                });
//            }
//        });
    }
    public void firstClick()
    {
        setOnMouseClicked(mouseEvent -> {
            int xPosition = (int) (mouseEvent.getSceneX()/80);
            int yPosition = (int) (mouseEvent.getSceneY()/80);
            if(tiles[xPosition][yPosition].getPiece() != null && !tiles[xPosition][yPosition].atMovement)
            {
                tiles[xPosition][yPosition].setAtMovement(true);
                secondClick(xPosition, yPosition);
            }
        });
    }
    public void secondClick(int xPos, int yPos)
    {
        setOnMouseClicked(mouseEvent1 -> {
            int dirXPosition = (int) (mouseEvent1.getSceneX()/80);
            int dirYPosition = (int) (mouseEvent1.getSceneY()/80);
            if(tiles[dirXPosition][dirYPosition].getPiece() == null && !tiles[dirXPosition][dirYPosition].atMovement)
            {
                tiles[dirXPosition][dirYPosition].setPiece(tiles[xPos][yPos].getPiece());
                tiles[xPos][yPos].setPiece(null);
                tiles[dirXPosition][dirYPosition].getPiece().placeChecker(dirXPosition, dirYPosition);
                setOnMouseClicked(null);
                firstClick();
            }
        });
    }
    public GridTile getTile(int x, int y)
    {
        return tiles[x][y];
    }
}
