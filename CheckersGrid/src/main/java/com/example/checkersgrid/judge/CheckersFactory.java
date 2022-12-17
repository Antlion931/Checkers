package com.example.checkersgrid.judge;

public class CheckersFactory {
    static CheckersFactory instance;

    static public CheckersFactory getInstance()
    {
        if(instance == null) {
            instance = new CheckersFactory();
        }
        return instance;
    }

    private CheckersFactory() {

    }
    public Checker makeCheckerForPlayer(int playerid) {
        return new Checker(playerid);
        //TODO:
    }
}
