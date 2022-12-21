package com.example.checkersgrid.judge;

import java.util.ArrayList;
import java.util.List;

public class QueenChecker extends Checker{
    public QueenChecker(PlayerInterface player) {
        super(player);
    }

    @Override
    public char draw() {
        return Character.toUpperCase(player.getShortcut());
    }

    @Override
    public List<List<Cords>> possible_moves(Board board, Cords him) {
        List<List<Cords>> result = new ArrayList<>();
        Cords[] vectors = new Cords[] {new Cords(1,1), new Cords(-1, 1), new Cords(1, -1), new Cords(-1, -1), new Cords(1, 0), new Cords(-1, 0), new Cords(0, 1), new Cords(0, -1)};

        for(Cords c : vectors) {
            try {
                for(int i = 1;; i++) {
                    if( board.getFieldFrom(him, c.multiplay(i)) == null) {
                        List<Cords> simple = new ArrayList<>();
                        simple.add(0, him);
                        simple.add(1, board.getCordsFrom(him, c.multiplay(i)));
                        result.add(simple);
                    } else {
                        break;
                    }
                }

            } catch (OutOfBounds e) {continue;}
        }

        return result;
    }

    @Override
    public List<List<Cords>> possible_attacks(Board board, Cords him) {
        List<List<Cords>> result = new ArrayList<>();
        Cords[] vectors = new Cords[] {new Cords(1,1), new Cords(-1, 1), new Cords(1, -1), new Cords(-1, -1), new Cords(1, 0), new Cords(-1, 0), new Cords(0, 1), new Cords(0, -1)};

        for(Cords c : vectors) {
            try {
                for(int i = 1;; i++) {
                    if( board.getFieldFrom(him, c.multiplay(i)) == null && board.performAttack(him, board.getCordsFrom(him, c.multiplay(i))) != null) {
                        List<Cords> simple = new ArrayList<>();
                        simple.add(0, him);
                        simple.add(1, board.getCordsFrom(him, c.multiplay(i)));
                        result.add(simple);

                        List<List<Cords>> potentialNextMoves = continoue_attack(board.performAttack(him, board.getCordsFrom(him, c.multiplay(i))), board.getCordsFrom(him, c.multiplay(i)), c);

                        for(List<Cords> lc : potentialNextMoves) {
                            lc.add(0, him);
                            lc.add(1, board.getCordsFrom(him, c.multiplay(i)));
                            result.add(lc);
                        }
                    }
                }

            } catch (OutOfBounds e) {continue;}
        }

        return result;
    }

    private List<List<Cords>> continoue_attack(Board board, Cords him, Cords vector) {
        List<List<Cords>> result = new ArrayList<>();

        try {
            for(int i = 1;; i++) {
                if( board.getFieldFrom(him, vector.multiplay(i)) == null && board.performAttack(him, board.getCordsFrom(him, vector.multiplay(i))) != null) {
                    List<Cords> simple = new ArrayList<>();
                    simple.add(0, board.getCordsFrom(him, vector.multiplay(i)));
                    result.add(simple);

                    List<List<Cords>> potentialNextMoves = continoue_attack(board.performAttack(him, board.getCordsFrom(him, vector.multiplay(i))), board.getCordsFrom(him, vector.multiplay(i)), vector);

                    for(List<Cords> lc : potentialNextMoves) {
                        lc.add(0, board.getCordsFrom(him, vector.multiplay(i)));
                        result.add(lc);
                    }
                }
            }

        } catch (OutOfBounds e) {}

        return result;
    }
}
