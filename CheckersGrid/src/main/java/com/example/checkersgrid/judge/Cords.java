package com.example.checkersgrid.judge;

public class Cords {
    public  int x;
    public int y;

    public Cords() {
        x = 0;
        y = 0;
    }

    public boolean cordsEquals(Cords obj) {
        return this.x == obj.x && this.y == obj.y;
    }

    public  Cords(Cords other) {
        this.x = other.x;;
        this.y = other.y;
    }

    public Cords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cords multiply(int k) {
        return new Cords(x*k, y*k);
    }
}
