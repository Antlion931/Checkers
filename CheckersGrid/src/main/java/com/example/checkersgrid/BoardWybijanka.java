package com.example.checkersgrid;

public class BoardWybijanka extends AbstractBoard
{
    public BoardWybijanka()
    {
        boardSize = 8;
        rowsNum = 3;
        winConditionIsCheckerPresent = false;
        createNew();
    }
}
