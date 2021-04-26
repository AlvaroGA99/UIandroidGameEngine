package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class EditorActivity extends AppCompatActivity   {

   public FragmentManager fm;
   private EditorGameSurfaceFragment f1 = new EditorGameSurfaceFragment();
    private EditorUiFragment f2 = new EditorUiFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);
        //if (savedInstanceState == null) {
             fm = getSupportFragmentManager();


            fm.beginTransaction().add(R.id.editorContainer, f1).add(R.id.editorContainer, f2,null).commit();
        //}

        // View decorview = getWindow().getDecorView();
         //decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);




    }
}