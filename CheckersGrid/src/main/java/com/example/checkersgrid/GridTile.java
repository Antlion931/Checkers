package com.example.checkersgrid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridTile extends Rectangle
{
    public GridTile(boolean white)
    {
        super();
        setWidth(100);
        setHeight(100);
        if(white)
        {
            setFill(Color.WHITE);
        }
        else
        {
            setFill(Color.GREY);
        }

        setOnMouseEntered(mouseEvent -> {
            highlightTile(true);
        });
        setOnMouseExited(mouseEvent -> {
            highlightTile(false);
        });
    }

    public void setSize(double W, double H)
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
