package com.example.checkersgrid.judge;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void draw() {
        Board board = new Board(3,0);
        Checker mockedChecker = mock(Checker.class);
        when(mockedChecker.draw()).thenReturn('m');
        board.body[0][0] = mockedChecker;
        board.body[1][2] = mockedChecker;

        assertEquals("m--\n---\n-m-\n", board.draw());
    }

    @Test
    void getCordsFrom() {

    }

    @Test
    void getFieldFrom() {
    }

    @Test
    void showAllPossibleMovesOfPlayer() {
    }

    @Test
    void showAllPossibleAttacksOfPlayer() {
    }

    @Test
    void update() {
    }

    @Test
    void performAttack() {
    }
}