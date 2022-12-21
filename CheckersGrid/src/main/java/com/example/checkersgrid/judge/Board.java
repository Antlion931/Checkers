package com.example.checkersgrid.judge;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    public enum Sides {
        UPPER,
        BOTTOM,
    }

    public Checker[][] body;

    public Board(Board other) {
        body = new Checker[other.body.length][other.body.length];

        for (int y = 0; y < body.length; y++) {
            for (int x = 0; x < body.length; x++ ) {
                if(other.body[x][y] == null) {
                    continue;
                }

                body[x][y] = other.body[x][y];
            }
        }

    }

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
                    result+=body[x][y].draw();
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

    public List<List<Cords>> showAllPossibleMovesOfPlayer(PlayerInterface player) {
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

    public List<List<Cords>> showAllPossibleAttacksOfPlayer(PlayerInterface player) {
        List<List<Cords>> result = new ArrayList<>();

        for (int y = 0; y < body.length; y++) {
            for (int x = 0; x < body.length; x++ ) {
                if(body[x][y] == null || body[x][y].player != player) {
                    continue;
                }

                result.addAll(body[x][y].possible_attacks(this, new Cords(x, y)));
            }
        }

        return result;
    }

    public void update(List<Cords> move) {
        if(listOfListContain(showAllPossibleMovesOfPlayer(body[move.get(0).x][move.get(0).y].player) , move)) {
            body[move.get(1).x][move.get(1).y] = body[move.get(0).x][move.get(0).y];
            body[move.get(0).x][move.get(0).y] = null;
        } else if (listOfListContain(showAllPossibleAttacksOfPlayer(body[move.get(0).x][move.get(0).y].player), move))  {
            for(int i = 0; i < move.size() - 1; i++) {
                body = performAttack(move.get(i), move.get(i + 1)).body;
            }
        } else {
            System.out.println("HI!");
        }
    }

    private boolean listOfListContain(List<List<Cords>> list, List<Cords> element) {
        over_every_list_element:
        for( List<Cords> lc : list) {
            if (lc.size() != element.size()) {
                continue;
            }

            for(int i = 0; i < lc.size(); i++) {
                if (!lc.get(i).cordsEquals(element.get(i))) {
                    continue over_every_list_element;
                }
            }
            return true;
        }
        return false;
    }

    public Board performAttack(Cords from, Cords to) {
        Board newBoard = new Board(this);
        int changeOnX = to.x - from.x != 0 ? (to.x - from.x) / Math.abs((to.x - from.x)) : 0;
        int changeOnY = to.y - from.y != 0 ? (to.y - from.y) / Math.abs((to.y - from.y)) : 0;

        if(newBoard.body[to.x][to.y] != null || newBoard.body[from.x][from.y] == null) {
            return null;
        }

        newBoard.body[to.x][to.y] = newBoard.body[from.x][from.y];
        newBoard.body[from.x][from.y] = null;

        Cords test = new Cords(from);
        test.x += changeOnX;
        test.y += changeOnY;

        boolean hasKillEnemy = false;

        while(!test.cordsEquals(to)) {
            if(newBoard.body[test.x][test.y] != null) {
                if(body[test.x][test.y].player == body[from.x][from.y].player) {
                    return null;
                }

                if(hasKillEnemy) {
                    return null;
                }

                newBoard.body[test.x][test.y] = null;
                hasKillEnemy = true;
            }

            test.x += changeOnX;
            test.y += changeOnY;
        }

        if (hasKillEnemy) {
            return newBoard;
        }
        return null;
    }

}
