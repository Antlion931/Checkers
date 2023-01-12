package fxTests;

import com.example.checkersgrid.BoardFactory;
import com.example.checkersgrid.Factory;
import com.example.checkersgrid.GridTile;
import com.example.checkersgrid.SimpleBoard;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardPieceTest
{
    @Test
    void pieceTest()
    {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Klasyczne");
        GridTile[][] tiles = board.getTiles();

        assertEquals(tiles[1][0].getPiece().getFill(), Color.BLACK);
        assertEquals(tiles[0][7].getPiece().getFill(), Color.WHITE);
    }
}
