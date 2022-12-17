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
    }
}
