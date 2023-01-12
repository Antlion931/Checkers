package fxTests;

import com.example.checkersgrid.BoardFactory;
import com.example.checkersgrid.Factory;
import com.example.checkersgrid.SimpleBoard;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaneTest
{
    @Test
    void paneTest()
    {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Klasyczne");
        board.createNew();
        Pane pane = board.getPane();

        assertNotNull(pane.getChildren());
    }
}
