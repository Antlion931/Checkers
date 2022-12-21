package com.example.checkersgrid.judge;

public enum Player implements PlayerInterface{
    WHITE('w'),
    BLACK('b');

    private char shortcut;

    private Board.Sides side;

    Player(char shortcut) {
        this.shortcut = shortcut;
    }

    public void setSideOnce(Board.Sides side) {
        if (side  != null) {
            this.side = side;
        }
    }

    public Board.Sides getSide() {
        return side;
    }

    public char getShortcut() {
        return shortcut;
    }
}
