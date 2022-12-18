package com.example.checkersgrid;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CheckerBoard extends Pane
{
    private final GridTile[][] tiles;
    private Group checkerTileGroup;
    private Group checkerPieceGroup;

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
        setOnMouseClicked(mouseEvent -> {
            System.out.println(mouseEvent.getSceneX());
            System.out.println(mouseEvent.getSceneY());
        });
    }

    public GridTile getTile(int x, int y)
    {
        return tiles[x][y];
    }
}
