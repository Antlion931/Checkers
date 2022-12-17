package com.example.checkersgrid.judge;

public class Board {
    Checker[][] body;

    public Board(int x, int players_rows) {
        body = new Checker[x][x];

        for(int i = 1; i < players_rows*x; i++ ) {
            if (((i%x) + (i/x)) % 2 == 1) {
                body[i%x][i/x] = CheckersFactory.getInstance().makeCheckerForPlayer(0);
            }
        }

        for(int i = x*x - 1; i >= (x-players_rows)*x; i-- ) {
            if (((i%x) + (i/x)) % 2 == 1) {
                body[i%x][i/x] = CheckersFactory.getInstance().makeCheckerForPlayer(1);
            }
        }

    }

    public  String draw() {
        String result = new String();

        for (int i = 0; i < body.length; i++){
            for (int k = 0; k < body.length; k++ ){
                if(body[k][i] != null) {
                    result+=body[k][i].playerid;
                } else {
                    result+='-';
                }
            }
            result+='\n';
        }
        return result;
    }
}
