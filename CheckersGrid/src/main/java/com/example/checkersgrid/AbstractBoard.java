package com.example.checkersgrid;
import com.example.checkersgrid.judge.Board;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public abstract class AbstractBoard implements SimpleBoard
{
    protected Pane pane = new Pane();
    protected GridTile[][] tiles;
    protected Group checkerTileGroup;
    protected Group checkerPieceGroup;
    protected Board judgeBoard;
    protected int boardSize;
    protected int rowsNum;
    protected boolean winConditionIsCheckerPresent;
    @Override
    public void update(String draw){
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                if (draw.split("\n")[y + 1].charAt(x) == 'b') {
                    var piece = new CheckerPiece(CheckerType.BLACK, x, y, tiles.length);
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else if (draw.split("\n")[y + 1].charAt(x) == 'w') {
                    var piece = new CheckerPiece(CheckerType.WHITE, x, y, tiles.length);
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else if (draw.split("\n")[y + 1].charAt(x) == 'B') {
                    var piece = new CheckerPiece(CheckerType.BLACK, x, y, tiles.length);
                    piece.makeQueen();
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else if (draw.split("\n")[y + 1].charAt(x) == 'W') {
                    var piece = new CheckerPiece(CheckerType.WHITE, x, y, tiles.length);
                    piece.makeQueen();
                    tiles[x][y].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                } else {
                    removePiece(x, y);
                }
            }
        }
    }

    @Override
    public void removePiece(int x, int y)
    {
        checkerPieceGroup.getChildren().remove(tiles[x][y].getPiece());
        tiles[x][y].setPiece(null);
    }
    @Override
    public GridTile[][] getTiles()
    {
        return tiles;
    }
    @Override
    public int getSize()
    {
        return boardSize;
    }
    @Override
    public boolean getWinCondition()
    {
        return winConditionIsCheckerPresent;
    }
    @Override
    public Pane getPane()
    {
        return pane;
    }
    @Override
    public void createNew()
    {
        checkerTileGroup = new Group();
        checkerPieceGroup = new Group();
        judgeBoard = new Board(boardSize, rowsNum);
        tiles = new GridTile[boardSize][boardSize];
        pane.getChildren().addAll(checkerTileGroup, checkerPieceGroup);

        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                GridTile tile = new GridTile((i + j) % 2 == 0, j, i);
                tiles[j][i] = tile;
                checkerTileGroup.getChildren().add(tile);

                CheckerPiece piece = null;

                if(i <= rowsNum - 1 && (i + j) % 2 == 1)
                {
                    piece = new CheckerPiece(CheckerType.BLACK, j, i, boardSize);
                }
                if(i >= boardSize - rowsNum && (i + j) % 2 == 1)
                {
                    piece = new CheckerPiece(CheckerType.WHITE, j, i, boardSize);
                }
                if(piece != null)
                {
                    tiles[j][i].setPiece(piece);
                    checkerPieceGroup.getChildren().add(piece);
                }
            }
        }
    }
}
