package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.gridlayout.widget.GridLayout;

import java.util.Queue;

public class GameActivity extends AppCompatActivity {
    private GridLayout gameField;
    private static final String TAG = GameActivity.class.getSimpleName();
    private Button newGameButton;
    private TextView topTextView;
    private TicTacToeGame game;
    private TicTacToeGame.GameMode gameMode;

    private int cellStatusToVectorDrawable(TicTacToeGame.CellStatus cellStatus) {
        switch (cellStatus) {
            case CROSS:
                return R.drawable.cross;
            case NOUGHT:
                return R.drawable.nought;
            default:
                return R.drawable.empty;
        }
    }

    private void updateGameCell(TicTacToeGame.GameMove move) {
        int horizontalRowIndex = move.horizontalRowIndex;
        int verticalRowIndex = move.verticalRowIndex;
        ImageButton cell = (ImageButton) gameField.getChildAt(horizontalRowIndex * 3 + verticalRowIndex);
        cell.setImageResource(cellStatusToVectorDrawable(move.cellStatus));
    }

    private void updateGameField() {
        Queue<TicTacToeGame.GameMove> history = game.getHistory();
        Log.d(TAG, "Queue size is " + history.size());
        TicTacToeGame.GameMove userMove = history.poll();
        if (userMove != null) {
            Log.d(TAG, "History queue head is " + userMove.horizontalRowIndex + " " + userMove.verticalRowIndex + ", status: " + userMove.cellStatus);
            updateGameCell(userMove);
        }

        if (gameMode == TicTacToeGame.GameMode.VS_AI) {
            TicTacToeGame.GameMove aiMove = history.poll();
            if (aiMove != null) {
                Log.d(TAG, "History queue head is " + aiMove.horizontalRowIndex + " " + aiMove.verticalRowIndex + ", status: " + aiMove.cellStatus);
                updateGameCell(aiMove);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        gameMode = (TicTacToeGame.GameMode) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
        game = new TicTacToeGame(gameMode);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        newGameButton = findViewById(R.id.new_game_button);
        topTextView = findViewById(R.id.top_text);

        gameField = findViewById(R.id.tic_tac_toe_grid_layout);
        int childCount = gameField.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final ImageButton cellButton = (ImageButton) gameField.getChildAt(i);
            final int verticalRowIndex = i % 3;
            final int horizontalRowIndex = i / 3;
            cellButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    handleClick(horizontalRowIndex, verticalRowIndex);
                    Log.d(TAG, "User clicked on cell, coordinates: " + horizontalRowIndex + " " + verticalRowIndex);
                    updateGameField();
                }
            });
        }
    }

    private void handleClick(int horizontalRowIndex, int verticalRowIndex) {
        game.tryMarkCell(horizontalRowIndex, verticalRowIndex);
        if (game.getIsFinished()) {
            int winnerPlayerNumber = game.getWinnerPlayerNumber();
            switch (winnerPlayerNumber) {
                case -1:
                    topTextView.setText(getResources().getString(R.string.tie_message));
                    break;
                case 0:
                    topTextView.setText(getResources().getString(R.string.crosses_win_message));
                    break;
                case 1:
                    topTextView.setText(getResources().getString(R.string.noughts_win_message));
                    break;
            }
            newGameButton.setVisibility(View.VISIBLE);
        }
    }

    public void startNewGame(View view) {
        topTextView.setText(R.string.top_text);

        GridLayout grid = findViewById(R.id.tic_tac_toe_grid_layout);
        int childCount = grid.getChildCount();

        TicTacToeGame.CellStatus defaultStatus = TicTacToeGame.CellStatus.EMPTY;
        for (int i = 0; i < childCount; i++) {
            final ImageButton imageButton = (ImageButton) grid.getChildAt(i);
            imageButton.setImageResource(cellStatusToVectorDrawable(defaultStatus));
        }

        game = new TicTacToeGame(gameMode);
    }
}
