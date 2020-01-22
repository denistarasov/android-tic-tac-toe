package com.example.tictactoe;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeGameTest {
    @Test
    public void test_simpleGame() {
        TicTacToeGame game = new TicTacToeGame();
        assertFalse(game.getIsFinished());

        boolean isCellMarked = game.tryMarkCell(0, 0);
        assertTrue(isCellMarked);
        assertFalse(game.getIsFinished());

        isCellMarked = game.tryMarkCell(0, 1);
        assertTrue(isCellMarked);
        assertFalse(game.getIsFinished());

        isCellMarked = game.tryMarkCell(1, 1);
        assertTrue(isCellMarked);
        assertFalse(game.getIsFinished());

        isCellMarked = game.tryMarkCell(0, 2);
        assertTrue(isCellMarked);
        assertFalse(game.getIsFinished());

        isCellMarked = game.tryMarkCell(2, 2);
        assertTrue(isCellMarked);
        assertTrue(game.getIsFinished());

        assertEquals(game.getWinnerPlayerNumber(), 0);
    }

    @Test
    public void test_doubleMarking() {
        TicTacToeGame game = new TicTacToeGame();

        boolean wasCellMarked = game.tryMarkCell(0, 0);
        assertTrue(wasCellMarked);

        wasCellMarked = game.tryMarkCell(0, 0);
        assertFalse(wasCellMarked);
    }

    @Test
    public void test_Tie() {
        // Grid looks like this:
        // XOX
        // XOX
        // OXO

        TicTacToeGame game = new TicTacToeGame();

        game.tryMarkCell(0, 0);
        game.tryMarkCell(0, 1);
        game.tryMarkCell(0, 2);
        game.tryMarkCell(1, 1);
        game.tryMarkCell(1, 0);
        game.tryMarkCell(2, 0);
        game.tryMarkCell(1, 2);
        game.tryMarkCell(2, 2);
        game.tryMarkCell(2, 1);

        assertTrue(game.getIsFinished());
        assertEquals(game.getWinnerPlayerNumber(), -1);
    }

    @Test
    public void test_twoGridWinAtOnce() {
        // Grid looks like this:
        // OOX
        // OXX
        // XOX
        TicTacToeGame game = new TicTacToeGame();

        game.tryMarkCell(1, 1);
        game.tryMarkCell(0, 0);
        game.tryMarkCell(1, 2);
        game.tryMarkCell(1, 0);
        game.tryMarkCell(2, 2);
        game.tryMarkCell(0, 1);
        game.tryMarkCell(2, 0);
        game.tryMarkCell(2, 1);
        game.tryMarkCell(0, 2);

        assertTrue(game.getIsFinished());
        assertEquals(game.getWinnerPlayerNumber(), 0);
    }
}