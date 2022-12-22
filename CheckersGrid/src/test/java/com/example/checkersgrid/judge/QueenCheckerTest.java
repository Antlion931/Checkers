package com.example.checkersgrid.judge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QueenCheckerTest {

    @Test
    void draw() {
        PlayerInterface mockedPlayer = mock(PlayerInterface.class);

        when(mockedPlayer.getShortcut()).thenReturn('m');

        QueenChecker queen = new QueenChecker(mockedPlayer);

        assertEquals('M', queen.draw());
    }

    @Test
    void possible_moves() {
    }

    @Test
    void possible_attacks() {
    }
}