package com.example.checkersgrid;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridTile extends Rectangle
{
    public GridTile(boolean light, int x, int y)
    {
        super();
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
        setTranslateX(40);
        setTranslateY(40);
    }

    private void setSize(double W, double H)
    {
        setWidth(W);
        setHeight(H);
    }

    public void highlightTile(boolean highlighted)
    {
        if(highlighted)
        {
            setStroke(Color.BLUE);
            setSize(96,96);
            setStrokeWidth(4);
        }
        else
        {
            setSize(100, 100);
            setStrokeWidth(0);
        }
    }
}
