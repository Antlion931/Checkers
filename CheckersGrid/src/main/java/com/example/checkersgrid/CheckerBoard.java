package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import javafx.scene.Group;
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
                    piece = new CheckerPiece(CheckerType.BLACK, j, i, size);
                }
                if(i >= size - 3 && (i + j) % 2 == 1)
                {
                    piece = new CheckerPiece(CheckerType.WHITE, j, i, size);
                }
                if(piece != null)
                {
                    tiles[j][i].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                }
            }
        }
    }

    public synchronized void update(String draw) {
        for(int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                if(draw.split("\n")[y + 1].charAt(x) == 'b')
                {
                    var piece = new CheckerPiece(CheckerType.BLACK, x, y, tiles.length);
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else if(draw.split("\n")[y + 1].charAt(x) == 'w')
                {
                    var piece = new CheckerPiece(CheckerType.WHITE, x, y, tiles.length);
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                }else if(draw.split("\n")[y + 1].charAt(x) == 'B')
                {
                    var piece = new CheckerPiece(CheckerType.BLACK, x, y, tiles.length);
                    piece.makeQueen();
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else if(draw.split("\n")[y + 1].charAt(x) == 'W')
                {
                    var piece = new CheckerPiece(CheckerType.WHITE, x, y, tiles.length);
                    piece.makeQueen();
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                }  else {
                    removePiece(x, y);
                }
            }
        }
    }

    public void removePiece(int x, int y)
    {
        checkerPieceGroup.getChildren().remove(tiles[x][y].getPiece());
        tiles[x][y].setPiece(null);
    }

    public synchronized GridTile[][] getTiles() {
        return tiles;
    }
}
