package com.example.checkersgrid;

public class BoardPolskie extends AbstractBoard
{
    public BoardPolskie()
    {
        boardSize = 10;
        rowsNum = 4;
        winConditionIsCheckerPresent = true;
        createNew();
    }
}
