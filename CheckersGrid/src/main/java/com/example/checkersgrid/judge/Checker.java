package com.example.checkersgrid.judge;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

abstract public class Checker {
    public Player player;

    public Checker(Player player) {
        this.player = player;
    }

    public char draw() {
        return player.getShortcut();
    }

    abstract public List<List<Cords>> possible_moves(Board board, Cords him);

    abstract public List<List<Cords>> possible_attacks(Board board, Cords him);
}
