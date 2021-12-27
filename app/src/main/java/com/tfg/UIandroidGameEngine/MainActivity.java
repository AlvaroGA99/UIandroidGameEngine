package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private int currentApiVersion;
    FirebaseAuth fa = FirebaseAuth.getInstance();
    public LinearLayout crearProyecto ;
    public LinearLayout plantillas ;
    public LinearLayout slider ;
    boolean itsOut;
    public int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearProyecto = null;
        plantillas = null;
        itsOut = true;

        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);
        width = dp.widthPixels;
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            //Toast.makeText(this, "SOY UN ASDXFASDASSDASD ", Toast.LENGTH_SHORT).show();
            slider = null;
            fm.beginTransaction().add(R.id.container, new MainMenuFragment(),null)/*.add(newProjectFragment,null).hide(newProjectFragment).add(usersProjectFragment,null).hide(usersProjectFragment).add(localProjectFragment,null).hide(localProjectFragment)*/.commit();
        }


        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.config_button, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                if (slider != null){
                    switch (item.getItemId()) {
                        case R.id.action_settings:

                            if (itsOut) {
                                slider.animate().translationX(0);
                            } else {
                                slider.animate().translationX(width);
                            }
                            itsOut = !itsOut;


                            return true;

                        default:
                            return super.onOptionsItemSelected(item);
                    }
                }
               return false;




            default:
                return super.onOptionsItemSelected(item);
        }


    }





         @Override
         public void onBackPressed(){

        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.putExtra("exit", "exit");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        if(crearProyecto != null && plantillas != null){

            if (crearProyecto.getVisibility() == View.VISIBLE && plantillas.getVisibility() == View.INVISIBLE){
                crearProyecto.setVisibility(View.INVISIBLE);
                plantillas.setVisibility(View.VISIBLE);
            }else{
                super.onBackPressed();
            }

        }else{
            super.onBackPressed();
        }

         }
}