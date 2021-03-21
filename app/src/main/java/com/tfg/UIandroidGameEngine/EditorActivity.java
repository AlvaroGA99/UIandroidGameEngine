package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);
        //if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.editorContainer, new EditorGameSurfaceFragment()).add(R.id.editorContainer, new EditorUiFragment(),null).commit();
        //}
    }
}