package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    private TicTacToeGame game = new TicTacToeGame();
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button newGameButton;
    private TextView topTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        newGameButton = findViewById(R.id.new_game_button);

        GridLayout grid = findViewById(R.id.tic_tac_toe_grid_layout);
        int childCount = grid.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final ImageButton cellButton = (ImageButton) grid.getChildAt(i);
            final int verticalRowIndex = i % 3;
            final int horizontalRowIndex = i / 3;
            cellButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TicTacToeGame.CellStatus cellStatus = handleClick(horizontalRowIndex, verticalRowIndex);
                    Log.d(TAG, "User clicked on cell, coordinates: " + horizontalRowIndex + " " + verticalRowIndex);
                    cellButton.setImageResource(cellStatusToVectorDrawable(cellStatus));
                }
            });
        }
    }

    private TicTacToeGame.CellStatus handleClick(int horizontalRowIndex, int verticalRowIndex) {
        game.tryMarkCell(horizontalRowIndex, verticalRowIndex);
        if (game.getIsFinished()) {
            topTextView = findViewById(R.id.top_text);
            int winnerPlayerNumber = game.getWinnerPlayerNumber() + 1;
            topTextView.setText("Player " + winnerPlayerNumber + " won");  // TODO: refactor
            newGameButton.setVisibility(View.VISIBLE);
        }
        return game.getCellStatus(horizontalRowIndex, verticalRowIndex);
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

        game = new TicTacToeGame();
    }
}
