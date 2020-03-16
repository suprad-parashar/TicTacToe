package com.example.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add Buttons for selecting different levels of gameplay.
        Button noobButton, intermediateButton, proButton, humanButton;
        noobButton = findViewById(R.id.noob_button);
        intermediateButton = findViewById(R.id.intermediate_button);
        proButton = findViewById(R.id.pro_button);
        humanButton = findViewById(R.id.human_button);
        
        //Logic for Level 0 button.
        noobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("level", 0);
                startActivity(intent);
            }
        });
        //Logic for Level 1 button.
        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("level", 1);
                startActivity(intent);
            }
        });
        //Logic for Level 2 button.
        proButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("level", 2);
                startActivity(intent);
            }
        });
        //Logic for Level 3 button.
        humanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("level", 3);
                startActivity(intent);
            }
        });
    }
}
