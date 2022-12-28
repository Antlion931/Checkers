package com.example.checkersgrid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CheckerPiece extends Circle
{
    private final CheckerType checkerType;
    private final int Size;
    private boolean isStriking;
    public CheckerPiece(CheckerType type, int x, int y, int boardSize)
    {
        super();
        checkerType = type;
        Size = boardSize;
        isStriking = false;
        setRadius(30);
        setStroke(Color.BLACK);
        setStrokeWidth(2);

        if(checkerType == CheckerType.WHITE)
        {
            setFill(Color.WHITE);
        }
        else
        {
            setFill(Color.BLACK);
        }

        placeChecker(x, y);

        toFront();

        setMouseTransparent(true);
    }
    public void placeChecker(int x, int y)
    {
        relocate(x * 80, y * 80);
        setTranslateX(10);
        setTranslateY(10);
        if(checkerType == CheckerType.WHITE && y == 0)
        {
            makeQueen();
        }
        else if(checkerType == CheckerType.BLACK && y == Size - 1)
        {
            makeQueen();
        }
    }
    public void makeQueen()
    {
        setStroke(Color.GOLD);
        setRadius(27);
        setStrokeWidth(8);
    }
    public void duringStrike(boolean strike)
    {
        isStriking = strike;
    }
    public boolean strikingState()
    {
        return isStriking;
    }
}
