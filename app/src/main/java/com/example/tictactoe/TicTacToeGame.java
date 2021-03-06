package com.example.tictactoe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class TicTacToeGame {
    private final GameMode gameMode;

    private class GridRow {
        int[] playersMarks;

        GridRow() {
            playersMarks = new int[NUMBER_OF_PLAYERS];
        }
    }

    private final Random randomGenerator;
    private CellStatus[][] gameGrid;
    private static final int GRID_SIZE = 3;
    private static final int MARKS_NEEDED_TO_WIN = GRID_SIZE;
    private static final int NUMBER_OF_PLAYERS = 2;
    private static final int NUMBER_OF_CELLS = GRID_SIZE * GRID_SIZE;
    private final GridRow[] horizontalRows;
    private final GridRow[] verticalRows;
    private GridRow primaryDiagonal;
    private GridRow secondaryDiagonal;
    private int winnerPlayerNumber;
    private boolean isFinished;
    private Queue<GameMove> gameHistory;
    private int currentPlayerNumber;
    private int cellsMarkedNumber;

    TicTacToeGame() {
        this(GameMode.TWO_PLAYERS);
    }

    TicTacToeGame(GameMode gameMode) {
        this.gameMode = gameMode;
        isFinished = false;
        gameGrid = new CellStatus[GRID_SIZE][GRID_SIZE];
        horizontalRows = new GridRow[GRID_SIZE];
        verticalRows = new GridRow[GRID_SIZE];
        for (int i = 0; i != GRID_SIZE; ++i) {
            horizontalRows[i] = new GridRow();
            verticalRows[i] = new GridRow();
            for (int j = 0; j != GRID_SIZE; ++j) {
                gameGrid[i][j] = CellStatus.EMPTY;
            }
        }
        primaryDiagonal = new GridRow();
        secondaryDiagonal = new GridRow();
        currentPlayerNumber = 0;
        winnerPlayerNumber = -2;
        cellsMarkedNumber = 0;
        randomGenerator = new Random();
        gameHistory = new LinkedList<>();
    }

    private void markCellByAi() {
        boolean wasCellMarked = false;
        while (!wasCellMarked && !isFinished) {
            int randomHorizontalRowIndex = randomGenerator.nextInt(3);
            int randomVerticalRowIndex = randomGenerator.nextInt(3);
            wasCellMarked = tryMarkCell(randomHorizontalRowIndex, randomVerticalRowIndex, true);
        }
    }

    boolean tryMarkCell(int horizontalRowIndex, int verticalRowIndex) {
        return tryMarkCell(horizontalRowIndex, verticalRowIndex, false);
    }

    private boolean tryMarkCell(int horizontalRowIndex, int verticalRowIndex, boolean isAiTurn) {
        if (gameGrid[horizontalRowIndex][verticalRowIndex] != CellStatus.EMPTY || isFinished) {
            return false;
        }

        gameGrid[horizontalRowIndex][verticalRowIndex] = currentPlayerNumber == 0 ? CellStatus.CROSS : CellStatus.NOUGHT;
        ++cellsMarkedNumber;
        ++horizontalRows[horizontalRowIndex].playersMarks[currentPlayerNumber];
        ++verticalRows[verticalRowIndex].playersMarks[currentPlayerNumber];
        if (horizontalRowIndex == verticalRowIndex) {
            ++primaryDiagonal.playersMarks[currentPlayerNumber];
        }
        if (horizontalRowIndex == GRID_SIZE - verticalRowIndex - 1) {
            ++secondaryDiagonal.playersMarks[currentPlayerNumber];
        }
        gameHistory.add(new GameMove(
                horizontalRowIndex,
                verticalRowIndex,
                gameGrid[horizontalRowIndex][verticalRowIndex]
        ));

        updateStatus(horizontalRowIndex, verticalRowIndex);
        currentPlayerNumber = (currentPlayerNumber + 1) % NUMBER_OF_PLAYERS;

        if (gameMode == GameMode.VS_AI && !isAiTurn) {
            markCellByAi();
        }

        return true;
    }

    Queue<GameMove> getHistory() {
        return gameHistory;
    }

    public enum GameMode {
        TWO_PLAYERS,
        VS_AI
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

    int getWinnerPlayerNumber() {
        return winnerPlayerNumber;
    }

    boolean getIsFinished() {
        return isFinished;
    }

    private String cellStatusToText(CellStatus cell) {
        switch (cell) {
            case NOUGHT:
                return "O";
            case CROSS:
                return "X";
            default:
                return ".";
        }
    }

    private CellStatus[][] getGrid() {
        return gameGrid;
    }

    class GameMove {
        final int horizontalRowIndex;
        final int verticalRowIndex;
        final CellStatus cellStatus;

        GameMove(int horizontalRowIndex, int verticalRowIndex, CellStatus cellStatus) {
            this.horizontalRowIndex = horizontalRowIndex;
            this.verticalRowIndex = verticalRowIndex;
            this.cellStatus = cellStatus;
        }
    }

    public void printGridPretty() {
        CellStatus[][] currentGrid = getGrid();
        for (CellStatus[] row : currentGrid) {
            for (CellStatus cell : row) {
                System.out.print(cellStatusToText(cell));
            }
            System.out.println();
        }
    }

    public enum CellStatus {
        EMPTY,
        CROSS,
        NOUGHT
    }
}
