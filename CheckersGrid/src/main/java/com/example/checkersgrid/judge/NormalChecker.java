package com.example.checkersgrid.judge;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class NormalChecker extends Checker {
    public NormalChecker (Player player) {
        super(player);
    }

    public List<List<Cords>> possible_moves(Board board, Cords him) {
        List<List<Cords>> result = new ArrayList<>();
        //moves
        Cords[] possibleOffsets = new Cords[] {new Cords(-1, -1), new Cords(1, -1)};

        for (Cords offset : possibleOffsets) {
            try {
                if( board.getFieldFrom(him, offset) == null ) {
                    List<Cords> x = new ArrayList<>();
                    x.add(him);
                    x.add(board.getCordsFrom(him, offset));
                    result.add(x);
                }
            } catch (OutOfBounds e) { continue;}
        }




        return result;
    }

//    private List<List<Pair<Integer, Integer>>> think_throught_move(Board board, Pair<Integer, Integer> him, boolean attacked) {
//
//    }

}
