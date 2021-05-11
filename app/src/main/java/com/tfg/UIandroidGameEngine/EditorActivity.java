package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity   {

   public FragmentManager fm;
   public GameEngine theGameEngine = new GameEngine();
   private EditorGameSurfaceFragment f1 = EditorGameSurfaceFragment.newInstance(theGameEngine);
    private EditorUiFragment f2 = EditorUiFragment.newInstance(theGameEngine);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor/*new EditorGameView(this)*/);

        if(theGameEngine.getObjectsInScene().size() == 0){
           // Toast.makeText(getApplicationContext(),"CReación", Toast.LENGTH_SHORT).show();
            theGameEngine.ctx = getApplicationContext();
            BasicGameObject aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2,0, theGameEngine.getTheInputManager(),theGameEngine.ctx);

            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2 + 100, (float)(getHeight())/2,0,theGameEngine.getTheInputManager(),theGameEngine.ctx);
            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            theGameEngine.addGameObject(aux);
            aux = new BasicGameObject((float)(getWidth())/2, (float)(getHeight())/2,2,theGameEngine.getTheInputManager(),theGameEngine.ctx);
            aux.preUpdateScale.x = 3;
            aux.scale.x = 3;
            aux.preUpdateScale.y = 3;
            aux.scale.y = 3;
            aux.addComponent("InputMovementPlatformerComponent");
            aux.addComponent("GravityComponent");
            aux.addComponent("GroundColliderComponent");
            theGameEngine.addGameObject(aux);}
        //if (savedInstanceState == null) {
             fm = getSupportFragmentManager();


            fm.beginTransaction().add(R.id.editorContainer, f1).add(R.id.editorContainer, f2,null).commit();
        //}

        // View decorview = getWindow().getDecorView();
         //decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);




    }

    private int getHeight(){

        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        return dp.heightPixels;
    }


    private int getWidth(){

        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        return dp.widthPixels;
    }
}