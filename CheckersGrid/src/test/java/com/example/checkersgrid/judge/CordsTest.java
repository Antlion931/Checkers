package com.example.checkersgrid.judge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CordsTest {

    @Test
    void cordsEquals() {
        assertEquals(true, new Cords(1, 6).cordsEquals(new Cords(1, 6)));
        assertEquals(true, new Cords(-61, 0).cordsEquals(new Cords(-61, 0)));
        assertEquals(true, new Cords(0, 0).cordsEquals(new Cords(0, 0)));
        assertEquals(false, new Cords(1, 6).cordsEquals(new Cords(1, 7)));
        assertEquals(false, new Cords(-61, 0).cordsEquals(new Cords(61, 0)));
    }

    @Test
    void multiplay() {
        assertEquals(true, new Cords(6, 5).multiplay(3).cordsEquals(new Cords(18, 15)));
        assertEquals(true, new Cords(-6, 5).multiplay(-3).cordsEquals(new Cords(18, -15)));
        assertEquals(false, new Cords(-6, 0).multiplay(6).cordsEquals(new Cords(36, 0)));
        assertEquals(true, new Cords(6, 5).multiplay(0).cordsEquals(new Cords(0, 0)));
    }
}