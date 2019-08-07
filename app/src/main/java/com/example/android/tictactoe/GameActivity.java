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

class WinningTrio {
    int a;
    int b;
    int c;

    int getValue() {
        return GameActivity.boardState[a] * GameActivity.boardState[b] * GameActivity.boardState[c];
    }
}

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    boolean stateX = true;
    private int level;
    private ImageView[] positions;
    private boolean[] isPlayed;
    private TextView statusView, messageView;
    private WinningTrio[] trios;
    static public int[] boardState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initVariables();
        if (level != 3)
            statusView.setText(getResources().getString(R.string.your_turn));
    }

    /**
     * Initialises all the global variables and arrays.
     */
    private void initVariables() {
        isPlayed = new boolean[9];
        trios = new WinningTrio[8];
        for (int i = 0; i < 8; i++)
            trios[i] = new WinningTrio();
        initTrios();
        boardState = new int[9];
        for (int i = 0; i < 9; i++)
            boardState[i] = 2;
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
        for (ImageView image : positions)
            image.setOnClickListener(this);
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 3);
    }

    /**
     * Fetches the correct image based on whose turn it is to play.
     *
     * @return : The Image X or O based on the current state.
     */
    private int getImageWithState() {
        return (stateX) ? R.drawable.x_image : R.drawable.o_image;
    }

    /**
     * Changes The State of the Player.
     */
    private void toggleState() {
        if (checkWin()) {
            makeWin();
        } else if (checkTie()) {
            statusView.setText(getString(R.string.tie));
        } else {
            stateX = !stateX;
            String message = stateX ? "X's Turn" : "O's Turn";
            statusView.setText(message);
        }
    }

    /**
     * Sets the Winner.
     */
    private void makeWin() {
        for (int i = 0; i < 8; i++)
            isPlayed[i] = true;
        String message = stateX ? "X Won!" : "O Won!";
        statusView.setText(message);
    }

    /**
     * The Computer makes the move based on the level of gameplay.
     */
    private void makeMove() {
        int pos;
        if (level == 0) {
            //Assign Random Position.
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            pos = availablePositions.get(new Random().nextInt(availablePositions.size()));
        } else if (level == 1) {
            //Check and Obtain the Winning Trio.
            WinningTrio winningTrio = null;
            for (WinningTrio trio : trios) {
                if (trio.getValue() == 50)
                    winningTrio = trio;
            }
            if (winningTrio == null) {
                for (WinningTrio trio : trios)
                    if (trio.getValue() == 18)
                        winningTrio = trio;
            }
            //If Winning Trio exists, place a move to win/block.
            if (winningTrio != null) {
                if (boardState[winningTrio.a] == 2)
                    pos = winningTrio.a;
                else if (boardState[winningTrio.b] == 2)
                    pos = winningTrio.b;
                else
                    pos = winningTrio.c;
            } else {
                //Choose a random Position.
                List<Integer> availablePositions = new ArrayList<>();
                for (int i = 0; i < 9; i++)
                    if (boardState[i] == 2)
                        availablePositions.add(i);
                pos = availablePositions.get(new Random().nextInt(availablePositions.size()));
            }
        } else {
            pos = getPositionForAI();
        }
        makeMove(pos + 1);
    }

    int getPositionForAI() {
        int bestScore = -100;
        int position = -1;
        List<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            if (boardState[i] == 2)
                availablePositions.add(i);
        for (int pos : availablePositions) {
            boardState[pos] = 3;
            isPlayed[pos] = true;
            int score = getScoreForAI();
            if (score > bestScore) {
                bestScore = score;
                position = pos;
            }
            isPlayed[pos] = false;
            boardState[pos] = 2;
        }
        return position;
    }

    private int getScoreForAI() {
        if (gradeBoard() == 10)
            return 10;
        else if (gradeBoard() == -10)
            return -10;
        else if (checkTie())
            return 0;

        if (stateX) {
            int bestScore = -100;
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            for (int pos : availablePositions) {
                boardState[pos] = 3;
                isPlayed[pos] = true;
                int score = getScoreForAI();
                if (score > bestScore)
                    bestScore = score;
                isPlayed[pos] = false;
                boardState[pos] = 2;
            }
            return bestScore;
        } else {
            int bestScore = 100;
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            for (int pos : availablePositions) {
                boardState[pos] = 5;
                isPlayed[pos] = true;
                int score = getScoreForAI();
                if (score < bestScore)
                    bestScore = score;
                isPlayed[pos] = false;
                boardState[pos] = 2;
            }
            return bestScore;
        }
    }

    private int gradeBoard() {
        WinningTrio winningTrio = null;
        for (WinningTrio trio : trios)
            if (trio.getValue() == 27 || trio.getValue() == 125) {
                winningTrio = trio;
                break;
            }
        if (winningTrio == null)
            return 0;
        else if (winningTrio.getValue() == 27) {
            return 10;
        } else
            return -10;
    }

    /**
     * Initialises the Winning Trios.
     */
    private void initTrios() {
        trios[0].a = 0;
        trios[0].b = 1;
        trios[0].c = 2;
        trios[1].a = 3;
        trios[1].b = 4;
        trios[1].c = 5;
        trios[2].a = 6;
        trios[2].b = 7;
        trios[2].c = 8;
        trios[3].a = 0;
        trios[3].b = 3;
        trios[3].c = 6;
        trios[4].a = 1;
        trios[4].b = 4;
        trios[4].c = 7;
        trios[5].a = 2;
        trios[5].b = 5;
        trios[5].c = 8;
        trios[6].a = 0;
        trios[6].b = 4;
        trios[6].c = 8;
        trios[7].a = 2;
        trios[7].b = 4;
        trios[7].c = 6;
    }

    /**
     * Checks if a tie exists.
     *
     * @return : Returns true if the match is a draw or false otherwise.
     */
    private boolean checkTie() {
        for (int i : boardState)
            if (i == 2)
                return false;
        return true;
    }

    /**
     * Checks if a player has won.
     *
     * @return : Returns true if a player has won or false otherwise.
     */
    private boolean checkWin() {
        for (WinningTrio trio : trios)
            if (trio.getValue() == 27 || trio.getValue() == 125)
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

    /**
     * Executes the Human Move.
     *
     * @param position : The Position of the move on the board.
     */
    private void makeMove(int position) {
        if (!isPlayed[position - 1]) {
            isPlayed[position - 1] = true;
            positions[position - 1].setImageResource(getImageWithState());
            boardState[position - 1] = stateX ? 3 : 5;
            String message = (stateX ? "X" : "O") + " played at box " + position;
            messageView.setText(message);
            toggleState();
            if (level != 3 && !stateX) {
                makeMove();
            }
        }
    }
}
