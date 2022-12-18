package com.example.checkersgrid.judge;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public enum Sides {
        UPPER,
        BOTTOM,



    }

    Checker[][] body;

    public Board(int size, int players_rows) {
        body = new Checker[size][size];

        Player.BLACK.setSideOnce(Sides.UPPER);
        for(int i = 1; i < players_rows*size; i++ ) {
            if (((i%size) + (i/size)) % 2 == 1) {
                body[i%size][i/size] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.BLACK);
            }
        }

        Player.WHITE.setSideOnce(Sides.BOTTOM);
        for(int i = size*size - 1; i >= (size-players_rows)*size; i-- ) {
            if (((i%size) + (i/size)) % 2 == 1) {
                body[i%size][i/size] = CheckersFactory.getInstance().makeCheckerForPlayer(Player.WHITE);
            }
        }

    }

    public  String draw() {
        String result = new String();

        for (int y = 0; y < body.length; y++){
            for (int x = 0; x < body.length; x++ ){
                if(body[x][y] != null) {
                    result+=body[x][y].player.shortcut();
                } else {
                    result+='-';
                }
            }
            result+='\n';
        }
        return result;
    }

    public Cords getCordsFrom(Cords him, Cords offset) {
        Cords result = new Cords(offset);
        if (body[him.x][him.y].player.getSide() == Sides.UPPER) {
            result.x = -result.x;
            result.y = -result.y;
        }

        result.x = him.x + result.x;
        result.y = him.y + result.y;

        return result;
    }

    public Checker getFieldFrom(Cords him, Cords offset) throws OutOfBounds {
        Cords cords = getCordsFrom(him, offset);

        if( cords.x<0 || cords.x>=body.length || cords.y<0 || cords.y>= body.length) {
            throw new OutOfBounds();
        }

        return body[cords.x][cords.y];
    }

    public List<List<Cords>> showAllPossibleMovesOfPlayer(Player player) {
        List<List<Cords>> result = new ArrayList<>();

        for (int y = 0; y < body.length; y++) {
            for (int x = 0; x < body.length; x++ ) {
                if(body[x][y] == null || body[x][y].player != player) {
                    continue;
                }

                result.addAll(body[x][y].possible_moves(this, new Cords(x, y)));
            }
        }

        return result;
    }
}
