package fxTests;

import com.example.checkersgrid.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateBoardTest
{
    @Test
    void factoryKlasyczneTest()
    {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Klasyczne");
        SimpleBoard boardKlasyczne = new BoardKlasyczne();

        assertEquals(board.getClass(), boardKlasyczne.getClass());
    }
    @Test
    void factoryPolskieTest()
    {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Polskie");
        SimpleBoard boardPolskie = new BoardPolskie();

        assertEquals(board.getClass(), boardPolskie.getClass());
    }
    @Test
    void factoryWybijankaTest()
    {
        Factory factory = new BoardFactory();
        SimpleBoard board = factory.getBoard("Wybijanka");
        SimpleBoard boardWybijanka = new BoardWybijanka();

        assertEquals(board.getClass(), boardWybijanka.getClass());
    }
}
