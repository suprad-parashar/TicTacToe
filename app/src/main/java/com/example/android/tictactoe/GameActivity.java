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

/**
 * The WinningTrio class consists of the positions which constitutes the winning row, column or diagonal.
 */
class WinningTrio {
    int a;
    int b;
    int c;

    int getValue() {
        return GameActivity.boardState[a] * GameActivity.boardState[b] * GameActivity.boardState[c];
    }
}

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    boolean stateX = true;      //A boolean which tells who's turn it is to play.
    private int level;      //Specifies the difficulty of the opponent.
    private ImageView[] positions;      //Stores the locations of the images in the positions of the board.
    private boolean[] isPlayed;     //An array which tells us if a position has been played or not.
    private TextView statusView, messageView;       //Two TextViews to update the statuses of the board.
    private WinningTrio[] trios;        //The Winning trios.
    static public int[] boardState;     //An array of integers which tells us the state of the board.

    /**
     * The Method which is called when the game is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);     //Set the content of the screen to activity_game.xml

        initVariables();        //Initialise all the variables.
        if (level != 3)         //Check if the level is human or not.
            statusView.setText(getResources().getString(R.string.your_turn));       //Set the text of the status view to "Your Turn" if the opponent is not a human.
    }

    /**
     * Initialises all the global variables and arrays.
     */
    private void initVariables() {
        isPlayed = new boolean[9];
        trios = new WinningTrio[8];
        for (int i = 0; i < 8; i++)
            trios[i] = new WinningTrio();
        initTrios();        //Initialise all the winning positions.
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
        level = intent.getIntExtra("level", 3);     //Get the Level of the game.
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
        for (int i = 0; i < 9; i++)
            isPlayed[i] = true;
        String message = stateX ? "X Won!" : "O Won!";
        statusView.setText(message);
    }

    /**
     * The Computer makes the move based on the level of gameplay.
     */
    private void makeMove() {
        int pos;
        if (level == 0) {       //If the computer player is a noob.
            //Assign Random Position.
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            pos = availablePositions.get(new Random().nextInt(availablePositions.size()));
        } else if (level == 1) {        //If the computer player is an intermediate player.
            //Check and Obtain the Winning Trio.
            WinningTrio winningTrio = null;
            for (WinningTrio trio : trios)
                if (trio.getValue() == 18 || trio.getValue() == 50)
                    winningTrio = trio;
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
        } else {        //If the computer player is a pro.
            int bestScore = -9999;
            int position = -1;
            //Get all the available positions.
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            //Get all possible scores of the future boards.
            for (int p : availablePositions) {
                boardState[p] = 5;
                int score = getScoreForAI(true);
                boardState[p] = 2;
                if (score > bestScore) {
                    bestScore = score;
                    position = p;
                }
            }
            pos = position;
        }
        makeMove(pos + 1);
    }

    /**
     * This method is used to obtain the score of the board for the AI.
     * @param stateX : Is True if it's X's Turn, else false.
     * @return : A score of the current board.
     */
    private int getScoreForAI(boolean stateX) {
        int score = gradeBoard();
        if (score != 0)
            return score;
        else if (checkTie())
            return 0;
        if (stateX) {       //Minimise Player's Score.
            int bestScore = 9999;
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            for (int pos : availablePositions) {
                boardState[pos] = 3;
                score = getScoreForAI(false);
                if (score < bestScore)
                    bestScore = score;
                boardState[pos] = 2;
            }
            return bestScore;
        } else {        //Maximise Computer's Score.
            int bestScore = -9999;
            List<Integer> availablePositions = new ArrayList<>();
            for (int i = 0; i < 9; i++)
                if (boardState[i] == 2)
                    availablePositions.add(i);
            for (int pos : availablePositions) {
                boardState[pos] = 5;
                score = getScoreForAI(true);
                if (score > bestScore)
                    bestScore = score;
                boardState[pos] = 2;
            }
            return bestScore;
        }
    }

    /**
     * Assigns score to a board.
     * @return : +10 if O Wins, -10 is X Wins, 0 otherwise.
     */
    private int gradeBoard() {
        for (WinningTrio trio : trios) {
            if (trio.getValue() == 27)
                return -10;
            else if (trio.getValue() == 125)
                return 10;
        }
        return 0;
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

    /**
     * Performs tasks if something is clicked.
     */
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