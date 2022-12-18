package com.example.checkersgrid.judge;

public enum Player {
    WHITE('W'),
    BLACK('B');

    private char shortcut_for_drawing;
    private Board.Sides side;


    Player(char shortcut_for_drawing) {
        this.shortcut_for_drawing = shortcut_for_drawing;
    }

    public void setSideOnce(Board.Sides side) {
        if (side  != null) {
            this.side = side;
        }
    }

    public Board.Sides getSide() {
        return side;
    }

    public char shortcut() {
        return shortcut_for_drawing;
    }
}
