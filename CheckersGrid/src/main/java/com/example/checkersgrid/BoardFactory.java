package com.example.checkersgrid;

public class BoardFactory implements Factory
{
    @Override
    public SimpleBoard getBoard(String boardType) {
        if(boardType == null)
        {
            return null;
        }
        else if(boardType.equalsIgnoreCase("Klasyczne"))
        {
            return new BoardKlasyczne();
        }
        else if(boardType.equalsIgnoreCase("Polskie"))
        {
            return new BoardPolskie();
        }
        else if(boardType.equalsIgnoreCase("Wybijanka"))
        {
            return new BoardWybijanka();
        }

        return null;
    }
}
