package com.example.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    boolean stateX = true;

    private ImageView[] positions;
    private boolean[] isPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isPlayed = new boolean[9];
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
        stateX = !stateX;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image11:
                if (!isPlayed[0]) {
                    positions[0].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[0] = true;
                }
                break;
            case R.id.image12:
                if (!isPlayed[1]) {
                    positions[1].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[1] = true;
                }
                break;
            case R.id.image13:
                if (!isPlayed[2]) {
                    positions[2].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[2] = true;
                }
                break;
            case R.id.image21:
                if (!isPlayed[3]) {
                    positions[3].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[3] = true;
                }
                break;
            case R.id.image22:
                if (!isPlayed[4]) {
                    positions[4].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[4] = true;
                }
                break;
            case R.id.image23:
                if (!isPlayed[5]) {
                    positions[5].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[5] = true;
                }
                break;
            case R.id.image31:
                if (!isPlayed[6]) {
                    positions[6].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[6] = true;
                }
                break;
            case R.id.image32:
                if (!isPlayed[7]) {
                    positions[7].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[7] = true;
                }
                break;
            case R.id.image33:
                if (!isPlayed[8]) {
                    positions[8].setImageResource(getImageWithState());
                    toggleState();
                    isPlayed[8] = true;
                }
                break;
        }
    }
}
