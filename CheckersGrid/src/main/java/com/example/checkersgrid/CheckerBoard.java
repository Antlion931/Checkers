package com.example.checkersgrid;

import com.example.checkersgrid.judge.Board;
import com.example.checkersgrid.judge.Cords;
import com.example.checkersgrid.judge.Player;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.util.List;

public class CheckerBoard extends Pane
{
    private final GridTile[][] tiles;
    private final Group checkerTileGroup;
    private final Group checkerPieceGroup;
    private final Board judgeBoard;

    public CheckerBoard(int size)
    {
        checkerTileGroup = new Group();
        checkerPieceGroup = new Group();
        //hardcoded number or player rows
        judgeBoard = new Board(size, 3);
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

//        List<List<Cords>> movesWHITE = judgeBoard.showAllPossibleMovesOfPlayer(Player.WHITE);
//        System.out.println(judgeBoard.draw());
//
//        for(List<Cords> start : movesWHITE)
//        {
//            System.out.println(start.get(0).x + ", " + start.get(0).y);
//            tiles[start.get(0).x][start.get(0).y].highlightTile(true);
//        }
    }

    public void removePiece(int x, int y)
    {
        checkerPieceGroup.getChildren().remove(tiles[x][y].getPiece());
        tiles[x][y].setPiece(null);
    }
    public Board getJudgeBoard()
    {
        return judgeBoard;
    }
    public GridTile[][] getTiles() {
        return tiles;
    }
}
