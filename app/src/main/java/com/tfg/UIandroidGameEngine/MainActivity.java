package com.tfg.UIandroidGameEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            Toast.makeText(this, "SOY UN ASDXFASDASSDASD ", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().add(R.id.container, new MainMenuFragment(),null)/*.add(newProjectFragment,null).hide(newProjectFragment).add(usersProjectFragment,null).hide(usersProjectFragment).add(localProjectFragment,null).hide(localProjectFragment)*/.commit();
        }




              }
}