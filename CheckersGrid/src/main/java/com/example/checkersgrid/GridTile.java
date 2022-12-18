package com.example.checkersgrid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridTile extends Rectangle
{
    private CheckerPiece piece;
    boolean isHighlighted;
    boolean atMovement;
    int xPos;
    int yPos;
    public GridTile(boolean light, int x, int y)
    {
        super();
        xPos = x;
        yPos = y;
        isHighlighted = false;
        atMovement = false;
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

        setOnMouseClicked(mouseEvent -> {
//            System.out.println("getx(): " + getX());
//            System.out.println("getLayoutX(): " + getLayoutX());
//            System.out.println("getScaleX(): " + getScaleX());
//            System.out.println("getTranslateX(): " + getTranslateX());;
        });

        relocate(x * getWidth(), y * getHeight());
        //setTranslateX(40);
        //setTranslateY(40);
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
    public void highlightTile()
    {
        if(isHighlighted)
        {
            setSize(100, 100);
            setStrokeWidth(0);
        }
        else
        {
            setStroke(Color.BLUE);
            setSize(96,96);
            setStrokeWidth(4);
        }
        isHighlighted = !isHighlighted;
    }
    public void setAtMovement(boolean movement)
    {
        atMovement = movement;
    }
    public boolean isAtMovement() {
        return atMovement;
    }
}
