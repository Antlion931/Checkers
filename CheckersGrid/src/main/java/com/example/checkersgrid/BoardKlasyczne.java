package com.example.checkersgrid;

public class BoardKlasyczne extends AbstractBoard
{
    public BoardKlasyczne()
    {
        boardSize = 8;
        rowsNum = 3;
        winConditionIsCheckerPresent = true;
        createNew();
    }
}
