package fxTests;

import com.example.checkersgrid.BoardFactory;
import com.example.checkersgrid.Factory;
import com.example.checkersgrid.GridTile;
import com.example.checkersgrid.SimpleBoard;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTileTest
{
    @Test
    void tilesTest() {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Klasyczne");
        GridTile[][] tiles = board.getTiles();

        assertNull(tiles[0][0].getPiece());
        assertEquals(tiles[0][0].getFill(), Color.WHEAT);
        assertEquals(tiles[0][0].getHeight(), 80.0);
        assertEquals(tiles[0][0].getWidth(), 80.0);

        assertNotNull(tiles[1][0].getPiece());
        assertEquals(tiles[1][0].getFill(), Color.SIENNA);
        assertEquals(tiles[1][0].getHeight(), 80.0);
        assertEquals(tiles[1][0].getWidth(), 80.0);
    }
}
