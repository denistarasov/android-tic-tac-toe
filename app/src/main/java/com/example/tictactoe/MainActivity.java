package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE =
            "com.example.tictactoe.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void startGameActivity(TicTacToeGame.GameMode gameMode) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, gameMode);
        startActivity(intent);
    }

    public void startGameActivityTwoPlayers(View view) {
        startGameActivity(TicTacToeGame.GameMode.TWO_PLAYERS);
    }

    public void startGameActivityVsAi(View view) {
        startGameActivity(TicTacToeGame.GameMode.VS_AI);
    }
}
