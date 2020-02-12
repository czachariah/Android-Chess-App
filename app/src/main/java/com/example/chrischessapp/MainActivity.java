package com.example.chrischessapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

/**
 * @author Chris Zachariah
 */

public class MainActivity extends AppCompatActivity {

    /**
     * This method will start the main app. This page is not intended to be the real start page.
     * @param savedInstanceState => a Bundle
     * This method will re-direct to the real main page of the application
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* go straight to the start page */
        Intent intent = new Intent(this,StartPage.class);
        startActivity(intent);
    } // ends the onCreate() method
} // ends the MainActivity () class
