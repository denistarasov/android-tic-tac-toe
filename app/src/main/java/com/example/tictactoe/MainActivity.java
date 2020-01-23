package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    private TicTacToeGame game = new TicTacToeGame();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout grid = findViewById(R.id.tic_tac_toe_grid_layout);
        int childCount = grid.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final Button button = (Button) grid.getChildAt(i);
            final int verticalRowIndex = i % 3;
            final int horizontalRowIndex = i / 3;
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    TicTacToeGame.CellStatus cellStatus = handleClick(horizontalRowIndex, verticalRowIndex);
                    Log.d(TAG, "User clicked on cell, coordinates: " + horizontalRowIndex + " " + verticalRowIndex);
                    String cellText = "";
                    switch (cellStatus) {
                        case CROSS:
                            cellText = "X";
                            break;
                        case NOUGHT:
                            cellText = "O";
                            break;
                    }
                    button.setText(cellText);
                }
            });
        }
    }

    private TicTacToeGame.CellStatus handleClick(int horizontalRowIndex, int verticalRowIndex) {
        game.tryMarkCell(horizontalRowIndex, verticalRowIndex);
        return game.getCellStatus(horizontalRowIndex, verticalRowIndex);
    }
}
