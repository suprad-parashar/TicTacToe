package com.example.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    boolean stateX = true;
    private int level;
    private ImageView[] positions;
    private boolean[] isPlayed;
    private TextView statusView, messageView;
    private int[] boardState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isPlayed = new boolean[9];
        boardState = new int[9];
        statusView = findViewById(R.id.game_status_display);
        messageView = findViewById(R.id.game_message_display);
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

        Intent intent = getIntent();
        level = intent.getIntExtra("level",3);
        if (level == 0) {
            statusView.setText(getResources().getString(R.string.your_turn));
        }
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
            if (level == 0 && !stateX) {
                makeMove();
            }
            String message = stateX ? "X's Turn" : "O's Turn";
            statusView.setText(message);
        }
    }

    private void makeMove() {
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            if (boardState[i] == 0)
                availablePositions.add(i);
        int pos = availablePositions.get(new Random().nextInt(availablePositions.size()));
        makeMove(pos + 1);
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
                boardState[1] * boardState[4] * boardState[7],
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
                makeMove(1);
                break;
            case R.id.image12:
                makeMove(2);
                break;
            case R.id.image13:
                makeMove(3);
                break;
            case R.id.image21:
                makeMove(4);
                break;
            case R.id.image22:
                makeMove(5);
                break;
            case R.id.image23:
                makeMove(6);
                break;
            case R.id.image31:
                makeMove(7);
                break;
            case R.id.image32:
                makeMove(8);
                break;
            case R.id.image33:
                makeMove(9);
                break;
        }
    }

    private void makeMove(int position) {
        if (!isPlayed[position - 1]) {
            positions[position - 1].setImageResource(getImageWithState());
            boardState[position - 1] = stateX ? 1 : 2;
            String message = (stateX ? "X" : "O") + " played at box " + position;
            messageView.setText(message);
            toggleState();
            isPlayed[position - 1] = true;
        }
    }
}
