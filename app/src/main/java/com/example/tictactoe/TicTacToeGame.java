package com.example.tictactoe;

import java.util.Arrays;

public class TicTacToeGame {
    private class GridRow {
        int[] playersMarks;

        GridRow() {
            playersMarks = new int[NUMBER_OF_PLAYERS];
        }
    }

    private static final int GRID_SIZE = 3;
    private static final int MARKS_NEEDED_TO_WIN = GRID_SIZE;
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int NUMBER_OF_CELLS = GRID_SIZE * GRID_SIZE;
    private static final int GRID_DEFAULT_VALUE = -1;

    private int[][] gameGrid;
    private GridRow[] horizontalRows;
    private GridRow[] verticalRows;
    private GridRow primaryDiagonal;
    private GridRow secondaryDiagonal;
    private int winnerPlayerNumber;
    private boolean isFinished;
    private int currentPlayerNumber;
    private int cellsMarkedNumber;

    public TicTacToeGame() {
        isFinished = false;
        gameGrid = new int[GRID_SIZE][GRID_SIZE];
        horizontalRows = new GridRow[GRID_SIZE];
        verticalRows = new GridRow[GRID_SIZE];
        for (int i = 0; i != GRID_SIZE; ++i) {
            horizontalRows[i] = new GridRow();
            verticalRows[i] = new GridRow();
            for (int j = 0; j != GRID_SIZE; ++j) {
                gameGrid[i][j] = GRID_DEFAULT_VALUE;
            }
        }
        primaryDiagonal = new GridRow();
        secondaryDiagonal = new GridRow();
        currentPlayerNumber = 0;
        winnerPlayerNumber = -2;
        cellsMarkedNumber = 0;
    }

    public boolean tryMarkCell(int horizontalRowNumber, int verticalRowNumber) {
        if (gameGrid[horizontalRowNumber][verticalRowNumber] != GRID_DEFAULT_VALUE) {
            return false;
        }

        gameGrid[horizontalRowNumber][verticalRowNumber] = currentPlayerNumber;
        ++cellsMarkedNumber;
        ++horizontalRows[horizontalRowNumber].playersMarks[currentPlayerNumber];
        ++verticalRows[verticalRowNumber].playersMarks[currentPlayerNumber];
        if (horizontalRowNumber == verticalRowNumber) {
            ++primaryDiagonal.playersMarks[currentPlayerNumber];
        }
        if (horizontalRowNumber == GRID_SIZE - verticalRowNumber - 1) {
            ++secondaryDiagonal.playersMarks[currentPlayerNumber];
        }

        updateStatus(horizontalRowNumber, verticalRowNumber);
        currentPlayerNumber = (currentPlayerNumber + 1) % NUMBER_OF_PLAYERS;

        return true;
    }

    private void updateStatus(int horizontalRowNumber, int verticalRowNumber) {
        if (
                horizontalRows[horizontalRowNumber].playersMarks[currentPlayerNumber] == MARKS_NEEDED_TO_WIN ||
                verticalRows[verticalRowNumber].playersMarks[currentPlayerNumber] == MARKS_NEEDED_TO_WIN ||
                primaryDiagonal.playersMarks[currentPlayerNumber] == MARKS_NEEDED_TO_WIN ||
                secondaryDiagonal.playersMarks[currentPlayerNumber] == MARKS_NEEDED_TO_WIN
        ) {
            winnerPlayerNumber = currentPlayerNumber;
            isFinished = true;
        } else if (cellsMarkedNumber == NUMBER_OF_CELLS) {
            winnerPlayerNumber = -1;
            isFinished = true;
        }
    }

    public int getWinnerPlayerNumber() {
        return winnerPlayerNumber;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public int[][] getGrid() {
        return gameGrid;
    }

    public void printGridPretty() {
        int[][] currentGrid = getGrid();
        for (int[] row : currentGrid) {
            System.out.println(Arrays.toString(row));
        }
    }
}
