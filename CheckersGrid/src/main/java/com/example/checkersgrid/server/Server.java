package com.example.checkersgrid.server;

import com.example.checkersgrid.judge.Player;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(58902)) {
            System.out.println("Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game();
                pool.execute(game.new ServerPlayer(listener.accept(), Player.WHITE));
                pool.execute(game.new ServerPlayer(listener.accept(), Player.BLACK));
            }
        }
    }
}
