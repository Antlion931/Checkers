package com.example.checkersgrid.judge;

public interface PlayerInterface {
    public void setSideOnce(Board.Sides side);

    public Board.Sides getSide();

    public char getShortcut();
}
