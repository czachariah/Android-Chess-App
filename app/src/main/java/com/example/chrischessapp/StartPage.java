package com.example.chrischessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Chris Zachariah
 */
public class StartPage extends AppCompatActivity {

    /**
     * This method will create the main start page for the user.
     * @param savedInstanceState => Bundle
     * This method will have two buttons: Play New Game and Load Previous Game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // connect to the correct View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the newGame button
        Button newGameButton = findViewById(R.id.NewGameButton);

        // activate a listener for the button
        newGameButton.setOnClickListener(new View.OnClickListener() {
            // when clicking on the newGame button, the user will be re-directed to another page where one can play a new game
            @Override
            public void onClick(View view) {
                Log.i("new Game", "newGame button clicked");
                Toast.makeText(getApplicationContext(),"Starting new game ...",Toast.LENGTH_SHORT).show();
                openNewGame();
            }
        });

    } // ends the onCreate() method

    /**
     * This method will be used by the newGame button to re-direct to another page where a new chess game can then be played
     */
    public void openNewGame() {
        Intent intent = new Intent(this,PlayNewGame.class);
        startActivity(intent);
    } // ends the openNewGame() method
} // ends the StartPage() method
