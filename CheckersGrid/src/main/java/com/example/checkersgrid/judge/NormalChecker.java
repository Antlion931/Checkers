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

        //attacks
        possibleOffsets = new Cords[] {new Cords(-2, -2), new Cords(2, -2), new Cords(-2, 2), new Cords(2, 2)};

        for (Cords offset : possibleOffsets) {
            try {
                if( board.getFieldFrom(him, offset) == null && board.performAttack(him, board.getCordsFrom(him, offset)) != null) {
                    List<Cords> simple = new ArrayList<>();
                    simple.add(0, him);
                    simple.add(1, board.getCordsFrom(him, offset));
                    result.add(simple);

                    List<List<Cords>> potentialNextMoves = thinkThroughtNextAttacks(board.performAttack(him, board.getCordsFrom(him, offset)), board.getCordsFrom(him, offset));

                    for(List<Cords> lc : potentialNextMoves) {
                        lc.add(0, him);
                        lc.add(1, board.getCordsFrom(him, offset));
                        result.add(lc);
                    }
                }
            } catch (OutOfBounds e) { continue;}
        }
        return result;
    }

    private List<List<Cords>> thinkThroughtNextAttacks(Board board, Cords him) {
        List<List<Cords>> result = new ArrayList<>();
        Cords[] possibleOffsets = new Cords[] {new Cords(-2, -2), new Cords(2, -2), new Cords(-2, 2), new Cords(2, 2)};

        for (Cords offset : possibleOffsets) {
            try {
                if( board.getFieldFrom(him, offset) == null && board.performAttack(him, board.getCordsFrom(him, offset)) != null) {
                    List<Cords> simple = new ArrayList<>();
                    simple.add(0, board.getCordsFrom(him, offset));
                    result.add(simple);

                    List<List<Cords>> potentialNextMoves = thinkThroughtNextAttacks(board.performAttack(him, board.getCordsFrom(him, offset)), board.getCordsFrom(him, offset));

                    for(List<Cords> lc : potentialNextMoves) {
                        lc.add(0, board.getCordsFrom(him, offset));
                        result.add(lc);
                    }
                }
            } catch (OutOfBounds e) { continue;}
        }

        return result;
    }
}
