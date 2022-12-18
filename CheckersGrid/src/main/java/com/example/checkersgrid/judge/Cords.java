package com.example.checkersgrid.judge;

public class Cords {
    public  int x;
    public int y;

    public Cords() {
        x = 0;
        y = 0;
    }

    public  Cords(Cords other) {
        this.x = other.x;;
        this.y = other.y;
    }

    public Cords(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
