package com.example.checkersgrid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridTile extends Rectangle
{
    private CheckerPiece piece;
    private boolean isHighlighted;
    int xPos;
    int yPos;
    public GridTile(boolean light, int x, int y)
    {
        super();
        xPos = x;
        yPos = y;
        isHighlighted = false;
        setWidth(80);
        setHeight(80);
        if(light)
        {
            setFill(Color.WHEAT);
        }
        else
        {
            setFill(Color.SIENNA);
        }

        relocate(x * getWidth(), y * getHeight());
    }
    private void setSize(double W, double H)
    {
        setWidth(W);
        setHeight(H);
    }
    public void setPiece(CheckerPiece checkerPiece)
    {
        piece = checkerPiece;
    }
    public CheckerPiece getPiece()
    {
        return piece;
    }
    public void highlightTile(boolean light)
    {
        if(light)
        {
            setStroke(Color.BLUE);
            setSize(78,78);
            setStrokeWidth(3);
        }
        else
        {
            setSize(80, 80);
            setStrokeWidth(0);
        }
        isHighlighted = light;
    }
    public boolean isHighlighted()
    {
        return isHighlighted;
    }
}
