package com.example.checkersgrid;
import com.example.checkersgrid.judge.Board;
import javafx.scene.layout.Pane;

public interface SimpleBoard
{
    void removePiece(int x, int y);
    public void update(String draw);
    GridTile[][] getTiles();
    int getSize();
    boolean getWinCondition();
    Pane getPane();
    void createNew();
}
