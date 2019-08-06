package com.example.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    boolean stateX = true;

    private ImageView[] positions;
    private boolean[] isPlayed;
    private TextView statusView;
    private int[] boardState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isPlayed = new boolean[9];
        boardState = new int[9];
        statusView = findViewById(R.id.game_status_display);
        positions = new ImageView[]{
                findViewById(R.id.image11),
                findViewById(R.id.image12),
                findViewById(R.id.image13),
                findViewById(R.id.image21),
                findViewById(R.id.image22),
                findViewById(R.id.image23),
                findViewById(R.id.image31),
                findViewById(R.id.image32),
                findViewById(R.id.image33)
        };

        for (ImageView image : positions)
            image.setOnClickListener(this);
    }

    private int getImageWithState() {
        return (stateX) ? R.drawable.x_image : R.drawable.o_image;
    }

    private void toggleState() {
        if (checkWin()) {
            for (int i = 0; i < 8; i++)
                isPlayed[i] = true;
            String message = stateX ? "X Won!" : "O Won!";
            statusView.setText(message);
        } else if (checkTie()) {
            statusView.setText(getString(R.string.tie));
        } else {
            stateX = !stateX;
            String message = stateX ? "X's Turn" : "O's Turn";
            statusView.setText(message);
        }
    }

    private boolean checkTie() {
        for (int i : boardState)
            if (i == 0)
                return false;
        return true;
    }

    private boolean checkWin() {
        int[] wins = {
                boardState[0] * boardState[1] * boardState[2],
                boardState[3] * boardState[4] * boardState[5],
                boardState[6] * boardState[7] * boardState[8],
                boardState[0] * boardState[3] * boardState[6],
                boardState[1] * boardState[4] * boardState[2],
                boardState[2] * boardState[5] * boardState[8],
                boardState[0] * boardState[4] * boardState[8],
                boardState[2] * boardState[4] * boardState[6]
        };
        for (int i : wins)
            if (i == 1 || i == 8)
                return true;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image11:
                if (!isPlayed[0]) {
                    positions[0].setImageResource(getImageWithState());
                    boardState[0] = stateX ? 1 : 2;
                    toggleState();
                    isPlayed[0] = true;
                }
                break;
            case R.id.image12:
                if (!isPlayed[1]) {
                    boardState[1] = stateX ? 1 : 2;
                    positions[1].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[1] = true;
                }
                break;
            case R.id.image13:
                if (!isPlayed[2]) {
                    boardState[2] = stateX ? 1 : 2;
                    positions[2].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[2] = true;
                }
                break;
            case R.id.image21:
                if (!isPlayed[3]) {
                    boardState[3] = stateX ? 1 : 2;
                    positions[3].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[3] = true;
                }
                break;
            case R.id.image22:
                if (!isPlayed[4]) {
                    boardState[4] = stateX ? 1 : 2;
                    positions[4].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[4] = true;
                }
                break;
            case R.id.image23:
                if (!isPlayed[5]) {
                    boardState[5] = stateX ? 1 : 2;
                    positions[5].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[5] = true;
                }
                break;
            case R.id.image31:
                if (!isPlayed[6]) {
                    boardState[6] = stateX ? 1 : 2;
                    positions[6].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[6] = true;
                }
                break;
            case R.id.image32:
                if (!isPlayed[7]) {
                    boardState[7] = stateX ? 1 : 2;
                    positions[7].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[7] = true;
                }
                break;
            case R.id.image33:
                if (!isPlayed[8]) {
                    boardState[8] = stateX ? 1 : 2;
                    positions[8].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[8] = true;
                }
                break;
        }
    }
}
