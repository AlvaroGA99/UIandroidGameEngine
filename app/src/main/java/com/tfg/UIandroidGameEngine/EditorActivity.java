package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new EditorGameView(this));
    }
}