package com.example.checkersgrid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CheckerPiece extends Circle
{
    private final CheckerType checkerType;
    public CheckerPiece(CheckerType type, int x, int y)
    {
        super();
        checkerType = type;
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
    }
}
