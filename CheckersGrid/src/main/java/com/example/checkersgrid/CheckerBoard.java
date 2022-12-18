package com.example.checkersgrid;

import javafx.scene.layout.Pane;

public class CheckerBoard extends Pane
{
    private final GridTile[][] tiles;

    public CheckerBoard(int size)
    {
        tiles = new GridTile[size][size];

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                GridTile tile = new GridTile((i + j) % 2 == 0, j, i);
                tiles[j][i] = tile;
                getChildren().add(tile);

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
                    getChildren().add(piece);
                }
            }
        }
    }

    public GridTile getTile(int x, int y)
    {
        return tiles[x][y];
    }
}
