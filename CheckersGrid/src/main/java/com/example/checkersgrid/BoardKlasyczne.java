package com.example.checkersgrid;

public class BoardKlasyczne extends AbstractBoard
{
    public BoardKlasyczne()
    {
        boardSize = 4;
        rowsNum = 1;
        winConditionIsCheckerPresent = true;
        createNew();
    }
}
